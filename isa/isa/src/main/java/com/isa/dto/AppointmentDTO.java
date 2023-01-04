package com.isa.dto;

import com.isa.model.Appointments;
import com.isa.model.Facility;

import java.util.Date;

public class AppointmentDTO {

    private Integer appointmentId;
    private String userId;
    private String date;
    private String facilityName;



    public AppointmentDTO(Appointments appointments){
        this(appointments.getAppointmentId(), appointments.getUserId(), appointments.getDate(), appointments.getFacilityName());
    }

    public AppointmentDTO(Integer appointmentId, String userId, String date, String facilityName) {
        this.appointmentId = appointmentId;
        this.userId = userId;
        this.date = date;
        this.facilityName = facilityName;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public String getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }

    public String getFacilityName() {
        return facilityName;
    }
}