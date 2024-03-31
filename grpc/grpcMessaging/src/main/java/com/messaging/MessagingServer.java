package com.messaging;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import com.messaging.*;
import com.messaging.MessagingService.Message;
import com.messaging.MessagingService.MessageList;
import com.messaging.MessagingService.MessageRequest;
import com.messaging.MessagingService.MessageResponse;
import com.messaging.MessagingService.UserRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MessagingServer {
    private static final ConcurrentHashMap<String, List<Message>> messagesStore = new ConcurrentHashMap<>();

    static class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {

        @Override
        public void sendMessage(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
            String recipient = request.getRecipient();
            Message message = Message.newBuilder()
                    .setSender(request.getSender())
                    .setMessage(request.getMessage())
                    .build();
            messagesStore.computeIfAbsent(recipient, k -> new ArrayList<>()).add(message);

            MessageResponse response = MessageResponse.newBuilder().setStatus("Message envoyé avec succès").build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void getMessagesForUser(UserRequest request, StreamObserver<MessageList> responseObserver) {
            String username = request.getUsername();
            List<Message> userMessages = messagesStore.getOrDefault(username, new ArrayList<>());

            MessageList messageList = MessageList.newBuilder().addAllMessages(userMessages).build();
            responseObserver.onNext(messageList);
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(9023)
                .addService(new ChatServiceImpl())
                .build();

        server.start();
        System.out.println("Le serveur a démarré le port : " + server.getPort());
        server.awaitTermination();
    }
}