package com.isa.controller;

import com.isa.dto.QuestionnaireDTO;
import com.isa.dto.UserDTO;
import com.isa.model.Questionnaire;
import com.isa.model.User;
import com.isa.service.QuestionnaireService;
import com.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/questionnaires")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<QuestionnaireDTO>> getAllQuestionnaires() {

        List<Questionnaire> questionnaires = questionnaireService.findAll();

        List<QuestionnaireDTO> questionnairesDTO = new ArrayList<>();
        for (Questionnaire q: questionnaires) {
            questionnairesDTO.add(new QuestionnaireDTO(q));
        }
        return new ResponseEntity<>(questionnairesDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/connect")
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
    public ResponseEntity<QuestionnaireDTO> submitQuestionnaire(@RequestBody QuestionnaireDTO questionnaireDTO) {
        // Retrieve the user by user ID from the questionnaire DTO
        User user = userService.findOne(questionnaireDTO.getUserId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Create a new Questionnaire object from the questionnaire DTO
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setDateOfQuestionnaire(questionnaireDTO.getDateOfQuestionnaire());
        questionnaire.setFirstName(questionnaireDTO.getFirstName());
        questionnaire.setParentName(questionnaireDTO.getParentName());
        questionnaire.setLastName(questionnaireDTO.getLastName());
        questionnaire.setJmbg(questionnaireDTO.getJmbg());
        questionnaire.setDateOfBirth(questionnaireDTO.getDateOfBirth());
        questionnaire.setGender(questionnaireDTO.getGender());
        questionnaire.setAddress(questionnaireDTO.getAddress());
        questionnaire.setCity(questionnaireDTO.getCity());
        questionnaire.setPhoneNumber(questionnaireDTO.getPhoneNumber());
        questionnaire.setWorkplace(questionnaireDTO.getWorkplace());
        questionnaire.setJob(questionnaireDTO.getJob());
        questionnaire.setTimesGiven(questionnaireDTO.getTimesGiven());
        questionnaire.setBloodType(questionnaireDTO.getBloodType());
        questionnaire.setAccepted(questionnaireDTO.getAccepted());
        questionnaire.setDrunkAlcohol(questionnaireDTO.getDrunkAlcohol());
        questionnaire.setHadTattoo(questionnaireDTO.getHadTattoo());
        questionnaire.setDangerousJob(questionnaireDTO.getDangerousJob());
        questionnaire.setDonatedBlood(questionnaireDTO.getDonatedBlood());
        questionnaire.setUser(user);

        // Save the questionnaire
        questionnaireService.save(questionnaire);

        // Update the user's filledQuestionnaire field to true
        user.setFilledQuestionnaire(true);
        userService.save(user);

        UserDTO userDTO = new UserDTO(user);
        userDTO.setFilledQuestionnaire(true);

        // Create and return the response DTO
        QuestionnaireDTO responseDTO = new QuestionnaireDTO(questionnaire);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{questionnaireId}")
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
        existingQuestionnaire.setAccepted(updatedQuestionnaireDTO.getAccepted());
        existingQuestionnaire.setDrunkAlcohol(updatedQuestionnaireDTO.getDrunkAlcohol());
        existingQuestionnaire.setHadTattoo(updatedQuestionnaireDTO.getHadTattoo());
        existingQuestionnaire.setDangerousJob(updatedQuestionnaireDTO.getDangerousJob());
        existingQuestionnaire.setDonatedBlood(updatedQuestionnaireDTO.getDonatedBlood());

        // Save the updated questionnaire
        questionnaireService.save(existingQuestionnaire);

        // Create and return the response DTO
        QuestionnaireDTO responseDTO = new QuestionnaireDTO(existingQuestionnaire);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
