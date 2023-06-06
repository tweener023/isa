package com.isa.service;

import com.isa.model.Analytics;
import com.isa.model.Facility;
import com.isa.model.User;
import com.isa.repository.AnalyticsRepository;
import com.isa.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {

    @Autowired
    private AnalyticsRepository analyticsRepository;


    public List<Analytics> findAll() {
        return analyticsRepository.findAll();
    }

    public Analytics findOne(Long id) {
        return analyticsRepository.findById(id).orElseGet(null);
    }

    public Analytics save(Analytics analytics) {
        return analyticsRepository.save(analytics);
    }

    public void remove(Long id) {
        analyticsRepository.deleteById(id);
    }

    public Analytics findOneByFacility(Facility fac){return analyticsRepository.findOneByFacility(fac);}


}
