package com.pooja.quiz_service.services;

import com.pooja.quiz_service.model.QuestionDto;
import com.pooja.quiz_service.model.Response;

import java.util.List;

public interface QuizService {
    String createQuiz(String difficultyLevel, String language, Integer numOfQuestions,String quizTitle);

    List<QuestionDto> getQuestionsForQuizId(Long quizId);

    Integer calculateScore(Long quizId, List<Response> responses);
}
