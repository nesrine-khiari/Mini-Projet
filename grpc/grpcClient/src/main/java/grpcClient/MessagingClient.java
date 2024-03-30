package grpcClient;

import com.messaging.*;
import com.messaging.MessagingService.Message;
import com.messaging.MessagingService.MessageList;
import com.messaging.MessagingService.MessageRequest;
import com.messaging.MessagingService.MessageResponse;
import com.messaging.MessagingService.UserRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;

public class MessagingClient {

    private final ManagedChannel channel;
    private final ChatServiceGrpc.ChatServiceBlockingStub blockingStub;

    public MessagingClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = ChatServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void sendMessage(String sender, String recipient, String message) {
        try {
            MessageRequest request = MessageRequest.newBuilder()
                    .setSender(sender)
                    .setRecipient(recipient)
                    .setMessage(message)
                    .build();

            MessageResponse response = blockingStub.sendMessage(request);
            System.out.println("Status: " + response.getStatus());
        } catch (StatusRuntimeException e) {
            System.err.println("RPC Echec: " + e.getStatus());
        }
    }

    public void getMessagesForUser(String username) {
        try {
            UserRequest request = UserRequest.newBuilder()
                    .setUsername(username)
                    .build();

            MessageList response = blockingStub.getMessagesForUser(request);
            System.out.println("Messages de l'utilisateur " + username + ":");
            for (Message message : response.getMessagesList()) {
                System.out.println("Expéditeur: " + message.getSender());
                System.out.println("Message: " + message.getMessage());
                System.out.println();
            }
        } catch (StatusRuntimeException e) {
            System.err.println("RPC Echec: " + e.getStatus());
        }
    }

    public static void main(String[] args) throws Exception {
        MessagingClient client = new MessagingClient("localhost", 9023);
        try {
            // Sending a message
            client.sendMessage("Nesrine", "Nour", "hello");

            // Getting messages for a user
            client.getMessagesForUser("Nesrine");
        } finally {
            client.shutdown();
        }
    }
}
