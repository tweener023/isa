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
    private UserDTO centerAdmins;
    private Integer centerSupplies;


    public FacilityDTO(Facility facility) {
        centerId = facility.getCenterId();
        centerName = facility.getCenterName();
        centerAddress = facility.getCenterAddress();
        centerDescription = facility.getCenterDescription();
        centerAdmins = new UserDTO(facility.getCenterAdmins()) ;
        centerSupplies = facility.getCenterSupplies();
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

    public UserDTO getCenterAdmins() {
        return centerAdmins;
    }

    public Integer getCenterSupplies() {
        return centerSupplies;
    }

}
