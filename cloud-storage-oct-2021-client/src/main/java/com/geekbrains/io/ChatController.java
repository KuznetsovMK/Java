package com.geekbrains.io;

import com.geekbrains.model.AbstractMessage;
import com.geekbrains.model.Operation;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ChatController implements Initializable {


    public ListView<String> listView;
    public ListView<String> userlistView;
    public TextField input;
    public TextField curPath;
    public TextField userInput;
    public TextField localPath;
    private ObjectDecoderInputStream dis;
    private ObjectEncoderOutputStream dos;
    private Path localUserPath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Socket socket = new Socket("localhost", 8189);
            dos = new ObjectEncoderOutputStream(socket.getOutputStream());
            dis = new ObjectDecoderInputStream(socket.getInputStream());
            System.out.println("OK");

            localPath.setText("localroot");
            curPath.setText("root");

            File file = new File("root");
            String[] files = file.list();
            for (String str : files) {
                listView.getItems().add(str);
            }

            localUserPath = Paths.get("localroot");
            File userLocalFile = new File(String.valueOf(localUserPath));
            String[] userLocalFiles = userLocalFile.list();
            for (String str : userLocalFiles) {
                userlistView.getItems().add(str);
            }

            Thread readThread = new Thread(() -> {
                try {
                    while (true) {
                        AbstractMessage message = (AbstractMessage) dis.readObject();
                        Platform.runLater(() -> {
                            checkMsg(message.getMessage());
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

//    public void sendMessage(ActionEvent actionEvent) throws IOException {
//        String str = curPath.getText() + " " + input.getText();
//        input.clear();
//        dos.writeObject(new AbstractMessage(str));
//        dos.flush();
//    }

    private void checkMsg(String msg) {
        if (msg.split("\n")[0].equals("localFileInfo")) {
            userInput.clear();
            userInput.setText(msg.split("\n")[1]);
        }

        else if (msg.split("\n")[0].equals("serverFileInfo")) {
            input.clear();
            input.setText(msg.split("\n")[1]);
        } else {
            fillFilesView(msg);
        }

    }

    private void fillFilesView(String msg) {
        //0-Path, 1-infoMsg
        listView.getItems().clear();
        fillCurrentPath(msg.split("\n")[0]);
        String[] files = msg.split("\n");
        int c = msg.split("\n")[0].equals("root") ? 2 : 1;
        for (int i = c; i < files.length; i++) {
            listView.getItems().add(files[i]);
        }

        userlistView.getItems().clear();
//        fillCurrentPath(msg.split("\n")[0]);
        String[] localFiles = localUserPath.toFile().list();
//        int c = msg.split("\n")[0].equals("root") ? 2 : 1;
        for (int i = 0; i < localFiles.length; i++) {
            userlistView.getItems().add(localFiles[i]);
        }
    }

    private void fillCurrentPath(String s) {
        localPath.clear();
        localPath.setText("localroot");
        curPath.clear();
        curPath.setText(s);
    }

    public void moveFileToServer(ActionEvent actionEvent) throws IOException {
        MultipleSelectionModel<String> langsSelectionModel = userlistView.getSelectionModel();
        String selectFileName = langsSelectionModel.selectedItemProperty().getValue();
        if (!Files.isDirectory(localUserPath.resolve(selectFileName))) {
            String str1 = "fileOperation " + Operation.TO_SERVER + " " + selectFileName;
            sendMsg(str1);
        } else {
            userInput.setText("Can't move directory! Only files!");
        }
        langsSelectionModel.clearSelection();
    }

    public void moveFileToLocal(ActionEvent actionEvent) throws IOException {
        MultipleSelectionModel<String> langsSelectionModel = listView.getSelectionModel();
        String selectFileName = langsSelectionModel.selectedItemProperty().getValue();
        if (!localUserPath.resolve(selectFileName).toFile().exists()) {
            Files.createFile(localUserPath.resolve(selectFileName));
        }
        String str1 = "fileOperation " + Operation.TO_LOCAL + " " + selectFileName;
        sendMsg(str1);
        langsSelectionModel.clearSelection();
    }

    public void sendMsg(String s) throws IOException {
        String str = curPath.getText() + " " + s;
        dos.writeObject(new AbstractMessage(str));
        dos.flush();
    }

    public void delete(ActionEvent actionEvent) throws IOException {
        MultipleSelectionModel<String> langsSelectionModel = listView.getSelectionModel();
        String selectFileName = langsSelectionModel.selectedItemProperty().getValue();
        String str1 = "fileOperation " + Operation.DELETE + " " + selectFileName;
        sendMsg(str1);
        langsSelectionModel.clearSelection();
    }

    public void viewLocalFileInfo(MouseEvent mouseEvent) throws IOException {
        MultipleSelectionModel<String> langsSelectionModel = userlistView.getSelectionModel();
        String selectFileName = langsSelectionModel.selectedItemProperty().getValue();
        if (!Files.isDirectory(localUserPath.resolve(selectFileName))) {
            String str1 = "fileOperation " + Operation.LOCAL_FILE_INFO + " " + selectFileName;
            sendMsg(str1);
        } else {
            userInput.setText("Can't show directory info! Only files!");
        }
    }

    public void viewServerFileInfo(MouseEvent mouseEvent) throws IOException {
        MultipleSelectionModel<String> langsSelectionModel = listView.getSelectionModel();
        String selectFileName = langsSelectionModel.selectedItemProperty().getValue();
        String str1 = "fileOperation " + Operation.SERVER_FILE_INFO + " " + selectFileName;
        sendMsg(str1);

    }
}
