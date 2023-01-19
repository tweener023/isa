package com.isa.controller;

import com.isa.dto.FacilityDTO;
import com.isa.model.Facility;
import com.isa.service.FacilityService;
import com.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    FacilityService facilityService;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MEDIC') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/medic")
    @PreAuthorize("hasRole('MEDIC')")
    public String moderatorAccess() {
        return "Medic Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping(value = "/facilites/all")
    public ResponseEntity<List<FacilityDTO>> getAllFacilities(){
        List <Facility> facilities = facilityService.findAll();

        //convert facilities to dtos
        List<FacilityDTO> facilitiesDTO = new ArrayList<>();
        for(Facility f : facilities){
            facilitiesDTO.add(new FacilityDTO(f));
        }

        return new ResponseEntity<>(facilitiesDTO, HttpStatus.OK);
    }
}
