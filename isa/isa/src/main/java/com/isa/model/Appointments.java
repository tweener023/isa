package com.isa.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

@Entity
public class Appointments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentId;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "dateOfAppointment", nullable = false)
    private LocalDate dateOfAppointment;

    @Column(name = "timeOfAppointment", nullable = false)
    private LocalTime timeOfAppointment;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "center_id")
    private Facility center;


    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = userId;
    }

    public LocalDate getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(LocalDate dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public LocalTime getTimeOfAppointment() {
        return timeOfAppointment;
    }

    public void setTimeOfAppointment(LocalTime timeOfAppointment) {
        this.timeOfAppointment = timeOfAppointment;
    }

    public Facility getFacilityName() {
        return center;
    }

    public void setFacility(Facility facilityName) {
        this.center = facilityName;
    }

}
