package com.isa.controller;

import com.isa.dto.QuestionnaireDTO;
import com.isa.dto.UserDTO;
import com.isa.model.BloodType;
import com.isa.model.Gender;
import com.isa.model.Questionnaire;
import com.isa.model.User;
import com.isa.service.QuestionnaireService;
import com.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
@RequestMapping(value = "api/questionnaires")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private UserService userService;

    private boolean isValidDate(String date) {
        String regex = "^\\d{4}-\\d{2}-\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    private boolean isValidName(String name) {
        String regex = "^[A-Z][a-z]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
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

    private boolean isValidTimesGiven(String number) {
        String regex = "^(0|[1-9]\\d*)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    private boolean isValidBloodType(BloodType bloodType) {
        String genderStr = bloodType.toString();
        String regex = "^(A|B|AB|O)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(genderStr);
        return matcher.matches();
    }


    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<List<QuestionnaireDTO>> getAllQuestionnaires() {

        List<Questionnaire> questionnaires = questionnaireService.findAll();

        List<QuestionnaireDTO> questionnairesDTO = new ArrayList<>();
        for (Questionnaire q: questionnaires) {
            questionnairesDTO.add(new QuestionnaireDTO(q));
        }
        return new ResponseEntity<>(questionnairesDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/connect")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<String> connectUserAndQuestionnaire(@RequestParam("userId") Integer userId, @RequestParam("questionnaireId") Integer questionnaireId) {
        User user = userService.findOne(userId);
        Questionnaire questionnaire = questionnaireService.findOne(questionnaireId);

        if (user == null || questionnaire == null) {
            return new ResponseEntity<>("User or Questionnaire not found", HttpStatus.BAD_REQUEST);
        }

        questionnaire.setUser(user);
        questionnaireService.save(questionnaire);

        user.setFilledQuestionnaire(true);
        userService.save(user);

        UserDTO userDTO = new UserDTO(user);
        userDTO.setFilledQuestionnaire(true);


        return new ResponseEntity<>("User and Questionnaire connected successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/submit")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<QuestionnaireDTO> submitQuestionnaire(@RequestBody QuestionnaireDTO questionnaireDTO) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr1 = dateFormat.format(questionnaireDTO.getDateOfQuestionnaire());
        String dateStr2 = dateFormat.format(questionnaireDTO.getDateOfBirth());

        if (!isValidDate(dateStr1)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!isValidDate(dateStr2)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!isValidName(questionnaireDTO.getFirstName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!isValidName(questionnaireDTO.getParentName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!isValidName(questionnaireDTO.getLastName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        String jmbgStr = String.valueOf(questionnaireDTO.getJmbg());

        if (!isValidJMBG(jmbgStr)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!isValidGender(questionnaireDTO.getGender())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!isValidAddress(questionnaireDTO.getAddress())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!isValidCityName(questionnaireDTO.getCity())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!isValidPhoneNumber(questionnaireDTO.getPhoneNumber())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!isValidBloodType(questionnaireDTO.getBloodType())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!isValidWorkplace(questionnaireDTO.getWorkplace())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!isValidJob(questionnaireDTO.getJob())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!isValidTimesGiven(Integer.toString(questionnaireDTO.getTimesGiven()))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Retrieve the user by user ID from the questionnaire DTO
        User user = userService.findOne(questionnaireDTO.getUserId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Create a new Questionnaire object from the questionnaire DTO
        Questionnaire questionnaire = new Questionnaire(
                questionnaireDTO.getDateOfQuestionnaire(),
                questionnaireDTO.getFirstName(),
                questionnaireDTO.getParentName(),
                questionnaireDTO.getLastName(),
                questionnaireDTO.getJmbg(),
                questionnaireDTO.getDateOfBirth(),
                questionnaireDTO.getGender(),
                questionnaireDTO.getAddress(),
                questionnaireDTO.getCity(),
                questionnaireDTO.getPhoneNumber(),
                questionnaireDTO.getWorkplace(),
                questionnaireDTO.getJob(),
                questionnaireDTO.getTimesGiven(),
                questionnaireDTO.getBloodType(),
                questionnaireDTO.getDrunkAlcohol(),
                questionnaireDTO.getHadTattoo(),
                questionnaireDTO.getDangerousJob(),
                questionnaireDTO.getDonatedBlood(),
                userService.findOne(questionnaireDTO.getUserId())
        );

        questionnaire.setAccepted(!questionnaire.getDonatedBlood() && !questionnaire.getHadTattoo() && !questionnaire.getDrunkAlcohol());

        questionnaireService.save(questionnaire);

        user.setFilledQuestionnaire(true);
        userService.save(user);

        UserDTO userDTO = new UserDTO(user);
        userDTO.setFilledQuestionnaire(true);

        QuestionnaireDTO responseDTO = new QuestionnaireDTO(questionnaire);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{questionnaireId}")
    @PreAuthorize("hasAnyRole('USER', 'MEDIC', 'ADMINISTRATOR')")
    public ResponseEntity<QuestionnaireDTO> updateQuestionnaire(@PathVariable("questionnaireId") Integer questionnaireId, @RequestBody QuestionnaireDTO updatedQuestionnaireDTO) {
        // Retrieve the existing questionnaire from the database
        Questionnaire existingQuestionnaire = questionnaireService.findOne(questionnaireId);

        if (existingQuestionnaire == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the fields of the existing questionnaire with the new values from the updatedQuestionnaireDTO
        existingQuestionnaire.setDateOfQuestionnaire(updatedQuestionnaireDTO.getDateOfQuestionnaire());
        existingQuestionnaire.setFirstName(updatedQuestionnaireDTO.getFirstName());
        existingQuestionnaire.setParentName(updatedQuestionnaireDTO.getParentName());
        existingQuestionnaire.setLastName(updatedQuestionnaireDTO.getLastName());
        existingQuestionnaire.setJmbg(updatedQuestionnaireDTO.getJmbg());
        existingQuestionnaire.setDateOfBirth(updatedQuestionnaireDTO.getDateOfBirth());
        existingQuestionnaire.setGender(updatedQuestionnaireDTO.getGender());
        existingQuestionnaire.setAddress(updatedQuestionnaireDTO.getAddress());
        existingQuestionnaire.setCity(updatedQuestionnaireDTO.getCity());
        existingQuestionnaire.setPhoneNumber(updatedQuestionnaireDTO.getPhoneNumber());
        existingQuestionnaire.setWorkplace(updatedQuestionnaireDTO.getWorkplace());
        existingQuestionnaire.setJob(updatedQuestionnaireDTO.getJob());
        existingQuestionnaire.setTimesGiven(updatedQuestionnaireDTO.getTimesGiven());
        existingQuestionnaire.setBloodType(updatedQuestionnaireDTO.getBloodType());
        existingQuestionnaire.setDrunkAlcohol(updatedQuestionnaireDTO.getDrunkAlcohol());
        existingQuestionnaire.setHadTattoo(updatedQuestionnaireDTO.getHadTattoo());
        existingQuestionnaire.setDangerousJob(updatedQuestionnaireDTO.getDangerousJob());
        existingQuestionnaire.setDonatedBlood(updatedQuestionnaireDTO.getDonatedBlood());

        existingQuestionnaire.setAccepted(!existingQuestionnaire.getDonatedBlood() && !existingQuestionnaire.getHadTattoo() && !existingQuestionnaire.getDrunkAlcohol());

        questionnaireService.save(existingQuestionnaire);

        // Create and return the response DTO
        QuestionnaireDTO responseDTO = new QuestionnaireDTO(existingQuestionnaire);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
