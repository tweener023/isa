package com.isa.dto;

import com.isa.model.Complaint;
import com.isa.model.Facility;
import com.isa.model.StatusOfComplaint;
import com.isa.model.User;

public class ComplaintDTO {
    private Integer complaintId;
    private Integer facilityId;
    private Integer userId;
    private String complaintText;
    private StatusOfComplaint statusOfComplaint;

    public ComplaintDTO() {

    }

    public ComplaintDTO(Complaint complaint) {
        this.complaintId = complaint.getComplaintId();
        this.facilityId = complaint.getFacility().getCenterId();
        this.userId = complaint.getUser().getId();
        this.complaintText = complaint.getComplaintText();
        this.statusOfComplaint = complaint.getStatusOfComplaint();
    }

    public ComplaintDTO(Facility facility, User user, String complaintText, StatusOfComplaint statusOfComplaint) {
        this.facilityId = facility.getCenterId();
        this.userId = user.getId();
        this.complaintText = complaintText;
        this.statusOfComplaint = statusOfComplaint;
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
}
