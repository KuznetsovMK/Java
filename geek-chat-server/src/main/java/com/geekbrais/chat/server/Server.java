package com.geekbrais.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;
    private List<ClientHandler> clients;

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. port: " + port + ".");
            while (true) {
                System.out.println("Waiting for new client connections...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected.");
                new ClientHandler(socket, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public void broadcastMsg(String message) throws IOException {
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(message);
        }
    }

    public boolean privateMessage(String message) throws IOException {
        String nicknameFrom = message.split("\\s")[0];
        String nicknameTo = message.split("\\s")[2];
        String privateMsg = message.split("\\s", 4)[3];
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.getUsername().equals(nicknameTo)) {
                clientHandler.sendMessage(nicknameFrom + " <private>: " + privateMsg);
                return true;
            }
        }
        return false;
    }

    public boolean isNickBusy(String nickname) {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.getUsername().equals(nickname)) {
                return true;
            }
        }
        return false;
    }
}
