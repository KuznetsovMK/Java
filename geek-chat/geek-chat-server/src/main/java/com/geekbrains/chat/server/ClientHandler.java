package com.geekbrains.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {
    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;
    private ExecutorService service;

    public ClientHandler(Socket socket, Server server) throws IOException {

        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.server = server;
        this.service = Executors.newCachedThreadPool();

//        new Thread(() -> {
//            try {
//                //цикл авторизации и общения
//                while (true) {
//                    String msg = in.readUTF();
//
//                    if (msg.startsWith("/")) {
//                        executeCommand(msg);
//                        continue;
//                    }
//                    server.broadcastMessage(username + ": " + msg);
//                }
//            } catch (IOException | SQLException e) {
//                e.printStackTrace();
//            } finally {
//                disconnect();
//            }
//        }).start();


        service.execute(() -> {
            try {
                //цикл авторизации и общения
                while (true) {
                    String msg = in.readUTF();

                    if (msg.startsWith("/")) {
                        executeCommand(msg);
                        continue;
                    }
                    server.broadcastMessage(username + ": " + msg);
//                    System.out.println(service.toString());
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        });

    }

    private void executeCommand(String cmd) throws IOException, SQLException {
        //login
        if (cmd.startsWith("/login ")) {
            String usernameFromLogin = cmd.split("\\s")[1];

//            if (!server.isUserRegistered(usernameFromLogin)) {
//                sendMessage("/login_failed Current nickname is not found");
//                return;
//            }

            if (!UsersRegistry.isUserRegistered(usernameFromLogin)) {
                sendMessage("/login_failed Current nickname is not found");
                return;
            }

            username = usernameFromLogin;
            sendMessage("/login_ok " + username);
            server.subscribe(this);
            return;
        }
        if (cmd.startsWith("/w ")) {
            String[] tokens = cmd.split("\\s", 3);
            server.sendPrivateMessage(this, tokens[1], tokens[2]);
            return;
        }

        if (cmd.startsWith("/nick ")) {
            String newUsername = cmd.split("\\s")[1];
            username = server.updateNicknameMsg(username, newUsername);
            server.broadcastClientList();
        }

        if (cmd.startsWith("/exit")) {
            throw new IOException();
        }
    }

    private void disconnect() {
        server.unsubscribe(this);
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            disconnect();
        }
    }

    public String getUsername() {
        return username;
    }
}
