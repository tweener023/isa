package com.isa.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer centerId;

    @Column(name = "centerName", unique = true, nullable = false)
    private String centerName;

    @Column(name = "centerAddress", nullable = false)
    private String centerAddress;

    @Column(name = "centerDescription", nullable = false)
    private String centerDescription;

    @Column(name = "centerAppointments" /*, nullable = false*/)
    private Appointments[] centerAppointments;

    @Column(name = "centerAdmins"/*, nullable = false*/)
    private User[] centerAdmins;

    @Column(name = "centerSupplies", unique = true, nullable = false)
    private Integer centerSupplies;

    public Facility() {super();}

    public Facility(Integer centerId, String centerName, String centerAddress, String centerDescription, Appointments[] centerAppointments, User[] centerAdmins, Integer centerSupplies) {
        this.centerId = centerId;
        this.centerName = centerName;
        this.centerAddress = centerAddress;
        this.centerDescription = centerDescription;
        this.centerAppointments = centerAppointments;
        this.centerAdmins = centerAdmins;
        this.centerSupplies = centerSupplies;
    }

    public Integer getCenterId() {
        return centerId;
    }

    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCenterAddress() {
        return centerAddress;
    }

    public void setCenterAddress(String centerAddress) {
        this.centerAddress = centerAddress;
    }

    public String getCenterDescription() {
        return centerDescription;
    }

    public void setCenterDescription(String centerDescription) {
        this.centerDescription = centerDescription;
    }

    public Appointments[] getCenterAppointments() {
        return centerAppointments;
    }

    public void setCenterAppointments(Appointments[] centerAppointments) {
        this.centerAppointments = centerAppointments;
    }

    public User[] getCenterAdmins() {
        return centerAdmins;
    }

    public void setCenterAdmins(User[] centerAdmins) {
        this.centerAdmins = centerAdmins;
    }

    public Integer getCenterSupplies() {
        return centerSupplies;
    }

    public void setCenterSupplies(Integer centerSupplies) {
        this.centerSupplies = centerSupplies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Facility)) return false;
        Facility facility = (Facility) o;
        return Objects.equals(getCenterId(), facility.getCenterId()) && Objects.equals(getCenterName(), facility.getCenterName()) && Objects.equals(getCenterAddress(), facility.getCenterAddress()) && Objects.equals(getCenterDescription(), facility.getCenterDescription()) && Arrays.equals(getCenterAppointments(), facility.getCenterAppointments()) && Arrays.equals(getCenterAdmins(), facility.getCenterAdmins()) && Objects.equals(getCenterSupplies(), facility.getCenterSupplies());
    }

    @Override
    public String toString() {
        return "Facility{" +
                "centerId=" + centerId +
                ", centerName='" + centerName + '\'' +
                ", centerAddress='" + centerAddress + '\'' +
                ", centerDescription='" + centerDescription + '\'' +
                ", centerAppointments=" + Arrays.toString(centerAppointments) +
                ", centerAdmins=" + Arrays.toString(centerAdmins) +
                ", centerSupplies=" + centerSupplies +
                '}';
    }

}