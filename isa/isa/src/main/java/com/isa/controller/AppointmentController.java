package com.isa.controller;

import com.isa.dto.AppointmentDTO;
import com.isa.dto.FacilityDTO;
import com.isa.model.Appointments;
import com.isa.model.Facility;
import com.isa.service.AppointmentService;
import com.isa.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/appointments")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;


    @GetMapping(value = "/all")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments(){
        List <Appointments> appointment = appointmentService.findAll();

        //convert facilities to dtos
        List<AppointmentDTO> appointmentDTO = new ArrayList<>();
        for(Appointments a : appointment){
            appointmentDTO.add(new AppointmentDTO(a));
        }

        return new ResponseEntity<>(appointmentDTO, HttpStatus.OK);
    }

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
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable Integer id) {

        Appointments appointment = appointmentService.findOne(id);

        // facility must exist
        if (appointment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AppointmentDTO> saveAppointment(@RequestBody AppointmentDTO appointmentDTO) {

        Appointments appointments = new Appointments();
        appointments.setAppointmentId(appointmentDTO.getAppointmentId());
        appointments.setDate(appointmentDTO.getDate());
        appointments.setFacilityName(appointmentDTO.getFacilityName());
        appointments.setUserId(appointmentDTO.getUserId());

        return new ResponseEntity<>(new AppointmentDTO(appointments), HttpStatus.CREATED);
    }


    @PutMapping(consumes = "application/json")
    public ResponseEntity<AppointmentDTO> updateAppointment(@RequestBody AppointmentDTO appointmentDTO) {

        // a appointment must exist
        Appointments appointment = appointmentService.findOne(appointmentDTO.getAppointmentId());

        if (appointment == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        appointment.setAppointmentId(appointmentDTO.getAppointmentId());
        appointment.setDate(appointmentDTO.getDate());
        appointment.setUserId(appointmentDTO.getUserId());
        appointment.setFacilityName(appointmentDTO.getFacilityName());

        appointment = appointmentService.save(appointment);
        return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Integer id) {

        Appointments appointment = appointmentService.findOne(id);

        if (appointment != null) {
            appointmentService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
