package com.geekbrains.chat.server;

import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;
    private List<ClientHandler> clients;
    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(Server.class);


    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();


        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Сервер запущен на порту " + port + "...");
//            System.out.println("Сервер запущен на порту " + port + "...");
            new UsersRegistry(); //CQL: connection & statement & preparedStatement


            while (true) {
                log.info("Ждём нового клиента...");
//                System.out.println("Ждём нового клиента...");
                Socket socket = serverSocket.accept();
                log.info("Клиент подключился");
//                System.out.println("Клиент подключился");
                new ClientHandler(socket, this);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            UsersRegistry.disconnect();
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastMessage("Клиент " + clientHandler.getUsername() + " подлючился \n");
        broadcastClientList();
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastMessage("Клиент " + clientHandler.getUsername() + " отключился \n");
        broadcastClientList();
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(message);
        }
    }

    public synchronized void sendPrivateMessage(ClientHandler sender, String receiverUsername, String message) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(receiverUsername)) {
                client.sendMessage("От: " + sender.getUsername() + " Сообщение: " + message);
                sender.sendMessage("Пользователю: " + receiverUsername + " Сообщение: " + message);
                return;
            }
        }

        sender.sendMessage("Невозможно отправить сообщение пользователю: " + receiverUsername + ". Такого пользователя нет.");
    }

    public synchronized void sendPrivateMessageFromServer(String receiverUsername, String message) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(receiverUsername)) {
                client.sendMessage("Server: " + message);
                return;
            }
        }
    }

    public synchronized String updateNicknameMsg(String oldNick, String newNick) {
        try {
            if (UsersRegistry.setUpdateUsername(oldNick, newNick)) {
                broadcastMessage("Клиент " + oldNick + " изменил имя на: " + newNick + "\n");
                return newNick;
            }
            String msgFailUpdateName = "Данное имя занято.\n";
            sendPrivateMessageFromServer(oldNick, msgFailUpdateName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return oldNick;
    }

    void broadcastClientList() {
        StringBuilder stringBuilder = new StringBuilder("/clients_list ");
        for (ClientHandler client : clients) {
            stringBuilder.append(client.getUsername()).append(" ");
        }
        // /clients_list Alex Bob Nick
        stringBuilder.setLength(stringBuilder.length() - 1);
        String clientsList = stringBuilder.toString();
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(clientsList);
        }
    }
}
