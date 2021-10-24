package com.geekbrains.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;
    private List<ClientHandler> clients;
    private static Connection connection;
    private static Statement statement;

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту " + port + "...");
            while (true) {
                System.out.println("Ждём нового клиента...");
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(socket, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
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

//    public synchronized boolean isUserOnline(String nickname) {
//        for (ClientHandler clientHandler : clients) {
//            if (clientHandler.getUsername().equals(nickname)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public synchronized boolean isUserRegistered(String nickname) {
        try {
            connect();
            if (selectUsers(nickname)) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }

    public static boolean selectUsers(String name) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT name FROM users;");

        while (rs.next()) {
            if (name.equals(rs.getString("name"))) {
                rs.close();
                return true;
            }
        }
        rs.close();
        return false;
    }

    public synchronized String updateNickname(String oldNick, String newNick) {
        try {
            connect();
            if (setUpdateUsername(oldNick, newNick)) {
                broadcastMessage("Клиент " + oldNick + " изменил имя на: " + newNick + "\n");
                return newNick;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            disconnect();
        }
        return oldNick;
    }

    public static boolean setUpdateUsername(String name, String updateName) throws SQLException {
//        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET name = ? WHERE name = ?");
//        preparedStatement.setString(1, updateName);
//        preparedStatement.setString(2, name);

        statement.executeUpdate(String.format("UPDATE users SET name = '%s' WHERE name = '%s'", updateName, name));
        return true;
    }

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        statement = connection.createStatement();
    }

    public static void disconnect() {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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
