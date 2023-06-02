package com.isa.repository;

import com.isa.model.Questionnaire;
import com.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Integer> {
    Optional<Questionnaire> findById(String id);

    Questionnaire findByUser(User user);
}
