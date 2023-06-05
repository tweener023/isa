package com.isa.controller;

import com.isa.dto.ComplaintDTO;
import com.isa.model.Complaint;
import com.isa.model.Facility;
import com.isa.model.StatusOfComplaint;
import com.isa.model.User;
import com.isa.service.ComplaintService;
import com.isa.service.FacilityService;
import com.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping(value = "api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private UserService userService;

    @Autowired
    private FacilityService facilityService;

    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints() {
        List<Complaint> complaints = complaintService.getAllComplaints();

        List<ComplaintDTO> complaintDTOs = new ArrayList<>();
        for (Complaint complaint : complaints) {
            complaintDTOs.add(new ComplaintDTO(complaint));
        }

        return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<ComplaintDTO> getComplaintById(@PathVariable Integer id) {
        Complaint complaint = complaintService.getComplaintById(id);

        if (complaint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ComplaintDTO(complaint), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<List<ComplaintDTO>> getComplaintsByUserId(@RequestParam Integer userId) {
        User user = userService.findOne(userId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Complaint> complaints = complaintService.getComplaintsByUser(user);

        // Convert complaints to DTOs
        List<ComplaintDTO> complaintDTOs = new ArrayList<>();
        for (Complaint complaint : complaints) {
            complaintDTOs.add(new ComplaintDTO(complaint));
        }

        return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<ComplaintDTO> saveComplaint(@RequestBody ComplaintDTO complaintDTO) {
        User user = userService.findOne(complaintDTO.getUserId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Complaint complaint = new Complaint();
        complaint.setUser(user);
        complaint.setComplaintText(complaintDTO.getComplaintText());
        complaint.setStatusOfComplaint(complaintDTO.getStatusOfComplaint());

        complaint = complaintService.saveComplaint(complaint);

        return new ResponseEntity<>(new ComplaintDTO(complaint), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<ComplaintDTO> updateComplaint(@RequestBody ComplaintDTO complaintDTO) {
        Complaint complaint = complaintService.getComplaintById(complaintDTO.getComplaintId());

        if (complaint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        complaint.setComplaintText(complaintDTO.getComplaintText());
        complaint.setStatusOfComplaint(complaintDTO.getStatusOfComplaint());

        complaint = complaintService.saveComplaint(complaint);

        return new ResponseEntity<>(new ComplaintDTO(complaint), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<Void> deleteComplaint(@PathVariable Integer id) {
        Complaint complaint = complaintService.getComplaintById(id);

        if (complaint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        complaintService.deleteComplaint(complaint);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/createComplaint", consumes = "application/json")
    public ResponseEntity<ComplaintDTO> createComplaint(@RequestBody ComplaintDTO complaintDTO) {
        User user = userService.findOne(complaintDTO.getUserId());
        Set<Facility> userFacilities = user.getFacilities();

        Facility facility = facilityService.findOne(complaintDTO.getFacilityId());
        if (!userFacilities.contains(facility)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Create a new Complaint object
        Complaint complaint = new Complaint();
        complaint.setFacility(facility);
        complaint.setUser(user);
        complaint.setComplaintText("My complaint is about " + facility.getCenterName() + " " + complaintDTO.getComplaintText());
        complaint.setStatusOfComplaint(StatusOfComplaint.WAITING_FOR_RESPONSE);

        // Save the new complaint
        complaint = complaintService.saveComplaint(complaint);

        // Create and return the DTO for the created complaint
        ComplaintDTO createdComplaintDTO = new ComplaintDTO(complaint);
        return new ResponseEntity<>(createdComplaintDTO, HttpStatus.CREATED);
    }
}
