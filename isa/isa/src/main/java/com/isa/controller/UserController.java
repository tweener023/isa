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
import com.isa.model.*;
import com.isa.payload.response.MessageResponse;
import com.isa.service.*;
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
import java.time.LocalDate;
import java.time.LocalTime;

import com.isa.dto.UserDTO;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	QuestionnaireService questionnaireService;

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
	public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {

		// a user must exist
		User user = userService.findOne(userDTO.getId());


		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		user.setPassword(encoder.encode(userDTO.getPassword()));


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
	@Transactional
	public ResponseEntity<UserDTO> addAppointmentToUser(@PathVariable Integer userId, @RequestBody AppointmentDTO appointmentDTO) {

		User user = userService.findOne(userId);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (user.isFilledQuestionnaire() == false) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		Questionnaire questionnaire = questionnaireService.findByUser(user);

		if (questionnaire.getAccepted() == false) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		Appointments appointment = appointmentService.findOne(appointmentDTO.getAppointmentId());

		if (appointment == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (appointment.getUser().getId() != 5) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		LocalDate today = LocalDate.now();
		LocalTime currentTime = LocalTime.now();

		boolean hasAppointmentFromSameFacility = user.getAppointments().stream()
				.filter(a -> a.getDateOfAppointment().isAfter(today) || (a.getDateOfAppointment().isEqual(today) && a.getTimeOfAppointment().isAfter(currentTime)))
				.anyMatch(a -> a.getFacilityName().getCenterId().equals(appointment.getFacilityName().getCenterId()));

		if (hasAppointmentFromSameFacility) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		appointment.setUser(user);

		user.getAppointments().add(appointment);

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

		String text = "Your appointment details:\n"
				+ "Date: " + appointment.getDateOfAppointment() + "\n"
				+ "Time: " + appointment.getTimeOfAppointment() + "\n"
				+ "Facility: " + appointment.getFacilityName().getCenterName();

		// Send the email with the appointment details and QR code image as an attachment
		sendAppointmentConfirmationEmail(user.getEmail(), "Appointment Confirmation", text, qrCodeImageBase64);

		return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
	}


	@PostMapping(value = "/{facilityId}/{userId}/customAppointment", consumes = "application/json")
	@PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
	@Transactional
	public ResponseEntity<AppointmentDTO> createCustomAppointment(@PathVariable("facilityId") Integer facilityId,@PathVariable("userId") Integer userId, @RequestBody AppointmentDTO appointmentDTO) {

		User user = userService.findOne(userId);
		Facility facility = facilityService.findOne(facilityId);

		if (facility == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (user.isFilledQuestionnaire() == false) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		Appointments appointment = new Appointments();
		appointment.setAppointmentId(appointmentDTO.getAppointmentId());
		appointment.setDateOfAppointment(appointmentDTO.getDateOfAppointment());
		appointment.setTimeOfAppointment(appointmentDTO.getTimeOfAppointment());
		appointment.setUser(user);
		appointment.setFacility(facility);

		appointment = appointmentService.save(appointment);


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

		String text = "Your appointment details:\n"
				+ "Date: " + appointment.getDateOfAppointment() + "\n"
				+ "Time: " + appointment.getTimeOfAppointment() + "\n"
				+ "Facility: " + appointment.getFacilityName().getCenterName();

		// Send the email with the appointment details and QR code image as an attachment
		sendAppointmentConfirmationEmail(user.getEmail(), "Appointment Confirmation", text, qrCodeImageBase64);


		return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.CREATED);
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


	private boolean isValidName(String name) {
		String regex = "^[A-Z][a-z]*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}

	private boolean isValidAddress(String address) {
		String regex = "^[a-zA-Z0-9\\s,.-]*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(address);
		return matcher.matches();
	}

	private boolean isValidCityName(String cityName) {
		String regex = "^(?:[A-Z][a-z]*)(?:\\s[A-Z][a-z]*)*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(cityName);
		return matcher.matches();
	}

	private boolean isValidPhoneNumber(String phoneNumber) {
		String regex = "^[0-9]{3}[-\\s]?[0-9]{3}[-\\s]?[0-9]{4}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}

	private boolean isValidJMBG(String jmbg) {
		String regex = "^[0-9]{8}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(jmbg);
		return matcher.matches();
	}

	private boolean isValidGender(Gender gender) {
		String genderStr = gender.toString().toUpperCase();
		String regex = "^(MALE|FEMALE)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(genderStr);
		return matcher.matches();
	}

	private boolean isValidWorkplace(String workplace) {
		String regex = "^[a-zA-Z0-9\\s.,'-]*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(workplace);
		return matcher.matches();
	}

	private boolean isValidJob(String job) {
		String regex = "^[a-zA-Z\\s.,'-]*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(job);
		return matcher.matches();
	}

	private boolean isValidPointsCollected(String number) {
		String regex = "^(0|[1-9]\\d*)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();
	}

	private boolean isValidCountry(String country) {
		String regex = "^(?:[A-Z][a-z]*)(?:\\s[A-Z][a-z]*)*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(country);
		return matcher.matches();
	}

	private boolean isValidZipCode(String zipCode) {
		String regex = "^\\d{5}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(zipCode);
		return matcher.matches();
	}

	private boolean isValidUsername(String username) {
		String regex = "^[a-zA-Z0-9_]{3,20}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(username);
		return matcher.matches();
	}

}
