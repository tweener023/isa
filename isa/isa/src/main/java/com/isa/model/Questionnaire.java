package com.isa.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Questionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private Date dateOfQuestionnaire;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "parentName", nullable = false)
    private String parentName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "jmbg", unique = true, nullable = false)
    private Integer jmbg;

    @Column(name = "dateOfBirth", nullable = false)
    private Date dateOfBirth;

    @Enumerated
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "workplace", nullable = false)
    private String workplace;

    @Column(name = "job", nullable = false)
    private String job;

    @Column(name = "timesGiven", nullable = false)
    private Integer timesGiven;

    @Enumerated
    @Column(name = "bloodType", nullable = false)
    private BloodType bloodType;

    @Column(name = "accepted", nullable = false)
    private Boolean accepted;

    @Column(name = "drunkAlcohol", nullable = false)
    private Boolean drunkAlcohol;

    @Column(name = "hadTattoo", nullable = false)
    private Boolean hadTattoo;

    @Column(name = "dangerousJob", nullable = false)
    private Boolean dangerousJob;

    @Column(name = "donatedBlood", nullable = false)
    private Boolean donatedBlood;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Questionnaire() {
        super();
    }

    public Questionnaire(Date dateOfQuestionnaire, String firstName, String parentName, String lastName, Integer jmbg, Date dateOfBirth, Gender gender, String address, String city, String phoneNumber, String workplace, String job, Integer timesGiven, BloodType bloodType, Boolean accepted, Boolean drunkAlcohol, Boolean hadTattoo, Boolean dangerousJob, Boolean donatedBlood, User user) {
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
        this.accepted = accepted;
        this.drunkAlcohol = drunkAlcohol;
        this.hadTattoo = hadTattoo;
        this.dangerousJob = dangerousJob;
        this.donatedBlood = donatedBlood;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateOfQuestionnaire() {
        return dateOfQuestionnaire;
    }

    public void setDateOfQuestionnaire(Date dateOfQuestionnaire) {
        this.dateOfQuestionnaire = dateOfQuestionnaire;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getJmbg() {
        return jmbg;
    }

    public void setJmbg(Integer jmbg) {
        this.jmbg = jmbg;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getTimesGiven() {
        return timesGiven;
    }

    public void setTimesGiven(Integer timesGiven) {
        this.timesGiven = timesGiven;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Boolean getDrunkAlcohol() {
        return drunkAlcohol;
    }

    public void setDrunkAlcohol(Boolean drunkAlcohol) {
        this.drunkAlcohol = drunkAlcohol;
    }

    public Boolean getHadTattoo() {
        return hadTattoo;
    }

    public void setHadTattoo(Boolean hadTattoo) {
        this.hadTattoo = hadTattoo;
    }

    public Boolean getDangerousJob() {
        return dangerousJob;
    }

    public void setDangerousJob(Boolean dangerousJob) {
        this.dangerousJob = dangerousJob;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }

    public Boolean getDonatedBlood() {
        return donatedBlood;
    }

    public void setDonatedBlood(Boolean donatedBlood) {
        this.donatedBlood = donatedBlood;
    }
}
