package com.isa.service;

import com.isa.model.Appointments;
import com.isa.model.Facility;
import com.isa.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;


    public List<Appointments> findAll() {
        return appointmentRepository.findAll();
    }

    public Appointments findOne(Integer id) {
        return appointmentRepository.findById(id).orElseGet(null);
    }

    public Appointments save(Appointments appointments) {
        return appointmentRepository.save(appointments);
    }

    public void remove(Integer id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointments> getAppointmentsByCenter(Facility center) {
        return appointmentRepository.findByCenter(center);
    }
}
