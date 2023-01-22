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
import org.springframework.web.bind.annotation.*;

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

    /*

    // GET /api/facilities?page=0&size=5&sort=centerName,DESC
    @GetMapping
    public ResponseEntity<List<FacilityDTO>> getfacilitiesPage(Pageable page) {

        // page object holds data about pagination and sorting
        // the object is created based on the url parameters "page", "size" and "sort"
        Page<Facility> facilities = facilityService.findAll(page);

        // convert users to DTOs
        List<FacilityDTO> facilitiesDTO = new ArrayList<>();
        for (Facility f : facilities) {
            facilitiesDTO.add(new FacilityDTO(f));
        }

        return new ResponseEntity<>(facilitiesDTO, HttpStatus.OK);
    }

     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<FacilityDTO> getFacility(@PathVariable Integer id) {

        Facility facility = facilityService.findOne(id);

        // facility must exist
        if (facility == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new FacilityDTO(facility), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<FacilityDTO> saveFacility(@RequestBody FacilityDTO facilityDTO) {

        User user = userService.findOne(facilityDTO.getCenterAdmins().getId());

        Facility facility = new Facility();
        facility.setCenterName(facilityDTO.getCenterName());
        facility.setCenterAddress(facilityDTO.getCenterAddress());
        facility.setCenterDescription(facilityDTO.getCenterDescription());
        facility.setCenterAdmins(user);
        facility.setCenterSupplies(facilityDTO.getCenterSupplies());


        facility = facilityService.save(facility);
        return new ResponseEntity<>(new FacilityDTO(facility), HttpStatus.CREATED);
    }


    @PutMapping(consumes = "application/json")
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


        facility = facilityService.save(facility);
        return new ResponseEntity<>(new FacilityDTO(facility), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
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
    public ResponseEntity<List<AppointmentDTO>> getUserAppointments(@PathVariable Integer userId) {
        User user = userService.findOne(userId);
        Set<Appointments> appointments = user.getAppointments();
        List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
        for (Appointments e : appointments) {
            AppointmentDTO appointmentDTO = new AppointmentDTO();
            appointmentDTO.setAppointmentId(e.getAppointmentId());
            appointmentDTO.setUser(new UserDTO(e.getUser()));
            appointmentDTO.setFacility(new FacilityDTO(e.getFacilityName()));
            appointmentDTO.setDate(e.getDate());

            appointmentsDTO.add(appointmentDTO);
        }
        return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
    }

}
