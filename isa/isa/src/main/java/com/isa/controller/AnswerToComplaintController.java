package com.isa.controller;

import com.isa.dto.AnswerToComplaintDTO;
import com.isa.model.AnswerToComplaint;
import com.isa.model.Complaint;
import com.isa.model.StatusOfComplaint;
import com.isa.service.AnswerToComplaintService;
import com.isa.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8081"})
@RequestMapping(value = "api/answers")
public class AnswerToComplaintController {

    @Autowired
    private AnswerToComplaintService answerToComplaintService;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private JavaMailSender javaMailSender;

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

    @PostMapping(value = "/answer")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    @Transactional
    public ResponseEntity<AnswerToComplaintDTO> answer(@RequestBody AnswerToComplaintDTO answerToComplaintDTO) {
        Integer complaintId = answerToComplaintDTO.getComplaintId();

        Complaint complaint = complaintService.getComplaintById(complaintId);

        if (complaint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AnswerToComplaint answerToComplaint = new AnswerToComplaint();
        answerToComplaint.setComplaint(complaint);
        answerToComplaint.setAnswerToComplaintText(answerToComplaintDTO.getAnswerToComplaintText());

        answerToComplaint = answerToComplaintService.saveAnswerToComplaint(answerToComplaint);

        complaint.setStatusOfComplaint(StatusOfComplaint.RESPONDED_TO); // Assuming you have a ComplaintStatus enum
        complaintService.saveComplaint(complaint);

        // Send email to user
        String userEmail = complaint.getUser().getEmail(); // Assuming you have a getEmail() method in the User class
        String emailSubject = "Response to your complaint";
        String emailText = "Dear User,\n\nYour complaint has been answered. Here is the response:\n\n" + answerToComplaintDTO.getAnswerToComplaintText();

        sendEmail(userEmail, emailSubject, emailText);

        return new ResponseEntity<>(new AnswerToComplaintDTO(answerToComplaint), HttpStatus.CREATED);
    }

    private void sendEmail(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    @GetMapping(value = "/byComplaint")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<AnswerToComplaintDTO> getAnswerByComplaint(@RequestParam Integer complaintId) {
        Complaint complaint = complaintService.getComplaintById(complaintId);

        if (complaint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AnswerToComplaint answerToComplaint = answerToComplaintService.getAnswerComplaintId(complaintId);
        if (answerToComplaint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AnswerToComplaintDTO(answerToComplaint), HttpStatus.OK);
    }
}
