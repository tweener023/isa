package com.isa.controller;

import com.isa.dto.AppointmentDTO;
import com.isa.dto.ComplaintDTO;
import com.isa.dto.FacilityDTO;
import com.isa.model.*;
import com.isa.service.ComplaintService;
import com.isa.service.FacilityService;
import com.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
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
        complaint.setDirectedTo(complaintDTO.getDirectedTo());

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
        complaint.setDirectedTo(complaintDTO.getDirectedTo());

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

    @PostMapping(value = "/sendComplaint",consumes = "application/json")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<ComplaintDTO> sendComplaint(@RequestBody ComplaintDTO complaintDTO) {
        User user = userService.findOne(complaintDTO.getUserId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<FacilityDTO> pastFacilities = getPastFacilitiesForUser(user);

        // Check if the complaint facility exists in past facilities
        FacilityDTO complaintFacility = null;
        for (FacilityDTO facilityDTO : pastFacilities) {
            if (facilityDTO.getCenterId().equals(complaintDTO.getFacilityId())) {
                complaintFacility = facilityDTO;
                break;
            }
        }

        if (complaintFacility == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Facility facility = facilityService.findOne(complaintFacility.getCenterId());

        if (facility == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Complaint complaint = new Complaint();
        complaint.setFacility(facility);
        complaint.setUser(user);
        complaint.setDirectedTo(complaintDTO.getDirectedTo());
        if (complaintDTO.getDirectedTo() == DirectedTo.FACILITY) {
            complaint.setComplaintText("Complaint about " + complaintFacility.getCenterName() + ": " + complaintDTO.getComplaintText());
        } else {
            complaint.setComplaintText("Complaint about staff lead by " + complaintFacility.getCenterAdmins().getFirstName() + " " + complaintFacility.getCenterAdmins().getLastName() + ": " + complaintDTO.getComplaintText());

        }
        complaint.setStatusOfComplaint(StatusOfComplaint.WAITING_FOR_RESPONSE);

        complaint = complaintService.saveComplaint(complaint);

        return new ResponseEntity<>(new ComplaintDTO(complaint), HttpStatus.CREATED);
    }

    @GetMapping(value = "/waitingForResponse")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaintsWaitingForResponse() {
        List<Complaint> complaints = complaintService.getAllComplaints();

        List<ComplaintDTO> complaintDTOs = new ArrayList<>();
        for (Complaint complaint : complaints) {
            if (complaint.getStatusOfComplaint() == StatusOfComplaint.WAITING_FOR_RESPONSE) {
                complaintDTOs.add(new ComplaintDTO(complaint));
            }
        }

        return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
    }

    public static List<FacilityDTO> getPastFacilitiesForUser(User user) {
        Set<Appointments> appointments = user.getAppointments();
        List<FacilityDTO> facilitiesDTO = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        for (Appointments appointment : appointments) {
            LocalDate appointmentDate = appointment.getDateOfAppointment();
            LocalTime appointmentTime = appointment.getTimeOfAppointment();

            if (appointmentDate.isBefore(currentDate) || (appointmentDate.equals(currentDate) && appointmentTime.isBefore(currentTime))) {
                FacilityDTO facilityDTO = new FacilityDTO(appointment.getFacilityName());
                facilitiesDTO.add(facilityDTO);
            }
        }

        return facilitiesDTO;
    }
}
