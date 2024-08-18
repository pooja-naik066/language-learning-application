package com.pooja.question_service.controller;

import com.pooja.question_service.model.Question;
import com.pooja.question_service.model.QuestionDto;
import com.pooja.question_service.model.Response;
import com.pooja.question_service.services.QuestionService;
import com.pooja.question_service.services.QuestionServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(QuestionController.class)
@TestExecutionListeners(listeners = WithSecurityContextTestExecutionListener.class, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class QuestionControllerTest {

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
             http.csrf(csrf -> csrf.disable())  // Disable CSRF protection (optional for APIs)
                    .authorizeHttpRequests(auth ->
                            auth.anyRequest().permitAll());
                    return  http.build();
        }
    }

    @MockBean
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllQuestions() throws Exception {
        Question question1=new Question(1L,"Bonjour=","Bye","Hi","Hi","EASY","French");
        Question question2=new Question(2L,"Aaj","Today","Tomorrow","Today","EASY","Hindi");
        List<Question> questionList= Arrays.asList(question1,question2);
        Mockito.when(questionService.getAllQuestions()).thenReturn(questionList);
        mockMvc.perform(MockMvcRequestBuilders.get("/questions"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstOption").value("Bye"));
    }

    @Test
    void getQuestionById() throws Exception {
        Question question1=new Question(1L,"Bonjour=","Bye","Hi","Hi","EASY","French");
        Mockito.when(questionService.getQuestionById(1L)).thenReturn(question1);
        mockMvc.perform(MockMvcRequestBuilders.get("/questions/1"))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstOption").value("Bye"));
    }

    @Test
   // @WithMockUser(username = "test")
    void createQuestion() throws Exception {
        Question question1=new Question(1L,"Bonjour=","Bye","Hi","Hi","EASY","French");

        // Mock the saveBook method to return the book after it's "saved"
        Mockito.when(questionService.createQuestion(any(Question.class))).thenReturn(question1);

        mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"question\": \"Bonjour=\", \"firstOption\": \"Bye\", \"secondOption\": \"Hi\", \"answer\": \"Hi\", \"difficultyLevel\": \"EASY\", \"language\": \"French\"}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.question").value("Bonjour="));
    }

    @Test
    void updateQuestion() throws Exception {
        Question question1 = new Question(1L, "Bonjour=", "Bye", "Hi", "Hi", "EASY", "French");

        // Mock the updateQuestion method in the QuestionService
        Mockito.when(questionService.updateQuestion(eq(1L),any(Question.class))).thenReturn(question1);

        // Perform a PUT request to update the question
        mockMvc.perform(MockMvcRequestBuilders.put("/questions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"questionText\": \"Bonjour=\", \"firstOption\": \"Bye\", \"secondOption\": \"Hi\", \"answer\": \"Hi\", \"difficultyLevel\": \"EASY\", \"language\": \"French\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.answer").value("Hi"));
    }

    @Test
    void deleteQuestion() throws Exception {

        Mockito.when(questionService.deleteQuestion(eq(1L))).thenReturn("Question deleted!");
        mockMvc.perform(MockMvcRequestBuilders.delete("/questions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Question deleted!"));

    }

    @Test
    void generateQuestionsForQuiz() throws Exception {
        List<Long> questionIds = Arrays.asList(1L, 2L, 3L);

        // Mock the generateQuestionsForQuiz method in the QuestionService
        Mockito.when(questionService.generateQuestionsForQuiz("EASY", "French", 3)).thenReturn(questionIds);

        // Perform a GET request with query parameters
        mockMvc.perform(MockMvcRequestBuilders.get("/questions/generate")
                        .param("difficultyLevel", "EASY")
                        .param("language", "French")
                        .param("numOfQuestions", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]").value(3));
    }

    @Test
    void getQuestionsForIds() throws Exception {
        QuestionDto question1=new QuestionDto(1L,"Bonjour=","Bye","Hi");
        QuestionDto question2=new QuestionDto(2L,"Aaj","Today","Tomorrow");
        List<QuestionDto> questionList= Arrays.asList(question1,question2);
        Mockito.when(questionService.getQuestionsForIds(Mockito.anyList())).thenReturn(questionList);
        mockMvc.perform(MockMvcRequestBuilders.post("/questions/getQuestions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1,2]"))  // Sending a list of question IDs as JSON
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].questionId").value(1L));


    }

    @Test
    void calculateScore() throws Exception {
        List<Response> responses = Arrays.asList(
                new Response(1L, "A"),
                new Response(2L, "B")
        );
        Mockito.when(questionService.calculateScore(Mockito.anyList())).thenReturn(2);
        mockMvc.perform(MockMvcRequestBuilders.post("/questions/score")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"questionId\":1,\"response\":\"A\"},{\"questionId\":2,\"response\":\"B\"}]"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(2));
    }
}