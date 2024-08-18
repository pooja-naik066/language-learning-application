package com.pooja.quiz_service.feign;

import com.pooja.quiz_service.model.QuestionDto;
import com.pooja.quiz_service.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    @GetMapping("questions/generate")
    public ResponseEntity<List<Long>> generateQuestionsForQuiz(@RequestParam String difficultyLevel,
                                                               @RequestParam String language,
                                                               @RequestParam Integer numOfQuestions);

    @PostMapping("questions/getQuestions")
    public ResponseEntity<List<QuestionDto>> getQuestionsForIds(@RequestBody List<Long> questionIdList);

    @PostMapping("questions/score")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Response> responses);

}
