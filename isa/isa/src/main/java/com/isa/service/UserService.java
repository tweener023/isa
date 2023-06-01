package com.isa.service;

import java.util.List;

import com.isa.model.Appointments;
import com.isa.model.User;
import com.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findOne(Integer id) {
		return userRepository.findById(id).orElseGet(null);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public Page<User> findAll(Pageable page) {
		return userRepository.findAll(page);
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public void remove(Integer id) {
		userRepository.deleteById(id);
	}
	
	public User findByEmail(String email) {return userRepository.findOneByEmail(email);}
	
	public List<User> findByLastName(String lastName) {
		return userRepository.findAllByLastName(lastName);
	}
	
	public List<User> findByFirstNameAndLastName(String firstName, String lastName) {
		return userRepository.findByFirstNameAndLastNameAllIgnoringCase(firstName, lastName);
	}
	
	public List<User> pronadjiPoPrezimenu(String prezime) {
		return userRepository.pronadjiUserePoPrezimenu(prezime);
	}

	public void questionnaireIsFilled(Integer jmbg) { userRepository.questionnaireIsFilled(jmbg);}

	public void addAppointmentToUser(Integer userId, Appointments appointment) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		user.getAppointments().add(appointment);
		userRepository.save(user);
	}
	
	}
