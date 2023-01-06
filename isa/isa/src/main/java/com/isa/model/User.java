package com.isa.model;

import java.util.Objects;

import javax.persistence.*;


@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "firstName", nullable = false)
	private String firstName;

	@Column(name = "lastName", nullable = false)
	private String lastName;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "country", nullable = false)
	private String country;

	@Column(name = "phoneNumber", nullable = false)
	private String phoneNumber;

	@Column(name = "jmbg", unique = true, nullable = false)
	private Integer jmbg;

	@Enumerated
	@Column(name = "gender", nullable = false)
	private Gender gender;

	@Column(name = "job", nullable = false)
	private String job;

	@Column(name = "workplace", nullable = false)
	private String workplace;

	@Enumerated
	@Column(name = "role", nullable = false)
	private Role role;

	@Column(name = "pointsCollected", nullable = false)
	private Integer pointsCollected;


	public User() {
		super();
	}

	public User(String email, String password, String firstName, String lastName, String address, String city, String country, String phoneNumber, Integer jmbg, Gender gender, String job, String workplace, Role role, Integer pointsCollected) {
		super();
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

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getJmbg() {
		return jmbg;
	}

	public void setJmbg(Integer jmbg) {
		this.jmbg = jmbg;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getPointsCollected() {
		return pointsCollected;
	}

	public void setPointsCollected(Integer pointsCollected) {
		this.pointsCollected = pointsCollected;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User s = (User) o;
		if (s.email == null || email == null) {
			return false;
		}
		return Objects.equals(email, s.email);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(email);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", email='" + email + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", address='" + address + '\'' +
				", city='" + city + '\'' +
				", country='" + country + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", jmbg=" + jmbg +
				", gender=" + gender +
				", job='" + job + '\'' +
				", workplace='" + workplace + '\'' +
				", role=" + role +
				", pointsCollected=" + pointsCollected +
				'}';
	}
}
