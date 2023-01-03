package com.isa.repository;

import com.isa.model.Appointments;
import com.isa.model.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointments, Integer> {
    public Page<Appointments> findAll(Pageable pageable);

}
