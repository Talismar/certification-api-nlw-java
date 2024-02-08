package com.talismar.certification_nlw.modules.students.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerDTO {
    private UUID questionID;
    private UUID alternativeID;
    private Boolean isCorrect;
}
