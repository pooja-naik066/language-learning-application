package com.pooja.quiz_service.services;

import com.pooja.quiz_service.feign.QuizInterface;
import com.pooja.quiz_service.model.QuestionDto;
import com.pooja.quiz_service.model.Quiz;
import com.pooja.quiz_service.model.Response;
import com.pooja.quiz_service.repositories.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class QuizServiceImplTest {
    @Mock
    private QuizInterface quizInterface;

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizServiceImpl quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createQuiz() {
        String difficultyLevel = "EASY";
        String language = "English";
        Integer numOfQuestions = 5;
        String quizTitle = "Sample Quiz";

        List<Long> questionIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        ResponseEntity<List<Long>> responseEntity = ResponseEntity.ok(questionIds);
        when(quizInterface.generateQuestionsForQuiz(difficultyLevel, language, numOfQuestions)).thenReturn(responseEntity);
        String result = quizService.createQuiz(difficultyLevel, language, numOfQuestions, quizTitle);
        assertEquals("SUCCESS", result);
    }

    @Test
    void getQuestionsForQuizId() {
        Long quizId = 1L;
        List<Long> questionIds = Arrays.asList(1L, 2L, 3L);

        Quiz quiz = new Quiz();
        quiz.setQuizId(quizId);
        quiz.setQuestionIds(questionIds);

        List<QuestionDto> questionList = Arrays.asList(
                new QuestionDto(1L, "Question 1", "Option 1", "Option 2"),
                new QuestionDto(2L, "Question 2", "Option 1", "Option 2"));
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
        when(quizInterface.getQuestionsForIds(questionIds)).thenReturn(ResponseEntity.ok(questionList));
        List<QuestionDto> result = quizService.getQuestionsForQuizId(quizId);
        assertEquals(questionList.size(), result.size());
    }

    @Test
    void calculateScore() {
        Long quizId = 1L;
        List<Response> responses = Arrays.asList(
                new Response(1L, "A"),
                new Response(2L, "B"),
                new Response(3L, "C")
        );
        Integer expectedScore = 2;

        // Mock the behavior of the quizInterface
        when(quizInterface.calculateScore(responses)).thenReturn(ResponseEntity.ok(expectedScore));
        Integer result = quizService.calculateScore(quizId, responses);
        assertEquals(expectedScore, result);

    }
}