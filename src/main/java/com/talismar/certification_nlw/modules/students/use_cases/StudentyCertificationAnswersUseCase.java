package com.talismar.certification_nlw.modules.students.use_cases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talismar.certification_nlw.modules.questions.repositories.QuestionRepository;
import com.talismar.certification_nlw.modules.students.dtos.StudentCertificationAnswerDTO;
import com.talismar.certification_nlw.modules.students.dtos.VerifyIfHasCertificationDTO;
import com.talismar.certification_nlw.modules.students.entities.AnswersCertificationsEntity;
import com.talismar.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.talismar.certification_nlw.modules.students.entities.StudentEntity;
import com.talismar.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import com.talismar.certification_nlw.modules.students.repositories.StudentRepository;

@Service
public class StudentyCertificationAnswersUseCase {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception{
        // Verificar se o usuário existe
        // var student = studentRepository.findByEmail(dto.getEmail());

        // if (student.isEmpty()) {
        //    throw new Exception("Email do estudante incorreto.");
        // }
        var hasCertification = verifyIfHasCertificationUseCase.execute(new VerifyIfHasCertificationDTO(dto.getEmail(), dto.getTechnology()));

        if (hasCertification) {
            throw new Exception("Você ja tirou sua certification");
        }

        // Buscar as alternativas das perguntas
        // - Correct ou Incorrect
        var questions = questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();

        AtomicInteger correctAnswers = new AtomicInteger(0);
        
        dto.getQuestionsAnswers().stream().forEach(questionAnswer -> {
            var question = questions.stream().filter(q -> q.getId().equals(questionAnswer.getQuestionID())).findFirst().get();
            var findCorrectAlternative = question.getAlternatives().stream().filter(alternative -> alternative.isCorrect()).findFirst().get();
            
            if (findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID())) {
                questionAnswer.setIsCorrect(true);
                correctAnswers.incrementAndGet();
            }else {
                questionAnswer.setIsCorrect(false);
            }

            var answerCertificationCreated = AnswersCertificationsEntity.builder()
            .answerID(questionAnswer.getAlternativeID())
            .questionID(questionAnswer.getQuestionID())
            .isCorrect(questionAnswer.getIsCorrect()).build();
            
            answersCertifications.add(answerCertificationCreated);
        });

        // Verificar se o usuário existe
        var student = studentRepository.findByEmail(dto.getEmail());
        UUID studentID;
        if (student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentID = studentCreated.getId();
        }else {
            studentID = student.get().getId();
        }

        // List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();

        CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
        .technology(dto.getTechnology())
        .studentID(studentID)
        .grate(correctAnswers.get())
        .build();

        var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);
        answersCertifications.stream().forEach(answersCertification -> {
            answersCertification.setCertificationID(certificationStudentEntity.getId());
            answersCertification.setCertificationStudentEntity(certificationStudentEntity);
        });

        certificationStudentEntity.setAnswersCertificationsEntity(answersCertifications);

        certificationStudentRepository.save(certificationStudentEntity);

        // Salvar as informação da certificação
        return certificationStudentCreated;
    }
}
