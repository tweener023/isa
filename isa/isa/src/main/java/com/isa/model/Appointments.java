package com.isa.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Appointments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentId;

    @Column(name = "userId", unique = true, nullable = true)
    private String userId;

    @JsonFormat(pattern = "yyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "dateOfAppointment", nullable = false)
    private String date;

    @Column(name = "facilityName", nullable = false)
    private String facilityName;


    public Appointments() {
        super();
    }

    public Appointments(Integer appointmentId, String userId, String date, String facilityName) {
        this.appointmentId = appointmentId;
        this.userId = userId;
        this.date = date;
        this.facilityName = facilityName;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointments)) return false;
        Appointments that = (Appointments) o;
        return Objects.equals(getAppointmentId(), that.getAppointmentId()) && Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getDate(), that.getDate()) && Objects.equals(getFacilityName(), that.getFacilityName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppointmentId(), getUserId(), getDate(), getFacilityName());
    }

    @Override
    public String toString() {
        return "Appointments{" +
                "appointmentId=" + appointmentId +
                ", userId='" + userId + '\'' +
                ", date=" + date +
                ", facilityName='" + facilityName + '\'' +
                '}';
    }
}
