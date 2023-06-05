package com.isa.controller;

import com.isa.dto.AnswerToComplaintDTO;
import com.isa.model.AnswerToComplaint;
import com.isa.model.Complaint;
import com.isa.service.AnswerToComplaintService;
import com.isa.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/answers")
public class AnswerToComplaintController {

    @Autowired
    private AnswerToComplaintService answerToComplaintService;

    @Autowired
    private ComplaintService complaintService;

    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<List<AnswerToComplaintDTO>> getAllAnswerToComplaints() {
        List<AnswerToComplaint> answerToComplaints = answerToComplaintService.getAllAnswerToComplaints();

        List<AnswerToComplaintDTO> answerToComplaintDTOs = new ArrayList<>();
        for (AnswerToComplaint answerToComplaint : answerToComplaints) {
            answerToComplaintDTOs.add(new AnswerToComplaintDTO(answerToComplaint));
        }

        return new ResponseEntity<>(answerToComplaintDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<AnswerToComplaintDTO> getAnswerToComplaintById(@PathVariable Integer id) {
        AnswerToComplaint answerToComplaint = answerToComplaintService.getAnswerToComplaintById(id);

        if (answerToComplaint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AnswerToComplaintDTO(answerToComplaint), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<AnswerToComplaintDTO> saveAnswerToComplaint(@RequestBody AnswerToComplaintDTO answerToComplaintDTO) {
        AnswerToComplaint answerToComplaint = new AnswerToComplaint();

        // Retrieve the Complaint object based on the complaintId
        Complaint complaint = complaintService.getComplaintById(answerToComplaintDTO.getComplaintId());

        if (complaint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        answerToComplaint.setComplaint(complaint);
        answerToComplaint.setAnswerToComplaintText(answerToComplaintDTO.getAnswerToComplaintText());

        answerToComplaint = answerToComplaintService.saveAnswerToComplaint(answerToComplaint);

        return new ResponseEntity<>(new AnswerToComplaintDTO(answerToComplaint), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<AnswerToComplaintDTO> updateAnswerToComplaint(@RequestBody AnswerToComplaintDTO answerToComplaintDTO) {
        AnswerToComplaint answerToComplaint = answerToComplaintService.getAnswerToComplaintById(answerToComplaintDTO.getId());

        if (answerToComplaint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Complaint complaint = complaintService.getComplaintById(answerToComplaintDTO.getComplaintId());

        if (complaint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        answerToComplaint.setComplaint(complaint);
        answerToComplaint.setAnswerToComplaintText(answerToComplaintDTO.getAnswerToComplaintText());

        answerToComplaint = answerToComplaintService.saveAnswerToComplaint(answerToComplaint);

        return new ResponseEntity<>(new AnswerToComplaintDTO(answerToComplaint), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<Void> deleteAnswerToComplaint(@PathVariable Integer id) {
        AnswerToComplaint answerToComplaint = answerToComplaintService.getAnswerToComplaintById(id);

        if (answerToComplaint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        answerToComplaintService.deleteAnswerToComplaint(answerToComplaint);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
