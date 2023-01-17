package com.isa.dto;

import com.isa.model.Appointments;
import com.isa.model.Facility;
import com.isa.model.User;

import java.util.Date;

public class AppointmentDTO {

    private Integer appointmentId;
    private UserDTO user;
    private String date;
    private FacilityDTO facility;


    public AppointmentDTO() {

    }


    public AppointmentDTO(Appointments appointments){
        appointmentId = appointments.getAppointmentId();
        user = new UserDTO(appointments.getUser());
        appointments.getDate();
        facility = new FacilityDTO(appointments.getFacilityName());
    }

    public AppointmentDTO(Integer appointmentId, UserDTO userId, String date, FacilityDTO facilityName) {
        this.appointmentId = appointmentId;
        this.user = userId;
        this.date = date;
        this.facility = facilityName;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public UserDTO getUser() {
        return user;
    }

    public String getDate() {
        return date;
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

    public void setDate(String date) {
        this.date = date;
    }

    public void setFacility(FacilityDTO facility) {
        this.facility = facility;
    }
}
