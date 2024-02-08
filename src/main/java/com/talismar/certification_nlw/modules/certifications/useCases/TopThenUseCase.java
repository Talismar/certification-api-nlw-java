package com.talismar.certification_nlw.modules.certifications.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talismar.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.talismar.certification_nlw.modules.students.repositories.CertificationStudentRepository;

@Service
public class TopThenUseCase {
    @Autowired
    private CertificationStudentRepository certificationStudentRepository;
    
    public List<CertificationStudentEntity> execute() {
        return certificationStudentRepository.findTop10ByOrderByGrateDesc();
    }

}
