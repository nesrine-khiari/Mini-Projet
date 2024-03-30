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

import java.util.Scanner;
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

    public static void main(String[] args) throws InterruptedException {
        MessagingClient client = new MessagingClient("localhost", 9023); 
        Scanner scanner = new Scanner(System.in); 
        try {
         
            System.out.println("Entrer votre nom:");
            String sender = scanner.nextLine(); 
            System.out.println("Entrez le nom du destinataire:");
            String recipient = scanner.nextLine(); 
            System.out.println("Entrer le message:");
            String message = scanner.nextLine(); 
            client.sendMessage(sender, recipient, message); 


            System.out.println("Entrez le nom d'un utilisateur pour vérifier les messages qu'il a reçus:");
            recipient = scanner.nextLine(); 
            client.getMessagesForUser(recipient); 
        } finally {
            client.shutdown(); 
        }
    }
}
