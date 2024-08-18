package com.pooja.quiz_service.controller;

import com.pooja.quiz_service.model.QuestionDto;
import com.pooja.quiz_service.model.QuizDto;
import com.pooja.quiz_service.model.Response;
import com.pooja.quiz_service.services.QuizService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizController.class)
class QuizControllerTest {
    @MockBean
    private QuizService quizService;

    @InjectMocks
    private QuizController quizController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createQuiz() throws Exception {
        QuizDto quizDto = new QuizDto("Sample Quiz","EASY", "English", 2);
        String response = "SUCCESS";
        when(quizService.createQuiz( quizDto.getDifficultyLevel(), quizDto.getLanguage(), quizDto.getNumOfQuestions(),quizDto.getQuizTitle()))
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/quiz/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"difficultyLevel\": \"EASY\", \"language\": \"English\", \"numOfQuestions\": 2, \"quizTitle\": \"Sample Quiz\"}" ))

                .andExpect(status().isOk())
                       .andExpect(content().string(response));

    }

    @Test
    void getQuestionsForQuizId() throws Exception {
        Long quizId = 1L;
        QuestionDto question1=new QuestionDto(1L,"Bonjour=","Bye","Hi");
        QuestionDto question2=new QuestionDto(2L,"Aaj","Today","Tomorrow");
        List<QuestionDto> questionList= Arrays.asList(question1,question2);
        when(quizService.getQuestionsForQuizId(quizId)).thenReturn(questionList);
        mockMvc.perform(MockMvcRequestBuilders.get("/quiz/" + quizId))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].question").value("Bonjour="));
    }

    @Test
    void submitQuiz() throws Exception {
        Long quizId = 1L;
        List<Response> responses = Arrays.asList(
                new Response(1L, "A"),
                new Response(2L, "B")
        );
        Mockito.when(quizService.calculateScore(quizId,responses)).thenReturn(2);
        mockMvc.perform(MockMvcRequestBuilders.post("/quiz/submit/" + quizId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"questionId\":1,\"response\":\"A\"},{\"questionId\":2,\"response\":\"B\"}]"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(2));
    }
}