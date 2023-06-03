package com.isa.service;

import com.isa.model.Appointments;
import com.isa.model.Facility;
import com.isa.model.User;
import com.isa.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    public AppointmentService(AppointmentRepository appointmentRepository, UserService userService) {
        this.appointmentRepository = appointmentRepository;
        this.userService = userService;
    }


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

    @Transactional
    public void updateAppointmentUser(Integer appointmentId) {
        Appointments appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment ID"));

        Facility facility = appointment.getFacilityName();

        // Modify the appointment and facility within the transaction
        User userWithId5 = userService.findOne(5);

        User currentUser = appointment.getUser();
        currentUser.getAppointments().remove(appointment);

        appointment.setUser(userWithId5);

        userWithId5.getAppointments().add(appointment);

        userService.save(currentUser);
        userService.save(userWithId5);
        appointmentRepository.save(appointment);
    }


}
