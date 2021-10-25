package com.geekbrains.chat.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller {
    @FXML
    TextField msgField, usernameField;

    @FXML
    TextArea msgArea;

    @FXML
    HBox loginPanel, msgPanel;

    @FXML
    Button logout;

    @FXML
    ListView<String> clientList;

    private Socket socket;
    private DataInputStream in;

    public String getUsername() {
        return username;
    }

    private DataOutputStream out;
    private String username;
    File logFile;
    FileWriter fileWriter;
    BufferedWriter bufferedWriter;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    public void sendMsg(ActionEvent actionEvent) {
        String msg = msgField.getText() + '\n';
        try {
            out.writeUTF(msg);
            msgField.clear();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Невозможно отправить сообщение", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void login(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()) {
            connect();
        }

        if (usernameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Имя пользователя не может быть пустым",
                    ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            out.writeUTF("/login " + usernameField.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsername(String username) {
        this.username = username;
        if (username != null) {
            loginPanel.setVisible(false);
            loginPanel.setManaged(false);
            msgPanel.setVisible(true);
            msgPanel.setManaged(true);
            logout.setVisible(true);
            logout.setManaged(true);
        } else {
            loginPanel.setVisible(true);
            loginPanel.setManaged(true);
            msgPanel.setVisible(false);
            msgPanel.setManaged(false);
            logout.setVisible(false);
            logout.setManaged(false);
        }
    }

    public void connect() {


        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());


            Thread dataThread = new Thread(() -> {
                try {
                    //цикл авторизации
                    while (true) {
                        String msg = in.readUTF();

                        //login_ok Alex
                        if (msg.startsWith("/login_ok ")) {
                            setUsername(msg.split("\\s")[1]);
                            break;
                        }

                        if (msg.startsWith("/login_failed ")) {
                            String cause = msg.split("\\s", 2)[1];
                            msgArea.appendText(cause + '\n');
                        }
                    }

                    //Создание файла лога
                    createLogFile();

                    readSomeLogLines();

                    //Зпись лога
                    try {
                        fileWriter = new FileWriter(logFile, true);
                        bufferedWriter = new BufferedWriter(fileWriter);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    //цикл общения
                    while (true) {
                        String msg = in.readUTF();
                        if (msg.startsWith("/")) {
                            executeCommand(msg);
                            continue;
                        }
                        Calendar cal = Calendar.getInstance();
                        msg = "[" + dateFormat.format(cal.getTime()) + "] " + msg;

                        writeToFile(msg);
                        msgArea.appendText(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    disconnect();
                }
            });
            dataThread.start();

        } catch (IOException e) {
            throw new RuntimeException("Unable to connect to server [localhost: 8189]");
        }
    }

    private void executeCommand(String cmd) {
        if (cmd.startsWith("/clients_list ")) {
            String[] tokens = cmd.split("\\s");

            Platform.runLater(() -> {
                clientList.getItems().clear();
                for (int i = 1; i < tokens.length; i++) {
                    clientList.getItems().add(tokens[i]);
                }
            });
        }
    }

    public void disconnect() {

        if (fileWriter != null) {
            try {
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setUsername(null);
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void logout(ActionEvent actionEvent) {
        try {
            out.writeUTF("/exit");
            clientList.getItems().clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createLogFile() {
        logFile = new File(username + "_log.txt");
    }

    public void writeToFile(String msg) throws IOException {
        bufferedWriter.write(msg);
    }

    public void readSomeLogLines(){
        msgArea.clear();
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(logFile, "r")) {
            int cnt = 0;
            String str;

            for (long i = randomAccessFile.length() - 2; i >= 0; i--) {
                randomAccessFile.seek(i);
                if (randomAccessFile.read() == '\n') cnt++;
                if (cnt == 100) {
                    System.out.println(i);
                    byte[] bytes = new byte[(int) (randomAccessFile.length() - i)];
                    randomAccessFile.read(bytes);
                    str = new String(bytes, StandardCharsets.UTF_8);
                    msgArea.appendText(str + "\n");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
