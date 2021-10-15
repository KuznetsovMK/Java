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
import java.nio.charset.StandardCharsets;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Socket socket = new Socket("localhost", 8189);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

//            path = FileSystems.getDefault().getPath("cloud-storage-oct-2021-client/src/main/resources/com/geekbrains/io/files");
//            File file = new File(String.valueOf(path));
//            String[] files = file.list();

            Thread readThread = new Thread(() -> {
                try {
                    path = Paths.get("K:\\GB-cloud\\local_files");
                    if (!Files.exists(path)) {
                        Files.createDirectory(path);
                    }
                    File file = new File(String.valueOf(path));
                    String[] files = file.list();
//                    while (true) {
//                        String message = dis.readUTF();
//                        Platform.runLater(() -> listView.getItems().add(message));
//                    }
                    for (String str : files) {
                        Platform.runLater(() -> listView.getItems().add(str));
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
        StringBuilder stringBuilder = new StringBuilder();
        String fileName = input.getText();

        InputStream is = new FileInputStream(path.resolve(fileName).toFile());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        int readBytes;

        while ((readBytes = bufferedReader.read()) != -1) {
            stringBuilder.append((char) readBytes);
        }

        long fileSize = stringBuilder.toString().getBytes(StandardCharsets.UTF_8).length;

        bufferedReader.close();
        String message = fileName + " " + fileSize + " " + stringBuilder.toString();
//        System.out.println(fileSize);

        dos.writeUTF(message);
        dos.flush();
        input.clear();
    }

    public void selectFile(MouseEvent mouseEvent) {
        MultipleSelectionModel<String> langsSelectionModel = listView.getSelectionModel();
        String selectFileName = langsSelectionModel.selectedItemProperty().getValue();
        input.setText(selectFileName);
        langsSelectionModel.clearSelection();
    }
}
