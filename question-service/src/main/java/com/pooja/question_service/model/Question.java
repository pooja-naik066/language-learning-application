package com.pooja.question_service.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long questionId;
    @NotNull(message = "Please enter the question")
    private String question;
    @NotNull(message = "Please enter the first option")
    private String firstOption;
    @NotNull(message = "Please enter second option")
    private String secondOption;
    @NotNull(message = "Please enter the answer")
    private String answer;
    private String difficultyLevel="EASY";
    @NotNull(message = "Please enter the language")
    private String language;
}
