package com.talismar.certification_nlw.modules.questions.dtos;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionAlternativeResultDTO {
    private UUID id;
    private String technology;
    private String description;
    private List<AlternativesResultDTO> alternativesResultDTO;
}
