package com.talismar.certification_nlw.modules.students.use_cases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talismar.certification_nlw.modules.students.dtos.VerifyIfHasCertificationDTO;
import com.talismar.certification_nlw.modules.students.repositories.CertificationStudentRepository;

@Service
public class VerifyIfHasCertificationUseCase {

    @Autowired
    private CertificationStudentRepository repository;

    public boolean execute(VerifyIfHasCertificationDTO dto) {
        var result = this.repository.findByStudentEmailAndTechnology(dto.getEmail(), dto.getTechnology());
        
        if (!result.isEmpty()) {
            return true;
        }
        
        return false;
    }
}
