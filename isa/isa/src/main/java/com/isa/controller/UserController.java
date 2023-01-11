package com.isa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isa.dto.UserDTO;
import com.isa.model.User;
import com.isa.service.UserService;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<UserDTO>> getAllUsers() {

		List<User> users = userService.findAll();

		// convert users to DTOs
		List<UserDTO> usersDTO = new ArrayList<>();
		for (User u : users) {
			usersDTO.add(new UserDTO(u));
		}

		return new ResponseEntity<>(usersDTO, HttpStatus.OK);
	}

	// GET /api/users?page=0&size=5&sort=firstName,DESC
	@GetMapping
	public ResponseEntity<List<UserDTO>> getusersPage(Pageable page) {

		// page object holds data about pagination and sorting
		// the object is created based on the url parameters "page", "size" and "sort"
		Page<User> users = userService.findAll(page);

		// convert users to DTOs
		List<UserDTO> usersDTO = new ArrayList<>();
		for (User u : users) {
			usersDTO.add(new UserDTO(u));
		}

		return new ResponseEntity<>(usersDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {

		User user = userService.findOne(id);

		// user must exist
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {

		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setAddress(userDTO.getAddress());
		user.setCity(userDTO.getCity());
		user.setCountry(userDTO.getCountry());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setJmbg(userDTO.getJmbg());
		user.setGender(userDTO.getGender());
		user.setJob(userDTO.getJob());
		user.setWorkplace(userDTO.getWorkplace());
		user.setPointsCollected(userDTO.getPointsCollected());

		user = userService.save(user);
		return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {

		// a user must exist
		User user = userService.findOne(userDTO.getId());

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		user.setPassword(userDTO.getPassword());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setAddress(userDTO.getAddress());
		user.setCity(userDTO.getCity());
		user.setCountry(userDTO.getCountry());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setJmbg(userDTO.getJmbg());
		user.setGender(userDTO.getGender());
		user.setJob(userDTO.getJob());
		user.setWorkplace(userDTO.getWorkplace());
		user.setPointsCollected(userDTO.getPointsCollected());

		user = userService.save(user);
		return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {

		User user = userService.findOne(id);

		if (user != null) {
			userService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/findByEmail")
	public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {

		User user = userService.findByEmail(email);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
	}

	@GetMapping(value = "/findLastName")
	public ResponseEntity<List<UserDTO>> getUsersByLastName(@RequestParam String lastName) {

		List<User> users = userService.findByLastName(lastName);

		// convert users to DTOs
		List<UserDTO> usersDTO = new ArrayList<>();
		for (User u : users) {
			usersDTO.add(new UserDTO(u));
		}
		return new ResponseEntity<>(usersDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/prezime")
	public ResponseEntity<List<UserDTO>> pronadjiUserePoPrezimenu(@RequestParam String lastName) {

		List<User> users = userService.pronadjiPoPrezimenu(lastName);

		// convert users to DTOs
		List<UserDTO> usersDTO = new ArrayList<>();
		for (User u : users) {
			usersDTO.add(new UserDTO(u));
		}
		return new ResponseEntity<>(usersDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/findFirstLast")
	public ResponseEntity<List<UserDTO>> getUsersByFirstNameAndLastName(@RequestParam String firstName,
			@RequestParam String lastName) {

		List<User> users = userService.findByFirstNameAndLastName(firstName, lastName);

		// convert users to DTOs
		List<UserDTO> usersDTO = new ArrayList<>();
		for (User u : users) {
			usersDTO.add(new UserDTO(u));
		}
		return new ResponseEntity<>(usersDTO, HttpStatus.OK);
	}

}
