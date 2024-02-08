package com.talismar.certification_nlw.modules.certifications.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talismar.certification_nlw.modules.certifications.useCases.TopThenUseCase;
import com.talismar.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.talismar.certification_nlw.modules.students.repositories.CertificationStudentRepository;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    @Autowired
    private TopThenUseCase topThenUseCase;

    @GetMapping("/top10")
    public List<CertificationStudentEntity> topThen() {
        return this.topThenUseCase.execute();
    }
}
