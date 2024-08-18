package com.pooja.quiz_service.controller;

import com.pooja.quiz_service.model.QuestionDto;
import com.pooja.quiz_service.model.QuizDto;
import com.pooja.quiz_service.model.Response;
import com.pooja.quiz_service.services.QuizService;
import com.pooja.quiz_service.services.QuizServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    private QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService){
        this.quizService=quizService;
    }

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        return new ResponseEntity<String>(quizService.createQuiz(quizDto.getDifficultyLevel()
                , quizDto.getLanguage(),quizDto.getNumOfQuestions(),
                quizDto.getQuizTitle()),HttpStatus.OK);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsForQuizId(@PathVariable Long quizId){
        return new ResponseEntity<List<QuestionDto>>(quizService.getQuestionsForQuizId(quizId)
                                           ,HttpStatus.FOUND);
    }

    @PostMapping("submit/{quizId}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Long quizId,@RequestBody List<Response> responses){
        return new ResponseEntity<Integer>(quizService.calculateScore(quizId,responses),
                                           HttpStatus.OK);

    }

}
