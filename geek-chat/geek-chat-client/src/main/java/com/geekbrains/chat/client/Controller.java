package com.geekbrains.chat.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.*;
import java.net.Socket;

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
    private DataOutputStream out;
    private String username;
    File logFile;
    BufferedWriter bufferedWriter;

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
        logFile = new File(username + "_log.txt");
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
                            readSomeLogLines();
                            break;
                        }

                        if (msg.startsWith("/login_failed ")) {
                            String cause = msg.split("\\s", 2)[1];
                            msgArea.appendText(cause + '\n');
                        }
                    }

                    //цикл общения
                    while (true) {
                        String msg = in.readUTF();
                        if (msg.startsWith("/")) {
                            executeCommand(msg);
                            continue;
                        }
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

    public void writeToFile(String msg) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(logFile, true))) {
            bufferedWriter.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readSomeLogLines() {

        String str = "";
        int totalLines = 0;
        int count = 0;
        int a = 100;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(logFile))) {
            while ((bufferedReader.readLine()) != null) {
                totalLines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(logFile))) {
            while ((str = bufferedReader.readLine()) != null) {
                if (count >= (totalLines - a)) {
                    msgArea.appendText(str + "\n");
                }
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
