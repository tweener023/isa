package com.isa.dto;

import com.isa.model.Gender;
import com.isa.model.Role;
import com.isa.model.User;

public class UserDTO {
	private Integer id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String country;
	private String phoneNumber;
	private Integer jmbg;
	private Gender gender;
	private String job;
	private String workplace;
	private Role role;
	private Integer pointsCollected;


	public UserDTO() {

	}

	public UserDTO(User user) {
		this(user.getId(), user.getEmail(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getAddress(), user.getCity(), user.getCountry(), user.getPhoneNumber(), user.getJmbg(), user.getGender(), user.getJob(), user.getWorkplace(), user.getRole(), user.getPointsCollected());
	}

	public UserDTO(Integer id, String email, String password, String firstName, String lastName, String address, String city, String country, String phoneNumber, Integer jmbg, Gender gender, String job, String workplace, Role role, Integer pointsCollected) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.jmbg = jmbg;
		this.gender = gender;
		this.job = job;
		this.workplace = workplace;
		this.role = role;
		this.pointsCollected = pointsCollected;
	}

	public Integer getId() {
		return id;
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

	public Role getRole() {
		return role;
	}

	public Integer getPointsCollected() {
		return pointsCollected;
	}
}
