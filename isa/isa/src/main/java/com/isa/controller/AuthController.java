package com.isa.controller;

import com.isa.model.ERole;
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
import java.util.Set;
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

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
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

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PutMapping(value = "/questionnaire")
    public void questionnaireIsFilled(@RequestParam Integer jmbg) {
        userService.questionnaireIsFilled(jmbg);
    }

}
