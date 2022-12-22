package com.isa.service;

import com.isa.model.Facility;
import com.isa.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;


    public List<Facility> findAll() {
        return facilityRepository.findAll();
    }

    public Facility findOne(Integer id) {
        return facilityRepository.findById(id).orElseGet(null);
    }

    public Facility save(Facility facility) {
        return facilityRepository.save(facility);
    }

    public void remove(Integer id) {
         facilityRepository.deleteById(id);
    }
}
