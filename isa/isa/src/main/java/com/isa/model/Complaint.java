package com.isa.model;

import javax.persistence.*;

@Entity
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer complaintId;

    @ManyToOne
    @JoinColumn(name = "center_id", referencedColumnName = "centerId")
    private Facility facility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "complaint_text", columnDefinition = "TEXT")
    private String complaintText;

    @Column(name = "status_of_complaint")
    private StatusOfComplaint statusOfComplaint;


    public Complaint() {
        super();
    }

    public Complaint(String complaintText, StatusOfComplaint statusOfComplaint) {
        this.complaintText = complaintText;
        this.statusOfComplaint = statusOfComplaint;
    }

    public Integer getComplaintId() {
        return complaintId;
    }

    public void setId(Integer complaintId) {
        this.complaintId = complaintId;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComplaintText() {
        return complaintText;
    }

    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }

    public StatusOfComplaint getStatusOfComplaint() {
        return statusOfComplaint;
    }

    public void setStatusOfComplaint(StatusOfComplaint statusOfComplaint) {
        this.statusOfComplaint = statusOfComplaint;
    }
}
