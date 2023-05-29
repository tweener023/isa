package com.isa.dto;

import com.isa.model.Gender;
import com.isa.model.User;

public class UserDTO {
	private Integer id;
	private String username;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String zipCode;
	private String country;
	private String phoneNumber;
	private Integer jmbg;
	private Gender gender;
	private String job;
	private String workplace;
	private Integer pointsCollected;
	private boolean filledQuestionnaire;
	private boolean accountVerified;


	public UserDTO() {

	}

	public UserDTO(User user) {
		this(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getAddress(), user.getCity(), user.getZipCode(), user.getCountry(), user.getPhoneNumber(), user.getJmbg(), user.getGender(), user.getJob(), user.getWorkplace(), user.getPointsCollected(), user.isFilledQuestionnaire(), user.isAccountVerified());
	}

	public UserDTO(Integer id, String username, String email, String password, String firstName, String lastName, String address, String city, String zipCode, String country, String phoneNumber, Integer jmbg, Gender gender, String job, String workplace, Integer pointsCollected, boolean filledQuestionnaire, boolean accountVerified) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.zipCode = zipCode;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.jmbg = jmbg;
		this.gender = gender;
		this.job = job;
		this.workplace = workplace;
		this.pointsCollected = pointsCollected;
		this.filledQuestionnaire = filledQuestionnaire;
		this.accountVerified = accountVerified;
	}

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getCountry() {
		return country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Integer getJmbg() {
		return jmbg;
	}

	public Gender getGender() {
		return gender;
	}

	public String getJob() {
		return job;
	}

	public String getWorkplace() {
		return workplace;
	}

	public Integer getPointsCollected() {
		return pointsCollected;
	}

	public boolean isFilledQuestionnaire() {
		return filledQuestionnaire;
	}

	public void setFilledQuestionnaire(boolean filledQuestionnaire) {

		this.filledQuestionnaire = filledQuestionnaire;
	}

	public boolean isAccountVerified() {
		return accountVerified;
	}
}
