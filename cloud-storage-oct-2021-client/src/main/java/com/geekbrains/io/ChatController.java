package com.geekbrains.io;

import com.geekbrains.model.AbstractMessage;
import com.geekbrains.model.UserNavigator;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class ChatController implements Initializable {


    public ListView<String> listView;
    public TextField input;
    public TextField curPath;
    private ObjectDecoderInputStream dis;
    private ObjectEncoderOutputStream dos;
    private File file;
    private String[] files;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Socket socket = new Socket("localhost", 8189);
            dos = new ObjectEncoderOutputStream(socket.getOutputStream());
            dis = new ObjectDecoderInputStream(socket.getInputStream());
            System.out.println("OK");

            curPath.setText("root");

            File file = new File("root");
            String[] files = file.list();
            for (String str : files) {
                listView.getItems().add(str);
            }


            Thread readThread = new Thread(() -> {
                try {
                    while (true) {
                        AbstractMessage message = (AbstractMessage) dis.readObject();
//                        UserNavigator pathMsg = (UserNavigator) dis.readObject();
                        Platform.runLater(() -> {
                            fillFilesView(message.getMessage());
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            readThread.setDaemon(true);
            readThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(ActionEvent actionEvent) throws IOException {
        String str = curPath.getText() + " " + input.getText();
        input.clear();
        dos.writeObject(new AbstractMessage(str));
        dos.flush();
    }

    private void fillFilesView(String msg) {
        listView.getItems().clear();
        fillCurrentPath(msg.split("\n")[0]);
        String[] files = msg.split("\n");
        for (int i = 1; i < files.length; i++) {
            listView.getItems().add(files[i]);
        }
    }

    private void fillCurrentPath(String s) {
        curPath.clear();
        curPath.setText(s);
    }
}
