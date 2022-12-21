package com.isa.dto;

import com.isa.model.User;

public class UserDTO {
	private Integer id;
	private String email;
	private String firstName;
	private String lastName;
	private Integer pointsCollected;


	public UserDTO() {

	}

	public UserDTO(User user) {
		this(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getPointsCollected());
	}

	public UserDTO(Integer id, String email, String firstName, String lastName, Integer pointsCollected) {
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pointsCollected = pointsCollected;
	}

	public Integer getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Integer getPointsCollected() {
		return pointsCollected;
	}
}
