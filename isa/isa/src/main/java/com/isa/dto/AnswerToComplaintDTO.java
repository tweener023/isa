package com.isa.dto;

import com.isa.model.AnswerToComplaint;
import com.isa.model.Complaint;

public class AnswerToComplaintDTO {
    private  Integer id;
    private Integer complaintId;
    private String answerToComplaintText;

    public AnswerToComplaintDTO() {

    }

    public AnswerToComplaintDTO(AnswerToComplaint answerToComplaint) {
        this.id = answerToComplaint.getId();
        this.complaintId = answerToComplaint.getComplaint().getComplaintId();
        this.answerToComplaintText = answerToComplaint.getAnswerToComplaintText();
    }

    public AnswerToComplaintDTO(Complaint complaint, String answerToComplaintText) {
        this.complaintId = complaint.getComplaintId();
        this.answerToComplaintText = answerToComplaintText;
    }

    public Integer getId() {
        return id;
    }

    public Integer getComplaintId() {
        return complaintId;
    }

    public String getAnswerToComplaintText() {
        return answerToComplaintText;
    }
}
