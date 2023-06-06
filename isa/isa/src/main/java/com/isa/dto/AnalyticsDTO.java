package com.isa.dto;


import com.isa.model.Analytics;

public class AnalyticsDTO {
    private Long id;

    private Float averageGrade;

    private Integer appointmentsLastMonth;

    private Integer appointmentsLastYear;

    private Integer appointmentsTotal;

    private Integer suppliesLastMonth;

    private Integer suppliesLastYear;

    private Integer suppliesTotal;

    private Integer equipmentLastMonth;

    private Integer equipmentLastYear;

    private Integer equipmentTotal;



    public AnalyticsDTO(){

    }

    public AnalyticsDTO(Analytics analytics){
        id = analytics.getId();
        averageGrade = analytics.getAverageGrade();
        appointmentsLastMonth = analytics.getAppointmentsLastMonth();
        appointmentsLastYear = analytics.getAppointmentsLastYear();
        appointmentsTotal = analytics.getAppointmentsTotal();
        suppliesLastMonth = analytics.getSuppliesLastMonth();
        suppliesLastYear = analytics.getSuppliesLastYear();
        suppliesTotal = analytics.getSuppliesTotal();
        equipmentLastMonth = analytics.getEquipmentLastMonth();
        equipmentLastYear = analytics.getEquipmentLastYear();
        equipmentTotal = analytics.getEquipmentTotal();
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
}

