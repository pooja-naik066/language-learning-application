package com.pooja.quiz_service.services;

import com.pooja.quiz_service.feign.QuizInterface;
import com.pooja.quiz_service.model.QuestionDto;
import com.pooja.quiz_service.model.Quiz;
import com.pooja.quiz_service.model.Response;
import com.pooja.quiz_service.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService{
    private QuizRepository quizRepository;
    private QuizInterface quizInterface;
    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository,QuizInterface quizInterface){
        this.quizRepository=quizRepository;
        this.quizInterface=quizInterface;
    }


    public String createQuiz(String difficultyLevel, String language, Integer numOfQuestions, String quizTitle) {
        List<Long> questionIds= quizInterface.generateQuestionsForQuiz(difficultyLevel,language,numOfQuestions).getBody();
        Quiz quiz=new Quiz();
        quiz.setQuizTitle(quizTitle);
        quiz.setQuestionIds(questionIds);
        quizRepository.save(quiz);
        return "SUCCESS";
    }

    @Override
    public List<QuestionDto> getQuestionsForQuizId(Long quizId) {
        Quiz quiz=quizRepository.findById(quizId).get();
        List<Long> questionsIds=quiz.getQuestionIds();
        List<QuestionDto> questionDtoList= quizInterface.getQuestionsForIds(questionsIds).getBody();
        return  questionDtoList;

    }

    @Override
    public Integer calculateScore(Long quizId,  List<Response> responses) {
        Integer score=quizInterface.calculateScore(responses).getBody();
        return score;
    }


}
