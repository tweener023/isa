package com.isa.service;

import com.isa.model.Questionnaire;
import com.isa.model.User;
import com.isa.repository.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionnaireService {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    public Questionnaire findOne(Integer id) { return questionnaireRepository.findById(id).orElseGet(null);}

    public List<Questionnaire> findAll() { return questionnaireRepository.findAll();}

    public Questionnaire findByUser(User user) { return questionnaireRepository.findByUser(user);}

    public Questionnaire save(Questionnaire questionnaire) { return questionnaireRepository.save(questionnaire); }
}
