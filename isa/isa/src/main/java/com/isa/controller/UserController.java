package com.isa.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.isa.dto.AppointmentDTO;
import com.isa.dto.FacilityDTO;
import com.isa.model.Appointments;
import com.isa.model.Facility;
import com.isa.service.AppointmentService;
import com.isa.service.EmailService;
import com.isa.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.isa.dto.UserDTO;
import com.isa.model.User;
import com.isa.service.UserService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.awt.Color;

@RestController
@CrossOrigin
@RequestMapping(value = "api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	FacilityService facilityService;

	@Autowired
	AppointmentService appointmentService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private JavaMailSender javaMailSender;



	@GetMapping(value = "/all")
	@PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
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
	@PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
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
	@PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
	public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {

		User user = userService.findOne(id);

		// user must exist
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
	public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {

		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setPassword(encoder.encode(userDTO.getPassword()));
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
		user.setFilledQuestionnaire(userDTO.isFilledQuestionnaire());
		user.setAccountVerified(userDTO.isAccountVerified());

		user = userService.save(user);
		return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {

		// a user must exist
		User user = userService.findOne(userDTO.getId());

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		user.setPassword(encoder.encode(userDTO.getPassword()));
		System.out.println("EVO JE SIFRA "+ user.getPassword());

		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setAddress(userDTO.getAddress());
		user.setCity(userDTO.getCity());
		user.setZipCode(userDTO.getZipCode());
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
	@PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
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
	@PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
	public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {

		User user = userService.findByEmail(email);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
	}

	@GetMapping(value = "/findLastName")
	@PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
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
	@PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
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
	@PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
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


	@GetMapping(value = "facility/{userId}")
	@PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
	public ResponseEntity<FacilityDTO> getFacilityAdmin(@PathVariable Integer userId) {
		User user = userService.findOne(userId);

		if(user == null ){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Facility facility = facilityService.findOneByAdmin(user);

		if(facility == null ){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		FacilityDTO facilityDTO = new FacilityDTO(facility);

		return new ResponseEntity<>(facilityDTO, HttpStatus.OK);
	}

	@PutMapping(value = "/{userId}/appointments", consumes = "application/json")
	@PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
	public ResponseEntity<UserDTO> addAppointmentToUser(@PathVariable Integer userId, @RequestBody AppointmentDTO appointmentDTO) {

		User user = userService.findOne(userId);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// Find the existing appointment by its ID
		Appointments appointment = appointmentService.findOne(appointmentDTO.getAppointmentId());

		if (appointment == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// Set the user for the appointment
		appointment.setUser(user);

		// Add the appointment to the user's appointments set
		user.getAppointments().add(appointment);

		// Save the updated user
		user = userService.save(user);

		// Generate the QR code image
		String qrCodeText = "Appointment ID: " + appointment.getAppointmentId()
				+ "\n Facility: " + appointment.getFacilityName().getCenterName()
				+ "\n Date: " + appointment.getDateOfAppointment()
				+ "\n Time: " + appointment.getTimeOfAppointment();
		int qrCodeWidth = 200;
		int qrCodeHeight = 200;
		String qrCodeImageBase64;
		try {
			qrCodeImageBase64 = generateQRCodeImage(qrCodeText, qrCodeWidth, qrCodeHeight);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// Update the email text without the <img> tag
		String text = "Your appointment details:\n"
				+ "Date: " + appointment.getDateOfAppointment() + "\n"
				+ "Time: " + appointment.getTimeOfAppointment() + "\n"
				+ "Facility: " + appointment.getFacilityName().getCenterName();

		// Send the email with the appointment details and QR code image as an attachment
		sendAppointmentConfirmationEmail(user.getEmail(), "Appointment Confirmation", text, qrCodeImageBase64);

		return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
	}

	private void sendAppointmentConfirmationEmail(String to, String subject, String text, String qrCodeImageBase64) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text, true);

			// Add the QR code image as an attachment
			byte[] imageBytes = Base64.getDecoder().decode(qrCodeImageBase64);
			helper.addAttachment("qr_code.png", new ByteArrayResource(imageBytes), "image/png");

			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public String generateQRCodeImage(String text, int width, int height) throws IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		try {
			BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

			BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			qrCodeImage.createGraphics();

			Graphics2D graphics = (Graphics2D) qrCodeImage.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, width, height);
			graphics.setColor(Color.BLACK);

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					if (bitMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			javax.imageio.ImageIO.write(qrCodeImage, "png", baos);
			baos.flush();
			byte[] imageBytes = baos.toByteArray();
			baos.close();

			// Convert the byte array to a Base64-encoded string
			String qrCodeImageBase64 = Base64.getEncoder().encodeToString(imageBytes);

			return qrCodeImageBase64;
		} catch (com.google.zxing.WriterException e) {
			throw new IOException("Error generating QR code image", e);
		}
	}


}
