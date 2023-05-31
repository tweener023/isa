package com.isa.dto;

import com.isa.model.Appointments;
import com.isa.model.Facility;
import com.isa.model.User;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class AppointmentDTO {

    private Integer appointmentId;
    private UserDTO user;
    private LocalDate dateOfAppointment;
    private LocalTime timeOfAppointment;
    private FacilityDTO facility;

    public AppointmentDTO() {

    }

    public AppointmentDTO(Appointments appointments){
        appointmentId = appointments.getAppointmentId();
        user = new UserDTO(appointments.getUser());
        dateOfAppointment = appointments.getDateOfAppointment();
        timeOfAppointment = appointments.getTimeOfAppointment();
        facility = new FacilityDTO(appointments.getFacilityName());
    }

    public AppointmentDTO(Integer appointmentId, UserDTO userId, LocalDate dateOfAppointment, LocalTime timeOfAppointment, FacilityDTO facilityName) {
        this.appointmentId = appointmentId;
        this.user = userId;
        this.dateOfAppointment = dateOfAppointment;
        this.timeOfAppointment = timeOfAppointment;
        this.facility = facilityName;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public UserDTO getUser() {
        return user;
    }

    public LocalDate getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(LocalDate dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public FacilityDTO getFacility() {
        return facility;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }



    public void setFacility(FacilityDTO facility) {
        this.facility = facility;
    }

    public LocalTime getTimeOfAppointment() {
        return timeOfAppointment;
    }

    public void setTimeOfAppointment(LocalTime timeOfAppointment) {
        this.timeOfAppointment = timeOfAppointment;
    }
}
