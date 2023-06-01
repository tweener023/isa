package com.isa.dto;

import com.isa.model.BloodType;
import com.isa.model.Gender;
import com.isa.model.Questionnaire;
import com.isa.model.User;

import java.util.Date;

public class QuestionnaireDTO {
    private Integer id;
    private Date dateOfQuestionnaire;
    private String firstName;
    private String parentName;
    private String lastName;
    private Integer jmbg;
    private Date dateOfBirth;
    private Gender gender;
    private String address;
    private String city;
    private String phoneNumber;
    private String workplace;
    private String job;
    private Integer timesGiven;
    private BloodType bloodType;
    private Boolean accepted;
    private Boolean drunkAlcohol;
    private Boolean hadTattoo;
    private Boolean dangerousJob;
    private Boolean donatedBlood;
    private Integer userId;

    public QuestionnaireDTO() {

    }

    public QuestionnaireDTO(Questionnaire questionnaire) {
        this.id = questionnaire.getId();
        this.dateOfQuestionnaire = questionnaire.getDateOfQuestionnaire();
        this.firstName = questionnaire.getFirstName();
        this.parentName = questionnaire.getParentName();
        this.lastName = questionnaire.getLastName();
        this.jmbg = questionnaire.getJmbg();
        this.dateOfBirth = questionnaire.getDateOfBirth();
        this.gender = questionnaire.getGender();
        this.address = questionnaire.getAddress();
        this.city = questionnaire.getCity();
        this.phoneNumber = questionnaire.getPhoneNumber();
        this.workplace = questionnaire.getWorkplace();
        this.job = questionnaire.getJob();
        this.timesGiven = questionnaire.getTimesGiven();
        this.bloodType = questionnaire.getBloodType();
        this.drunkAlcohol = questionnaire.getDrunkAlcohol();
        this.hadTattoo = questionnaire.getHadTattoo();
        this.dangerousJob = questionnaire.getDangerousJob();
        this.donatedBlood = questionnaire.getDonatedBlood();
        this.accepted = !donatedBlood && !hadTattoo && !drunkAlcohol;
        this.userId = questionnaire.getUser().getId();
    }

    public QuestionnaireDTO(Integer id, Date dateOfQuestionnaire, String firstName, String parentName, String lastName, Integer jmbg, Date dateOfBirth, Gender gender, String address, String city, String phoneNumber, String workplace, String job, Integer timesGiven, BloodType bloodType, Boolean drunkAlcohol, Boolean hadTattoo, Boolean dangerousJob, Boolean donatedBlood, User user) {
        this.id = id;
        this.dateOfQuestionnaire = dateOfQuestionnaire;
        this.firstName = firstName;
        this.parentName = parentName;
        this.lastName = lastName;
        this.jmbg = jmbg;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.workplace = workplace;
        this.job = job;
        this.timesGiven = timesGiven;
        this.bloodType = bloodType;
        this.accepted = !donatedBlood && !hadTattoo && !drunkAlcohol;
        this.drunkAlcohol = drunkAlcohol;
        this.hadTattoo = hadTattoo;
        this.dangerousJob = dangerousJob;
        this.donatedBlood = donatedBlood;
        this.userId = user.getId();
    }

    public Integer getId() {
        return id;
    }

    public Date getDateOfQuestionnaire() {
        return dateOfQuestionnaire;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getParentName() {
        return parentName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getJmbg() {
        return jmbg;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWorkplace() {
        return workplace;
    }

    public String getJob() {
        return job;
    }

    public Integer getTimesGiven() {
        return timesGiven;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public Boolean getDrunkAlcohol() {
        return drunkAlcohol;
    }

    public Boolean getHadTattoo() {
        return hadTattoo;
    }

    public Boolean getDangerousJob() {
        return dangerousJob;
    }

    public Boolean getDonatedBlood() {
        return donatedBlood;
    }

    public Integer getUserId() {
        return userId;
    }
}
