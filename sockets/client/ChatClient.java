package sockets.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5621;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)) {
            System.out.println("Connecté au serveur de chat.");

            // Thread pour lire les messages du serveur
            Thread readThread = new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.err.println("Erreur lors de la lecture des messages du serveur : " + e.getMessage());
                }
            });
            readThread.start();

            // Envoi des messages saisis par l'utilisateur
            String userInput;
            while ((userInput = scanner.nextLine()) != null) {
                out.println(userInput);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la connexion au serveur de chat : " + e.getMessage());
        }
    }
}
