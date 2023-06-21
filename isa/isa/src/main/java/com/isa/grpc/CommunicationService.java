package com.isa.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
public class CommunicationService extends com.isa.grpc.SpringGrpcServiceGrpc.SpringGrpcServiceImplBase {

    @Override
    public void communicate(com.isa.grpc.MessageProto request, StreamObserver<com.isa.grpc.MessageResponseProto> responseObserver){
        System.out.println("Message: " + request.getMessage() + "; randomInteger: " + request.getRandomInteger());

        com.isa.grpc.MessageResponseProto responseMessage = com.isa.grpc.MessageResponseProto.newBuilder()
                .setResponse("Spring Boot: " + UUID.randomUUID().toString()).setStatus("Status 200").build();

        responseObserver.onNext(responseMessage);
        responseObserver.onCompleted();
    }

}
