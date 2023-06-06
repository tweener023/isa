package com.isa.controller;


import com.isa.dto.AnalyticsDTO;
import com.isa.dto.FacilityDTO;
import com.isa.model.Analytics;
import com.isa.model.Facility;
import com.isa.service.AnalyticsService;
import com.isa.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "api/analytics")
public class AnalyticsController {

    @Autowired
    AnalyticsService analyticsService;

    @Autowired
    FacilityService facilityService;


    @GetMapping(value = "/{id}/getAnalytics")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
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
