package com.isa.service;

import com.isa.model.AnswerToComplaint;
import com.isa.repository.AnswerToComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerToComplaintService {

    @Autowired
    AnswerToComplaintRepository answerToComplaintRepository;

    public List<AnswerToComplaint> getAllAnswerToComplaints() {
        return answerToComplaintRepository.findAll();
    }

    public AnswerToComplaint getAnswerToComplaintById(Integer id) {
        return answerToComplaintRepository.findById(id).orElse(null);
    }

    public AnswerToComplaint saveAnswerToComplaint(AnswerToComplaint answerToComplaint) {
        return answerToComplaintRepository.save(answerToComplaint);
    }

    public void deleteAnswerToComplaint(AnswerToComplaint answerToComplaint) {
        answerToComplaintRepository.delete(answerToComplaint);
    }
}
