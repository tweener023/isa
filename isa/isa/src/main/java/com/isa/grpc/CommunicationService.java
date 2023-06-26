package com.isa.grpc;

import com.google.protobuf.Timestamp;
import com.isa.model.Appointments;
import com.isa.model.Facility;
import com.isa.service.FacilityService;
import com.isa.service.UserService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@GrpcService
public class CommunicationService extends com.isa.grpc.SpringGrpcServiceGrpc.SpringGrpcServiceImplBase {

    @Autowired
    FacilityService facilityService;

    @Autowired
    UserService userService;

    @Override
    public void communicate(com.isa.grpc.MessageProto request, StreamObserver<com.isa.grpc.MessageResponseProto> responseObserver) {
        System.out.println("Message: " + request.getMessage() + "; randomInteger: " + request.getRandomInteger());

        com.isa.grpc.MessageResponseProto responseMessage = com.isa.grpc.MessageResponseProto.newBuilder()
                .setResponse("Spring Boot: " + UUID.randomUUID().toString()).setStatus("Status 200").build();

        responseObserver.onNext(responseMessage);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllAppointments(com.isa.grpc.FacilityIdRequest request, StreamObserver<com.isa.grpc.AppointmentListResponseProto> responseObserver) {
        int centerId = request.getCenterId();

        // Retrieve facility appointments using your existing method
        List<com.isa.dto.AppointmentDTO> appointments = getFacilityAppointments(centerId);

        // Build the response message
        com.isa.grpc.AppointmentListResponseProto.Builder responseBuilder = com.isa.grpc.AppointmentListResponseProto.newBuilder();
        for (com.isa.dto.AppointmentDTO appointment : appointments) {
            com.isa.grpc.AppointmentDTO.Builder appointmentBuilder = com.isa.grpc.AppointmentDTO.newBuilder();
            appointmentBuilder.setAppointmentId(appointment.getAppointmentId());

            com.isa.grpc.UserDTO.Builder userBuilder = com.isa.grpc.UserDTO.newBuilder();
            userBuilder.setId(appointment.getUser().getId());
            userBuilder.setUsername(appointment.getUser().getUsername());
            userBuilder.setEmail(appointment.getUser().getEmail());
            // Set other user fields...

            appointmentBuilder.setUser(userBuilder.build());

            // Set other appointment fields...
            Timestamp dateOfAppointmentTimestamp = convertLocalDateToTimestamp(appointment.getDateOfAppointment());
            appointmentBuilder.setDateOfAppointment(dateOfAppointmentTimestamp);

            Timestamp timeOfAppointmentTimestamp = convertLocalTimeToTimestamp(appointment.getTimeOfAppointment());
            appointmentBuilder.setTimeOfAppointment(timeOfAppointmentTimestamp);

            com.isa.grpc.FacilityDTO.Builder facilityBuilder = com.isa.grpc.FacilityDTO.newBuilder();
            facilityBuilder.setCenterId(appointment.getFacility().getCenterId());
            facilityBuilder.setCenterName(appointment.getFacility().getCenterName());
            facilityBuilder.setCenterAddress(appointment.getFacility().getCenterAddress());
            // Set other facility fields...

            appointmentBuilder.setFacility(facilityBuilder.build());

            responseBuilder.addAppointments(appointmentBuilder.build());
        }

        com.isa.grpc.AppointmentListResponseProto response = responseBuilder.build();

        // Send the response
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    // Helper method to retrieve facility appointments
    private List<com.isa.dto.AppointmentDTO> getFacilityAppointments(int centerId) {
        Facility facility = facilityService.findOne(centerId);

        Set<Appointments> appointments = facility.getCenterAppointments();
        List<com.isa.dto.AppointmentDTO> appointmentsDTO = new ArrayList<>();
        for (Appointments e : appointments) {
            com.isa.dto.AppointmentDTO appointmentDTO = new com.isa.dto.AppointmentDTO();
            appointmentDTO.setAppointmentId(e.getAppointmentId());

            if (e.getUser() != null) {
                appointmentDTO.setUser(new com.isa.dto.UserDTO(e.getUser()));
            }
            appointmentDTO.setFacility(new com.isa.dto.FacilityDTO(e.getFacilityName()));
            appointmentDTO.setDateOfAppointment(e.getDateOfAppointment());
            appointmentDTO.setTimeOfAppointment(e.getTimeOfAppointment());

            appointmentsDTO.add(appointmentDTO);
        }

        return appointmentsDTO;
    }

    @Override
    public void createAppointment(com.isa.grpc.CreateAppointmentRequest request, StreamObserver<com.isa.grpc.AppointmentDTO> responseObserver) {
        int userId = request.getUserId();
        com.isa.grpc.AppointmentDTO appointmentDto = request.getAppointment();

        // Retrieve the user using userId
        com.isa.model.User user = userService.findOne(userId);

        if (user == null) {
            responseObserver.onError(Status.NOT_FOUND.withDescription("User not found").asRuntimeException());
            return;
        }

        if (!user.isFilledQuestionnaire()) {
            responseObserver.onError(Status.PERMISSION_DENIED.withDescription("User has not filled the questionnaire").asRuntimeException());
            return;
        }

        com.isa.grpc.AppointmentDTO.Builder appointmentBuilder = appointmentDto.toBuilder();

        // Create a new Appointments object
        com.isa.model.Appointments appointment = new com.isa.model.Appointments();

        // Set the appointment properties based on appointmentDto
        appointment.setAppointmentId(appointmentDto.getAppointmentId());
        appointment.setUser(user);
        appointment.setDateOfAppointment(convertTimestampToLocalDate(appointmentDto.getDateOfAppointment()));
        appointment.setTimeOfAppointment(convertTimestampToLocalTime(appointmentDto.getTimeOfAppointment()));
        // Set other appointment fields...


        // Add the appointment to the user's appointments list
        user.getAppointments().add(appointment);

        // Save the updated user
        user = userService.save(user);

        // Build the response message
        com.isa.grpc.AppointmentDTO response = appointmentBuilder.build();

        // Send the response
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // Helper method to convert LocalDate to Timestamp
    private Timestamp convertLocalDateToTimestamp(LocalDate localDate) {
        return Timestamp.newBuilder()
                .setSeconds(localDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC))
                .build();
    }

    // Helper method to convert LocalTime to Timestamp
    private Timestamp convertLocalTimeToTimestamp(LocalTime localTime) {
        return Timestamp.newBuilder()
                .setNanos(localTime.toSecondOfDay() * 1_000_000_000)
                .build();
    }
    // Helper method to convert Timestamp to LocalDate
    private LocalDate convertTimestampToLocalDate(com.google.protobuf.Timestamp timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.toLocalDate();
    }

    // Helper method to convert Timestamp to LocalTime
    private LocalTime convertTimestampToLocalTime(com.google.protobuf.Timestamp timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.toLocalTime();
    }
}
