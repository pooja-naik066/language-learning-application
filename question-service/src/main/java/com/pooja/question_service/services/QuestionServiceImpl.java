package com.pooja.question_service.services;

import com.pooja.question_service.exception.QuestionException;
import com.pooja.question_service.model.Question;
import com.pooja.question_service.model.QuestionDto;
import com.pooja.question_service.model.Response;
import com.pooja.question_service.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements  QuestionService {

    private QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository){
        this.questionRepository=questionRepository;
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestionById(Long questionId) {
        Optional<Question> question=questionRepository.findById(questionId);
        if(question.isEmpty())
            throw new QuestionException("Question not found");
        return question.get();

    }

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Long questionId, Question question) {
        if(question==null){
            throw new QuestionException("Question cannot be Null");
        }
        Optional<Question> tempQuestion=questionRepository.findById(questionId);
        if(tempQuestion.isEmpty())
            throw new RuntimeException("Question not found");
        tempQuestion.get().setQuestion(question.getQuestion());
        tempQuestion.get().setFirstOption(question.getFirstOption());
        tempQuestion.get().setSecondOption(question.getSecondOption());
        tempQuestion.get().setAnswer(question.getAnswer());
        tempQuestion.get().setDifficultyLevel(question.getDifficultyLevel());
        tempQuestion.get().setLanguage(question.getLanguage());
        return questionRepository.save(tempQuestion.get());
    }

    @Override
    public String deleteQuestion(Long questionId) {
        Optional<Question> question=questionRepository.findById(questionId);
        if(question.isEmpty())
            throw new QuestionException("Question not found");
        questionRepository.deleteById(questionId);
        return "Question deleted!";
    }

    @Override
    public List<Long> generateQuestionsForQuiz(String difficultyLevel, String language, Integer numOfQuestions) {
     List<Long> questionIdList=questionRepository.findRandomQuestions(difficultyLevel,language,numOfQuestions);
     return questionIdList;
    }

    @Override
    public List<QuestionDto> getQuestionsForIds(List<Long> questionIdList) {
        List<Question> questions=new ArrayList<>();
        List<QuestionDto> testQuestions=new ArrayList<>();
        for(Long id:questionIdList){
            questions.add(questionRepository.findById(id).get());
        }
        for(Question question:questions){
            QuestionDto questionDto=new QuestionDto();
            questionDto.setQuestionId(question.getQuestionId());
            questionDto.setQuestion(question.getQuestion());
            questionDto.setFirstOption(question.getFirstOption());
            questionDto.setSecondOption(question.getSecondOption());
            testQuestions.add(questionDto);
        }
        return testQuestions;
    }

    @Override
    public Integer calculateScore(List<Response> responses) {
        int score=0;
        for(Response response:responses){
            Optional<Question> question=questionRepository.findById(response.getQuestionId());
            if(response.getResponse().equals(question.get().getAnswer())){
                score++;
            }
        }
        return  score;
    }
}
