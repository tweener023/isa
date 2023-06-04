package com.isa.controller;

import com.isa.model.AnswerToComplaint;
import com.isa.service.AnswerToComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/answers")
public class AnswerToComplaintController {

    @Autowired
    private AnswerToComplaintService answerToComplaintService;

    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<List<AnswerToComplaint>> getAllAnswerToComplaints() {
        List<AnswerToComplaint> answerToComplaints = answerToComplaintService.getAllAnswerToComplaints();
        return new ResponseEntity<>(answerToComplaints, HttpStatus.OK);
    }
}
