package com.geekbrains.io;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatHandler implements Runnable {

    private static int counter = 0;
    private final String userName;
    private final Server server;
    private final DataInputStream dis;
    private final DataOutputStream dos;
//    private final InputStream is;
//    private final OutputStream os;
    private final SimpleDateFormat format;
    private final Path root;

    public ChatHandler(Socket socket, Server server) throws Exception {
        this.server = server;
        counter++;
        userName = "User#" + counter;
        format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        root = Path.of("root");
        if (!Files.exists(root)) {
            Files.createDirectory(root);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = dis.readUTF();
                File file = new File(msg.split(" ")[0]);
                Writer out = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(String.valueOf(root.resolve("user1").resolve(String.valueOf(file))))
                        )
                );
                out.write(msg.split(" ", 3)[2]);
                out.close();
            }
        } catch (Exception e) {
            System.err.println("Connection was broken");
            e.printStackTrace();
        }
    }

    public String getMessage(String msg) {
        return getTime() + " [" + userName + "]: " + msg;
    }

    public String getTime() {
        return format.format(new Date());
    }

    public void sendMessage(String msg) throws Exception {
        dos.writeUTF(msg);
        dos.flush();
    }
}
