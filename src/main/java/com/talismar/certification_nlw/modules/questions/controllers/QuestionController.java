package com.talismar.certification_nlw.modules.questions.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talismar.certification_nlw.modules.questions.dtos.AlternativesResultDTO;
import com.talismar.certification_nlw.modules.questions.dtos.QuestionAlternativeResultDTO;
import com.talismar.certification_nlw.modules.questions.entities.AlternativesEntity;
import com.talismar.certification_nlw.modules.questions.entities.QuestionEntity;
import com.talismar.certification_nlw.modules.questions.repositories.QuestionRepository;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository repository;

    @GetMapping("/technology/{technology}")
    public List<QuestionAlternativeResultDTO> findByTechnology(@PathVariable String technology) {
        var result = this.repository.findByTechnology(technology);
        var toMap = result.stream().map(question -> mapQuestionToDTO(question)).collect(Collectors.toList());
        return toMap;
    }

    static QuestionAlternativeResultDTO mapQuestionToDTO(QuestionEntity question) {
        var questionResultDTO = QuestionAlternativeResultDTO.builder()
        .id(question.getId())
        .technology(question.getTechnology())
        .description(question.getDescription()).build();

        List<AlternativesResultDTO> alternativesResultDTOs = question.getAlternatives().stream().map(alternative -> mapAlternativesToDTO((alternative))).collect(Collectors.toList());

        questionResultDTO.setAlternativesResultDTO(alternativesResultDTOs);
        return questionResultDTO;
    }   

    static AlternativesResultDTO mapAlternativesToDTO(AlternativesEntity alternatives) {
        return AlternativesResultDTO.builder()
        .id(alternatives.getId())
        .description(alternatives.getDescription()).build();
    }
}
