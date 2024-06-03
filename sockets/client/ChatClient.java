package sockets.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class ChatClient {

    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 5621;
        Scanner scanner = new Scanner(System.in);

        try {
            Socket socket = new Socket(hostname, port);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.print("Entrer votre nom: ");
            String userName = scanner.nextLine();
            writer.println(userName);

            // Thread to handle message sending
            Thread sendThread = new Thread(() -> {
                System.out.println("Bienvenue au chat. Taper 'au revoir' pour quitter le chat");
                while (!socket.isClosed()) {
                    String message = scanner.nextLine();
                    writer.println(message);
                    if ("au revoir".equalsIgnoreCase(message)) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            // Thread to handle message reception
            Thread receiveThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = reader.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });

            // Starting both threads
            sendThread.start();
            receiveThread.start();

            // Wait for the send thread to finish before closing resources
            sendThread.join();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        } finally {
            scanner.close();
            System.out.println("Client terminated.");
        }
    }
}
