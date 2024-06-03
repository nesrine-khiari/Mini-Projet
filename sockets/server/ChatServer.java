package sockets.server;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 5621;
    private static final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Chat Server is listening on port  " + PORT);

        try {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler newUser = new ClientHandler(socket);
                clients.add(newUser);
                new Thread(newUser).start();
            }
        } finally {
            serverSocket.close();
        }
    }

    // Broadcasts a message to all clients except the sender
    public static void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    // Handles client connections
    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private PrintWriter writer;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                writer = new PrintWriter(socket.getOutputStream(), true);

                String userName = reader.readLine();
                ChatServer.broadcastMessage(userName + " has joined the chat!", this);

                String clientMessage;

                do {
                    clientMessage = reader.readLine();
                    String messageToSend = userName + ": " + clientMessage;
                    ChatServer.broadcastMessage(messageToSend, this);
                } while (!clientMessage.equals("bye"));

                ChatServer.broadcastMessage(userName + " has left the chat.", this);
                clients.remove(this);
            } catch (IOException ex) {
                System.out.println("Server exception: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        void sendMessage(String message) {
            writer.println(message);
        }
    }
}
