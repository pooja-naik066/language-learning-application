package com.pooja.question_service.services;

import com.pooja.question_service.model.Question;
import com.pooja.question_service.model.QuestionDto;
import com.pooja.question_service.model.Response;
import com.pooja.question_service.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceTest {
    @Mock
    private QuestionRepository questionRepository;
    @InjectMocks
    private QuestionServiceImpl questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initializes the mocks and injects them into the service
    }

    @Test
    void getAllQuestions() {
        Question question1=new Question(1L,"Bonjour=","Bye","Hi","Hi","EASY","French");
        Question question2=new Question(2L,"Aaj","Today","Tomorrow","Today","EASY","Hindi");

        List<Question> questionList= Arrays.asList(question1,question2);
        Mockito.when(questionRepository.findAll()).thenReturn(questionList);
        List<Question> foundQuestions=questionService.getAllQuestions();

        assertEquals(2,foundQuestions.size());
        assertEquals(question1,foundQuestions.get(0));
    }

    @Test
    void getQuestionById() {
        Question question1=new Question(1L,"Bonjour=","Bye","Hi","Hi","EASY","French");
        Mockito.when(questionRepository.findById(question1.getQuestionId())).thenReturn(Optional.of(question1));
        Question foundQuestion=questionService.getQuestionById(1L);
        assertEquals(foundQuestion,question1);
    }

    @Test
    void createQuestion() {
        Question question1=new Question(1L,"Bonjour=","Bye","Hi","Hi","EASY","French");
        Mockito.when(questionRepository.save(question1)).thenReturn(question1);
        Question savedQuestion=questionService.createQuestion(question1);
        assertNotNull(savedQuestion);

    }

    @Test
    void updateQuestion() {

        Question question1=new Question(1L,"Bonjour=","Bye","Hi","Hi","MEDIUM","French");
        Question question2=new Question(1L,"Bonjour=","Bye","Hi","Hi","EASY","French");

        Mockito.when(questionRepository.findById(1L)).thenReturn(Optional.of(question1));
        Mockito.when(questionRepository.save(question1)).thenReturn(question1);
        Question updatedQuestion=questionService.updateQuestion(1L,question2);
        assertEquals("EASY",updatedQuestion.getDifficultyLevel());
    }

    @Test
    void deleteQuestion() {

        Question question1=new Question(1L,"Bonjour=","Bye","Hi","Hi","MEDIUM","French");
        Mockito.when(questionRepository.findById(question1.getQuestionId())).thenReturn(Optional.of(question1));
        String result= questionService.deleteQuestion(1L);
        assertEquals("Question deleted!",result);
    }

    @Test
    void generateQuestionsForQuiz() {
        List<Long> expectedQuestionIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        Mockito.when(questionRepository.findRandomQuestions("EASY", "French", 5))
                .thenReturn(expectedQuestionIds);
        List<Long> generatedQuestionIds=questionService.generateQuestionsForQuiz("EASY", "French", 5);
        assertEquals(5,generatedQuestionIds.size());
        assertEquals(expectedQuestionIds,generatedQuestionIds);
    }

    @Test
    void getQuestionsForIds() {
        List<Long> questionIdList = Arrays.asList(1L, 2L);
        Question question1=new Question(1L,"Bonjour=","Bye","Hi","Hi","EASY","French");
        Question question2=new Question(2L,"Aaj","Today","Tomorrow","Today","EASY","Hindi");
        Mockito.when(questionRepository.findById(1L)).thenReturn(Optional.of(question1));
        Mockito.when(questionRepository.findById(2L)).thenReturn(Optional.of(question2));

        List<QuestionDto> result = questionService.getQuestionsForIds(questionIdList);
        assertEquals(2,result.size());
        assertEquals("Aaj",result.get(1).getQuestion());
    }

    @Test
    void calculateScore() {
        Response response1=new Response(1L,"Hiii");
        Response response2=new Response(2L,"Today");

        Question question1=new Question(1L,"Bonjour=","Bye","Hi","Hi","EASY","French");
        Question question2=new Question(2L,"Aaj","Today","Tomorrow","Today","EASY","Hindi");

        Mockito.when(questionRepository.findById(1L)).thenReturn(Optional.of(question1));
        Mockito.when(questionRepository.findById(2L)).thenReturn(Optional.of(question2));
        Integer score= questionService.calculateScore(Arrays.asList(response1,response2));
        assertEquals(1,score);


    }
}