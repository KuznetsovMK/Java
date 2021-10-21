package com.geekbrains.io;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    public ListView<String> listView;
    public TextField input;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Path path;
    private byte[] buffer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        buffer = new byte[1024];
//        System.out.println(System.getProperty("user.dir"));
        path = Paths.get("\\GB-cloud\\local_files");
//        System.out.println(path);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            fillFilesInView();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Socket socket = new Socket("localhost", 8189);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            Thread readThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = dis.readUTF();
                        Platform.runLater(() -> input.setText(message));
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

    private void fillFilesInView() {
        listView.getItems().clear();
        File file = new File(String.valueOf(path));
        String[] files = file.list();
        for (String str : files) {
            listView.getItems().add(str);
        }

    }

    public void sendMessage(ActionEvent actionEvent) throws IOException {
        String fileName = input.getText();
        input.clear();
        Path filePath = path.resolve(fileName);
        if (Files.exists(filePath)) {
            dos.writeUTF(fileName);
            dos.writeLong(Files.size(filePath));
            try (FileInputStream fis = new FileInputStream(filePath.toFile())) {
                int read;
                while ((read = fis.read(buffer)) != -1) {
                    dos.write(buffer, 0, read);
                }
            }
            dos.flush();
        }
    }

    public void selectFile(MouseEvent mouseEvent) {
        MultipleSelectionModel<String> langsSelectionModel = listView.getSelectionModel();
        String selectFileName = langsSelectionModel.selectedItemProperty().getValue();
        if (!Files.isDirectory(path.resolve(selectFileName))) {
            input.setText(selectFileName);
        } else {
            input.setText("Select file! Not directory");
        }
        langsSelectionModel.clearSelection();
    }
}
