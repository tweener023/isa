package com.isa.dto;

import com.isa.model.*;

public class ComplaintDTO {
    private Integer complaintId;
    private Integer facilityId;
    private Integer userId;
    private String complaintText;
    private StatusOfComplaint statusOfComplaint;
    private DirectedTo directedTo;

    public ComplaintDTO() {

    }

    public ComplaintDTO(Complaint complaint) {
        this.complaintId = complaint.getComplaintId();
        this.facilityId = complaint.getFacility().getCenterId();
        this.userId = complaint.getUser().getId();
        this.complaintText = complaint.getComplaintText();
        this.statusOfComplaint = complaint.getStatusOfComplaint();
        this.directedTo = complaint.getDirectedTo();
    }

    public ComplaintDTO(Facility facility, User user, String complaintText, StatusOfComplaint statusOfComplaint, DirectedTo directedTo) {
        this.facilityId = facility.getCenterId();
        this.userId = user.getId();
        this.complaintText = complaintText;
        this.statusOfComplaint = statusOfComplaint;
        this.directedTo = directedTo;
    }

    public Integer getComplaintId() {
        return complaintId;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getComplaintText() {
        return complaintText;
    }

    public StatusOfComplaint getStatusOfComplaint() {
        return statusOfComplaint;
    }

    public DirectedTo getDirectedTo() {
        return directedTo;
    }
}
