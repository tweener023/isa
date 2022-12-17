package com.isa.dto;

import com.isa.model.User;

public class UserDTO {
	private Integer id;
	private String index;
	private String firstName;
	private String lastName;

	public UserDTO() {

	}

	public UserDTO(User user) {
		this(user.getId(), user.getIndex(), user.getFirstName(), user.getLastName());
	}

	public UserDTO(Integer id, String index, String firstName, String lastName) {
		this.id = id;
		this.index = index;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}

	public String getIndex() {
		return index;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
