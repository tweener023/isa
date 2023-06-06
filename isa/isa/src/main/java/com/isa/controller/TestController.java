package com.isa.controller;

import com.isa.dto.AnalyticsDTO;
import com.isa.dto.AppointmentDTO;
import com.isa.dto.FacilityDTO;
import com.isa.model.Analytics;
import com.isa.model.Appointments;
import com.isa.model.Facility;
import com.isa.service.AnalyticsService;
import com.isa.service.AppointmentService;
import com.isa.service.FacilityService;
import com.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    FacilityService facilityService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    AnalyticsService analyticsService;


    @Autowired
    UserService userService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MEDIC') or hasRole('ADMINISTRATOR')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/medic")
    @PreAuthorize("hasRole('MEDIC')")
    public String moderatorAccess() {
        return "Medic Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping(value = "/facilities/all")
    public ResponseEntity<List<FacilityDTO>> getAllFacilities(){
        List <Facility> facilities = facilityService.findAll();

        List<FacilityDTO> facilitiesDTO = new ArrayList<>();
        for(Facility f : facilities){
            facilitiesDTO.add(new FacilityDTO(f));
        }

        return new ResponseEntity<>(facilitiesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/appointments/all")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List <Appointments> appointments = appointmentService.findAll();

        List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
        for(Appointments a : appointments) {
            appointmentsDTO.add(new AppointmentDTO(a));
        }

        return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/appointments/byCenter/{centerId}")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByCenter(@PathVariable Integer centerId) {
        Facility center = facilityService.findOne(centerId);
        if (center == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Appointments> appointments = appointmentService.getAppointmentsByCenter(center);

        List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
        for (Appointments a : appointments) {
            appointmentsDTO.add(new AppointmentDTO(a));
        }

        return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/getAnalytics")
    public ResponseEntity<AnalyticsDTO> getFacility(@PathVariable Integer id) {

        System.out.println("----------------------------------------------------------------------------------" );
        System.out.println("EVO GA ID " + id);
        System.out.println("----------------------------------------------------------------------------------" );

        Facility fac = facilityService.findOne(id);
        if (fac == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Analytics analytics = analyticsService.findOneByFacility(fac);

        if (analytics == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AnalyticsDTO(analytics), HttpStatus.OK);
    }

}
