package com.pooja.question_service.repositories;

import com.pooja.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    @Query(value = "SELECT q.question_id FROM question q WHERE q.language=:language" +
            " AND q.difficulty_level=:difficultyLevel ORDER BY RAND() LIMIT :numOfQuestions",nativeQuery = true)
    List<Long> findRandomQuestions(@Param("difficultyLevel") String difficultyLevel,@Param("language")  String language,@Param("numOfQuestions") Integer  numOfQuestions);
}
