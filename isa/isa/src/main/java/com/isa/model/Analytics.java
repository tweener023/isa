package com.isa.model;


import javax.persistence.*;

@Entity
@Table(name = "analytics")
public class Analytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float averageGrade ;

    private Integer appointmentsLastMonth;

    private Integer appointmentsLastYear;

    private Integer appointmentsTotal;

    private Integer suppliesLastMonth;

    private Integer suppliesLastYear;

    private Integer suppliesTotal;

    private Integer equipmentLastMonth;

    private Integer equipmentLastYear;

    private Integer equipmentTotal;


    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "center_id")
    private Facility facility;

    public Analytics() {
    }

    public Analytics(Float averageGrade, Integer appointmentsLastMonth, Integer appointmentsLastYear, Integer appointmentsTotal, Integer suppliesLastMonth, Integer suppliesLastYear, Integer suppliesTotal, Integer equipmentLastMonth, Integer equipmentLastYear, Integer equipmentTotal, Facility facility) {
        this.averageGrade = averageGrade;
        this.appointmentsLastMonth = appointmentsLastMonth;
        this.appointmentsLastYear = appointmentsLastYear;
        this.appointmentsTotal = appointmentsTotal;
        this.suppliesLastMonth = suppliesLastMonth;
        this.suppliesLastYear = suppliesLastYear;
        this.suppliesTotal = suppliesTotal;
        this.equipmentLastMonth = equipmentLastMonth;
        this.equipmentLastYear = equipmentLastYear;
        this.equipmentTotal = equipmentTotal;
        this.facility = facility;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Float averageGrade) {
        this.averageGrade = averageGrade;
    }

    public Integer getAppointmentsLastMonth() {
        return appointmentsLastMonth;
    }

    public void setAppointmentsLastMonth(Integer appointmentsLastMonth) {
        this.appointmentsLastMonth = appointmentsLastMonth;
    }

    public Integer getAppointmentsLastYear() {
        return appointmentsLastYear;
    }

    public void setAppointmentsLastYear(Integer appointmentsLastYear) {
        this.appointmentsLastYear = appointmentsLastYear;
    }

    public Integer getAppointmentsTotal() {
        return appointmentsTotal;
    }

    public void setAppointmentsTotal(Integer appointmentsTotal) {
        this.appointmentsTotal = appointmentsTotal;
    }

    public Integer getSuppliesLastMonth() {
        return suppliesLastMonth;
    }

    public void setSuppliesLastMonth(Integer suppliesLastMonth) {
        this.suppliesLastMonth = suppliesLastMonth;
    }

    public Integer getSuppliesLastYear() {
        return suppliesLastYear;
    }

    public void setSuppliesLastYear(Integer suppliesLastYear) {
        this.suppliesLastYear = suppliesLastYear;
    }

    public Integer getSuppliesTotal() {
        return suppliesTotal;
    }

    public void setSuppliesTotal(Integer suppliesTotal) {
        this.suppliesTotal = suppliesTotal;
    }

    public Integer getEquipmentLastMonth() {
        return equipmentLastMonth;
    }

    public void setEquipmentLastMonth(Integer equipmentLastMonth) {
        this.equipmentLastMonth = equipmentLastMonth;
    }

    public Integer getEquipmentLastYear() {
        return equipmentLastYear;
    }

    public void setEquipmentLastYear(Integer equipmentLastYear) {
        this.equipmentLastYear = equipmentLastYear;
    }

    public Integer getEquipmentTotal() {
        return equipmentTotal;
    }

    public void setEquipmentTotal(Integer equipmentTotal) {
        this.equipmentTotal = equipmentTotal;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }
}

