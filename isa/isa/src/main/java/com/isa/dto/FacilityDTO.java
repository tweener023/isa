package com.isa.dto;

import com.isa.model.Appointments;
import com.isa.model.Facility;
import com.isa.model.User;

public class FacilityDTO {

    private Integer centerId;
    private String centerName;
    private String centerAddress;
    private String centerDescription;
    private Appointments[] centerAppointments;
    private User[] centerAdmins;
    private Integer centerSupplies;


    public FacilityDTO(Facility facility){
        this(facility.getCenterId(), facility.getCenterName(), facility.getCenterAddress(), facility.getCenterDescription(), facility.getCenterAdmins(), facility.getCenterSupplies());
    }

    public FacilityDTO(Integer centerId, String centerName, String centerAddress, String centerDescription,User[] centerAdmins, Integer centerSupplies) {
        this.centerId = centerId;
        this.centerName = centerName;
        this.centerAddress = centerAddress;
        this.centerDescription = centerDescription;
        this.centerAdmins = centerAdmins;
        this.centerSupplies = centerSupplies;
    }

    public Integer getCenterId() {
        return centerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public String getCenterAddress() {
        return centerAddress;
    }

    public String getCenterDescription() {
        return centerDescription;
    }

    public Appointments[] getCenterAppointments() {
        return centerAppointments;
    }

    public User[] getCenterAdmins() {
        return centerAdmins;
    }

    public Integer getCenterSupplies() {
        return centerSupplies;
    }

}
