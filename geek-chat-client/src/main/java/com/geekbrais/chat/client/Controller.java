package com.geekbrais.chat.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Controller {
    @FXML
    TextArea mainTextArea;

    @FXML
    TextField bottomTextField, usernameField;

    @FXML
    HBox loginPanel, msgPanel;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;

    public void sendMsg(ActionEvent e) {
        if (!bottomTextField.getText().equals("")) {
            String msg = bottomTextField.getText() + "\n";
            try {
                out.writeUTF(msg);
                bottomTextField.clear();
            } catch (IOException ioException) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "unable to send message.", ButtonType.OK);
                alert.showAndWait();
            }
            bottomTextField.requestFocus();
        }
    }

    public void login(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()) {
            connect();
        }
        if (usernameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "user name cannot be empty.",
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
        } else {
            loginPanel.setVisible(true);
            loginPanel.setManaged(true);
            msgPanel.setVisible(false);
            msgPanel.setManaged(false);
        }
    }

    public void connect() {
        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            Thread thread = new Thread(() -> {

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
                            mainTextArea.appendText(cause + '\n');
                        }

                    }

                    //цикл общения
                    while (true) {
                        String msg = in.readUTF();
                        mainTextArea.appendText(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    disconnect();
                }
            });
            thread.start();
        } catch (IOException e) {
            throw new RuntimeException("Unable to connect to server [localhost: 8189]");
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
}
