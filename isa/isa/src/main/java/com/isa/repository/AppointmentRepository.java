package com.isa.repository;

import com.isa.model.Appointments;
import com.isa.model.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointments, Integer> {
    public Page<Appointments> findAll(Pageable pageable);

    List<Appointments> findByCenter(Facility center);

    Optional<Appointments> findById(Integer appointmentId);
}
