package com.isa.controller;

import com.isa.dto.AppointmentDTO;
import com.isa.dto.FacilityDTO;
import com.isa.dto.UserDTO;
import com.isa.model.Appointments;
import com.isa.model.Facility;
import com.isa.model.User;
import com.isa.service.AppointmentService;
import com.isa.service.FacilityService;
import com.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping(value = "api/appointments")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    UserService userService;

    @Autowired
    FacilityService facilityService;

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



        // a new exam must have student and course defined
        if (appointmentDTO.getFacility() == null ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.findOne(appointmentDTO.getUser().getId());
        Facility facility = facilityService.findOne(appointmentDTO.getFacility().getCenterId());

        if (user == null || facility == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Appointments appointments = new Appointments();
        appointments.setAppointmentId(appointmentDTO.getAppointmentId());
        appointments.setDate(appointmentDTO.getDate());
        appointments.setFacility(facility);
        appointments.setUser(user);



        appointments = appointmentService.save(appointments);

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

    @GetMapping(value = "facility/{centerId}")
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public ResponseEntity<List<AppointmentDTO>> getFacilityAppointments(@PathVariable Integer centerId) {
        Facility facility = facilityService.findOne(centerId);

        Set<Appointments> appointments = facility.getCenterAppointments();
        List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
        for (Appointments e : appointments) {
            AppointmentDTO appointmentDTO = new AppointmentDTO();
            appointmentDTO.setAppointmentId(e.getAppointmentId());

            if (e.getUser()!= null){
                appointmentDTO.setUser(new UserDTO(e.getUser()));
            }
            appointmentDTO.setFacility(new FacilityDTO(e.getFacilityName()));
            appointmentDTO.setDate(e.getDate());

            appointmentsDTO.add(appointmentDTO);
        }

        return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
    }
}
