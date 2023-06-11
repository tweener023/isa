package com.isa.controller;

import com.isa.dto.AppointmentDTO;
import com.isa.dto.FacilityDTO;
import com.isa.dto.UserDTO;
import com.isa.model.Appointments;
import com.isa.model.Facility;
import com.isa.model.User;
import com.isa.service.FacilityService;
import com.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(value = "api/facilites")
public class FacilityController {

    @Autowired
    FacilityService facilityService;

    @Autowired
    UserService userService;


    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<FacilityDTO> getFacility(@PathVariable Integer id) {

        Facility facility = facilityService.findOne(id);

        // facility must exist
        if (facility == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new FacilityDTO(facility), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<FacilityDTO> saveFacility(@RequestBody FacilityDTO facilityDTO) {

        User user = userService.findOne(facilityDTO.getCenterAdmins().getId());

        Facility facility = new Facility();
        facility.setCenterName(facilityDTO.getCenterName());
        facility.setCenterAddress(facilityDTO.getCenterAddress());
        facility.setCenterDescription(facilityDTO.getCenterDescription());
        facility.setCenterAdmins(user);
        facility.setCenterSupplies(facilityDTO.getCenterSupplies());
        facility.setGrade(facilityDTO.getGrade());


        facility = facilityService.save(facility);
        return new ResponseEntity<>(new FacilityDTO(facility), HttpStatus.CREATED);
    }


    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<FacilityDTO> updateFacility(@RequestBody FacilityDTO facilityDTO) {

        // a facility must exist
        Facility facility = facilityService.findOne(facilityDTO.getCenterId());
        User user = userService.findOne(facilityDTO.getCenterAdmins().getId());

        if (facility == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        facility.setCenterName(facilityDTO.getCenterName());
        facility.setCenterAddress(facilityDTO.getCenterAddress());
        facility.setCenterDescription(facilityDTO.getCenterDescription());
        facility.setCenterAdmins(user);
        facility.setCenterSupplies(facilityDTO.getCenterSupplies());
        facility.setGrade(facilityDTO.getGrade());


        facility = facilityService.save(facility);
        return new ResponseEntity<>(new FacilityDTO(facility), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<Void> deleteFacility(@PathVariable Integer id) {

        Facility facility = facilityService.findOne(id);

        if (facility != null) {
            facilityService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{studentId}")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<List<AppointmentDTO>> getUserAppointments(@PathVariable Integer userId) {
        User user = userService.findOne(userId);
        Set<Appointments> appointments = user.getAppointments();
        List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
        for (Appointments e : appointments) {
            AppointmentDTO appointmentDTO = new AppointmentDTO();
            appointmentDTO.setAppointmentId(e.getAppointmentId());
            appointmentDTO.setUser(new UserDTO(e.getUser()));
            appointmentDTO.setFacility(new FacilityDTO(e.getFacilityName()));
            appointmentDTO.setDateOfAppointment(e.getDateOfAppointment());

            appointmentsDTO.add(appointmentDTO);
        }
        return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
    }

    @GetMapping("/userPast/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<List<FacilityDTO>> getUserPastFacilities(@PathVariable Integer userId) {
        User user = userService.findOne(userId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

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

        return new ResponseEntity<>(facilitiesDTO, HttpStatus.OK);
    }

    //change Visibility
    @PutMapping("/{facilityId}/changeVisibility")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<FacilityDTO> ChangeFacilityVisibility(@PathVariable("facilityId") Integer facilityId) {

        // a facility must exist
        Facility facility = facilityService.findOne(facilityId);

        if (facility == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        facility.setAvailable(false);

        facility = facilityService.save(facility);

        return new ResponseEntity<>(new FacilityDTO(facility), HttpStatus.CREATED);
    }

    //change Visibility
    @PutMapping("/{facilityId}/makeVisible")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<FacilityDTO> MakeVisible(@PathVariable("facilityId") Integer facilityId) {

        // a facility must exist
        Facility facility = facilityService.findOne(facilityId);

        if (facility == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        facility.setAvailable(true);

        facility = facilityService.save(facility);

        return new ResponseEntity<>(new FacilityDTO(facility), HttpStatus.CREATED);
    }

}
