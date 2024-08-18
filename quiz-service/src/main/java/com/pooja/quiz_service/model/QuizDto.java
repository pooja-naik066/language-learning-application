package com.pooja.quiz_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private String quizTitle;

    private String difficultyLevel;

    private String language;

    private Integer numOfQuestions;
}
