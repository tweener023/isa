package com.isa.controller;

import com.isa.model.ERole;
import com.isa.model.Gender;
import com.isa.model.Role;
import com.isa.model.User;
import com.isa.payload.request.LoginRequest;
import com.isa.payload.request.SignupRequest;
import com.isa.payload.response.JwtResponse;
import com.isa.payload.response.MessageResponse;
import com.isa.repository.RoleRepository;
import com.isa.repository.UserRepository;
import com.isa.security.jwt.JwtUtils;
import com.isa.security.services.UserDetailsImpl;
import com.isa.service.EmailService;
import com.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private EmailService emailService;

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
        String regex = "^(MALE|FEMALE)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(gender.toString());
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

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!userDetails.isAccountVerified()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Account is not verified."));
        }

        String jwt = jwtUtils.generateJwtToken(authentication);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getAddress(),
                userDetails.getCity(),
                userDetails.getZipCode(),
                userDetails.getCountry(),
                userDetails.getPhoneNumber(),
                userDetails.getJmbg(),
                userDetails.getGender(),
                userDetails.getJob(),
                userDetails.getWorkplace(),
                userDetails.getPointsCollected(),
                userDetails.isFilledQuestionnaire(),
                userDetails.isAccountVerified(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        if (!isValidName(signupRequest.getFirstName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid first name!"));
        }

        if (!isValidName(signupRequest.getLastName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid last name!"));
        }

        if (!isValidAddress(signupRequest.getAddress())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid address!"));
        }

        if (!isValidCityName(signupRequest.getCity())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid city name!"));
        }

        if (!isValidPhoneNumber(signupRequest.getPhoneNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid phone number!"));
        }

        if (!isValidGender(signupRequest.getGender())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid gender!"));
        }

        if (!isValidWorkplace(signupRequest.getWorkplace())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid workplace!"));
        }

        if (!isValidJob(signupRequest.getJob())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid job!"));
        }

        if (!isValidCountry(signupRequest.getCountry())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid country!"));
        }

        if (!isValidUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid username!"));
        }

        if (!isValidEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid email!"));
        }

        String jmbgString = String.valueOf(signupRequest.getJmbg());

        if (!isValidJMBG(jmbgString)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid JMBG!"));
        }

        String pointsCollectedString = String.valueOf(signupRequest.getPointsCollected());

        if (!isValidPointsCollected(pointsCollectedString)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid points collected!"));
        }

        String zipCodeString = String.valueOf(signupRequest.getZipCode());

        if (!isValidZipCode(zipCodeString)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid zip code!"));
        }


        User user = new User(signupRequest.getUsername(),
                            signupRequest.getEmail(),
                            encoder.encode(signupRequest.getPassword()),
                            signupRequest.getFirstName(),
                            signupRequest.getLastName(),
                            signupRequest.getAddress(),
                            signupRequest.getCity(),
                            signupRequest.getZipCode(),
                            signupRequest.getCountry(),
                            signupRequest.getPhoneNumber(),
                            signupRequest.getJmbg(),
                            signupRequest.getGender(),
                            signupRequest.getJob(),
                            signupRequest.getWorkplace(),
                            signupRequest.getPointsCollected());

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMINISTRATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "medic":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MEDIC)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        String verificationLink = "http://localhost:8080/api/auth/verify?email=" + user.getEmail();
        emailService.sendVerificationEmail(user.getEmail(), verificationLink);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PutMapping(value = "/questionnaire")
    public void questionnaireIsFilled(@RequestParam Integer jmbg) {
        userService.questionnaireIsFilled(jmbg);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setAccountVerified(true);
            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("Account verified successfully!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found."));
        }

    }

}
