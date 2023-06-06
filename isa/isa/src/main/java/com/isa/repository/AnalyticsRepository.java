package com.isa.repository;


import com.isa.model.Analytics;
import com.isa.model.Facility;
import com.isa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {

    public Page<Analytics> findAll(Pageable pageable);

    public Analytics findOneByFacility(Facility id);

}