package com.talismar.certification_nlw.modules.students.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyIfHasCertificationDTO {
    private String email;
    private String technology;
}
