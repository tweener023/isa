package com.isa.model;

import javax.persistence.*;

@Entity
public class AnswerToComplaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "complaintId", referencedColumnName = "complaintId")
    private Complaint complaint;

    @Column(name = "answer_to_complaint_text", columnDefinition = "TEXT")
    private String answerToComplaintText;

    public AnswerToComplaint() {
        super();
    }

    public AnswerToComplaint(Complaint complaint, String answerToComplaintText) {
        this.complaint = complaint;
        this.answerToComplaintText = answerToComplaintText;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public String getAnswerToComplaintText() {
        return answerToComplaintText;
    }

    public void setAnswerToComplaintText(String answerToComplaintText) {
        this.answerToComplaintText = answerToComplaintText;
    }
}
