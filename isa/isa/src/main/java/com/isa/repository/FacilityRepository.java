package com.isa.repository;

import com.isa.model.Facility;
import com.isa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FacilityRepository extends JpaRepository<Facility, Integer> {

    public Page<Facility> findAll(Pageable pageable);


}
