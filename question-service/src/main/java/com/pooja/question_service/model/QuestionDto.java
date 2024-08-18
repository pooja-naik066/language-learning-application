package com.pooja.question_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionDto {
    private Long questionId;
    private String question;
    private String firstOption;
    private String secondOption;

}
