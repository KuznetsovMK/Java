package com.geekbrais.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.server = server;

        new Thread(() -> {
            try {
                //цикл авторизации
                while (true) {
                    String msg = in.readUTF();
                    // /login Alex
                    if (msg.startsWith("/login ")) {
                        String usernameFromLogin = msg.split("\\s")[1];

                        if (server.isNickBusy(usernameFromLogin)) {
                            sendMessage("/login_failed Current nickname has already been occupied");
                            continue;
                        }

                        username = usernameFromLogin;
                        sendMessage("/login_ok " + username);
                        server.subscribe(this);
                        break;
                    }
                }

                //цикл общения
                while (true) {
                    String msg = in.readUTF();
                    if (msg.startsWith("/who_am_i")) {
                        sendMessage("Your nickname is: " + username + '\n');
                        continue;
                    }
                    if (msg.split("\\s")[0].equals("/w")) {
                        String privateMsg = msg.split("\\s", 3)[2];
                        if (server.privateMessage(username + " " + msg)) {
                            sendMessage(username + " <private>: " + privateMsg);
                        }
                        continue;
                    }
                    if (msg.startsWith("/exit")) {
                        sendMessage("bye-bye, see you later.\n");
                        disconnect();
                    }
                    server.broadcastMsg(username + ": " + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }).start();
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
    }

    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
    }

    public String getUsername() {
        return username;
    }
}
