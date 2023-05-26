package com.isa.service;

import com.isa.model.Questionnaire;
import com.isa.repository.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class QuestionnaireService {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    public Questionnaire findByUser(Integer id) { return questionnaireRepository.findByUser(id);}

    public Questionnaire save(Questionnaire questionnaire) { return questionnaireRepository.save(questionnaire); }
}
