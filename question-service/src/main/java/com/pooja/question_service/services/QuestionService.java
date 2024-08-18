package com.pooja.question_service.services;

import com.pooja.question_service.model.Question;
import com.pooja.question_service.model.QuestionDto;
import com.pooja.question_service.model.Response;
import org.springframework.http.HttpStatusCode;

import java.util.List;

public interface QuestionService {
    List<Question> getAllQuestions();

    Question getQuestionById(Long questionId);

    Question createQuestion(Question question);


    Question updateQuestion(Long questionId, Question question);

    String deleteQuestion(Long questionId);

    List<Long> generateQuestionsForQuiz(String difficultyLevel, String language, Integer numOfQuestions);

    List<QuestionDto> getQuestionsForIds(List<Long> questionIdList);

    Integer calculateScore(List<Response> responses);
}
