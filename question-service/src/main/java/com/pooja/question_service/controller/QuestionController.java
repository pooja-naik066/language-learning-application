package com.pooja.question_service.controller;

import com.pooja.question_service.model.Question;
import com.pooja.question_service.model.QuestionDto;
import com.pooja.question_service.model.Response;
import com.pooja.question_service.services.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService){
        this.questionService=questionService;
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions(){
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long questionId){
        return new ResponseEntity<Question>(questionService.getQuestionById(questionId), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question){
        return new ResponseEntity<Question>(questionService.createQuestion(question),HttpStatus.CREATED);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<Question> updateQuestion(@Valid @PathVariable Long questionId,@RequestBody Question question){
        return new ResponseEntity<Question>(questionService.updateQuestion(questionId,question),HttpStatus.OK);
    }

    @DeleteMapping("{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long questionId){
        return new ResponseEntity<String>(questionService.deleteQuestion(questionId),HttpStatus.OK);

    }

    @GetMapping("generate")
    public ResponseEntity<List<Long>> generateQuestionsForQuiz(@RequestParam String difficultyLevel,
                                                               @RequestParam String language,
                                                               @RequestParam Integer numOfQuestions){
        return new ResponseEntity<List<Long>>(questionService.generateQuestionsForQuiz(difficultyLevel,language,numOfQuestions)
                                                  ,HttpStatus.OK);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionDto>> getQuestionsForIds(@RequestBody List<Long> questionIdList){
        return new ResponseEntity<List<QuestionDto>>(questionService.getQuestionsForIds(questionIdList)
                                                     ,HttpStatus.OK);
    }
    @PostMapping("score")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Response> responses){
        return new ResponseEntity<Integer>(questionService.calculateScore(responses)
                                         ,HttpStatus.OK);

    }

}
