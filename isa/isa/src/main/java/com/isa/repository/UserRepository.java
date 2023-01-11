package com.isa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.isa.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	public User findOneByEmail(String email);


	public Page<User> findAll(Pageable pageable);


	public List<User> findAllByLastName(String lastName);


	public List<User> findByFirstNameAndLastNameAllIgnoringCase(String firstName, String lastName);


	@Query("select s from User s where s.lastName = ?1")
	public List<User> pronadjiUserePoPrezimenu(String prezime);
	
	

}
