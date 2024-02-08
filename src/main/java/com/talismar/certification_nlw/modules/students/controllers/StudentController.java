package com.talismar.certification_nlw.modules.students.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talismar.certification_nlw.modules.students.dtos.StudentCertificationAnswerDTO;
import com.talismar.certification_nlw.modules.students.dtos.VerifyIfHasCertificationDTO;
import com.talismar.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.talismar.certification_nlw.modules.students.use_cases.StudentyCertificationAnswersUseCase;
import com.talismar.certification_nlw.modules.students.use_cases.VerifyIfHasCertificationUseCase;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    @Autowired
    private StudentyCertificationAnswersUseCase studentyCertificationAnswersUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyIfHasCertificationDTO verifyIfHasCertificationDTO) {
        // Email
        // Technology
        System.out.println(verifyIfHasCertificationDTO);

        var result = this.verifyIfHasCertificationUseCase.execute(verifyIfHasCertificationDTO);

        if (result) {
            return "Usuário já fez a prova";
        }

        return "Usuário pode fazer a prova";
    }

    @PostMapping("/certification/answer")
    public ResponseEntity<?> certificationAnswer(@RequestBody StudentCertificationAnswerDTO studentCertificationAnswerDTO) {
        try {
            var result = studentyCertificationAnswersUseCase.execute(studentCertificationAnswerDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
