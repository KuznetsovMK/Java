package com.geekbrains.io;

import com.geekbrains.model.*;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    public TextField curPathFiled;
    public TextField userInput;
    public TextField localPathField;
    public TextField loginField;
    public PasswordField passwordField;
    public Button loginButton;
    public Button registrationButton;
    public Button toLocalButton;
    public Button toServerButton;
    public Button deleteButton;
    public Button backButton;
    public Button newFolder;
    private ObjectDecoderInputStream dis;
    private ObjectEncoderOutputStream dos;
    private Path localUserPath;
    private Path serverCurrentPath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Socket socket = new Socket("localhost", 8189);
            dos = new ObjectEncoderOutputStream(socket.getOutputStream());
            dis = new ObjectDecoderInputStream(socket.getInputStream());
            System.out.println("OK");

            listView.setCellFactory(TextFieldListCell.forListView());

            localUserPath = Paths.get("localroot");
            localPathField.setText("localroot");

            File userLocalFile = new File(String.valueOf(localUserPath));
            String[] userLocalFiles = userLocalFile.list();

            for (String str : userLocalFiles) {
                userlistView.getItems().add(str);
            }

            Thread readThread = new Thread(() -> {

                try {
                    while (true) {
                        AbstractMessage message = (AbstractMessage) dis.readObject();
                        checkMsg(message);
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

    private void checkMsg(AbstractMessage msg) throws Exception {

        switch (msg.getType()) {

            case SERVER_LIST_MESSAGE:

                ListMessage listMessage = (ListMessage) msg;

                System.out.println(listMessage.getFiles().toString());

                Platform.runLater(() -> {
                    listView.getItems().clear();
                    listView.getItems().addAll(listMessage.getFiles());
                });
                break;

            case LOCAL_LIST_MESSAGE:

                LocalListMessage localListMessage = (LocalListMessage) msg;

                Platform.runLater(() -> {
                    userlistView.getItems().clear();
                    userlistView.getItems().addAll(localListMessage.getFiles());
                });
                break;

            case FILE_MESSAGE_TO_LOCAL:

                FileMessageToLocal fileMessageToLocal = (FileMessageToLocal) msg;
                Path file = localUserPath.resolve(fileMessageToLocal.getName());

                if (fileMessageToLocal.isFirstButch()) {
                    Files.deleteIfExists(file);
                }

                try (FileOutputStream os = new FileOutputStream(file.toFile(), true)) {
                    os.write(fileMessageToLocal.getBytes(), 0, fileMessageToLocal.getEndByteNum());
                }

                if (fileMessageToLocal.isFinishBatch()) {

                    dos.writeObject(new LocalListMessage(localUserPath));
                    dos.flush();
                }
                break;

            case FILE_REQUEST_TO_SERVER:

                FileRequestToServer fileRequestToServer = (FileRequestToServer) msg;
                byte[] buffer = new byte[8192];
                boolean isFirstButch = true;
                Path filePath = localUserPath.resolve(fileRequestToServer.getName());
                long size = Files.size(filePath);

                try (FileInputStream is = new FileInputStream(localUserPath.resolve(fileRequestToServer.getName()).toFile())) {

                    int read;
                    while ((read = is.read(buffer)) != -1) {

                        FileMessageToServer message = new FileMessageToServer(filePath.getFileName().toString(),
                                size, buffer, isFirstButch, read, is.available() <= 0);
                        dos.writeObject(message);
                        dos.flush();
                        isFirstButch = false;
                    }
                } catch (Exception e) {
//            log.error("e:", e);
                }
                break;

            case SYSTEM_MESSAGE:

                SystemMessage systemMessage = (SystemMessage) msg;

                if (systemMessage.getMessage() != null) {
                    input.clear();
                    input.setText(systemMessage.getMessage());
                }
                break;

            case CHANGE_PATH:

                CurrentUserPath currentUserPath = (CurrentUserPath) msg;

                if (currentUserPath.getPath() != null) {

                    serverCurrentPath = Paths.get(currentUserPath.getPath());
                    curPathFiled.clear();
                    curPathFiled.setText(currentUserPath.getPath());
                }
                break;

            case LOGIN_OK:

                if (serverCurrentPath == null) {

                    listView.setEditable(false);
                    localPathField.setVisible(false);
                    userlistView.setVisible(false);
                    toLocalButton.setVisible(false);
                    toServerButton.setVisible(false);
                    deleteButton.setVisible(false);
                    backButton.setVisible(false);
                    newFolder.setVisible(false);
                } else {
                    listView.setEditable(true);
                    loginField.setVisible(false);
                    passwordField.setVisible(false);
                    loginButton.setVisible(false);
                    registrationButton.setVisible(false);
                    localPathField.setVisible(true);
                    userlistView.setVisible(true);
                    toLocalButton.setVisible(true);
                    toServerButton.setVisible(true);
                    deleteButton.setVisible(true);
                    backButton.setVisible(true);
                    newFolder.setVisible(true);
                }
                break;
        }
    }

    public void moveFileToServer(ActionEvent actionEvent) throws IOException {

        MultipleSelectionModel<String> langsSelectionModel = userlistView.getSelectionModel();
        String selectFileName = langsSelectionModel.selectedItemProperty().getValue();

        if (selectFileName != null) {

            if (!Files.isDirectory(localUserPath.resolve(selectFileName))) {
                dos.writeObject(new FileRequestToServer(selectFileName));
                dos.flush();
            } else {
                userInput.setText("Can't move directory! Only files!");
            }
        }
        langsSelectionModel.clearSelection();
    }

    public void moveFileToLocal(ActionEvent actionEvent) throws IOException {

        MultipleSelectionModel<String> langsSelectionModel = listView.getSelectionModel();
        String selectFileName = langsSelectionModel.selectedItemProperty().getValue();

        if (selectFileName != null) {

            if (!Files.isDirectory(serverCurrentPath.resolve(selectFileName))) {
                dos.writeObject(new FileRequestToLocal(selectFileName));
                dos.flush();
            } else {
                userInput.setText("Can't move directory! Only files!");
            }
        }
        langsSelectionModel.clearSelection();
    }

    public void deleteButtonAction(ActionEvent actionEvent) throws IOException {

        MultipleSelectionModel<String> langsSelectionModel = listView.getSelectionModel();
        String selectFileName = langsSelectionModel.selectedItemProperty().getValue();

        if (selectFileName != null) {
            dos.writeObject(new DeleteFile(selectFileName));
            dos.flush();
        }
        langsSelectionModel.clearSelection();
    }

    public void editCommit(ListView.EditEvent<String> stringEditEvent) throws IOException {

        MultipleSelectionModel<String> langsSelectionModel = listView.getSelectionModel();
        String oldName = langsSelectionModel.selectedItemProperty().getValue();

        listView.getItems().add(stringEditEvent.getIndex(), stringEditEvent.getNewValue());
        listView.getItems().remove(stringEditEvent.getIndex() + 1);

        String newName = stringEditEvent.getNewValue();

        dos.writeObject(new RenameFile(oldName, newName));
        dos.flush();

        langsSelectionModel.clearSelection();
    }

    public void backButtonAction(ActionEvent actionEvent) throws IOException {

        if (serverCurrentPath.getParent() != null) {
            dos.writeObject(new CurrentUserPath(serverCurrentPath.getParent().toString()));
            dos.flush();
        }
    }

    public void newFolder(ActionEvent actionEvent) throws IOException {

        dos.writeObject(new CreateNewFolder(serverCurrentPath.toString()));
        dos.flush();
    }

    public void registration(ActionEvent actionEvent) throws IOException {

        String login = loginField.getText();
        String pas = passwordField.getText();

        dos.writeObject(new UserRegistry(login, pas));
        dos.flush();
    }

    public void login(ActionEvent actionEvent) throws IOException {

        String login = loginField.getText();
        String pas = passwordField.getText();
        passwordField.clear();

        dos.writeObject(new UserLogin(login, pas));
        dos.flush();
    }

    public void listViewMouseClicked(MouseEvent mouseEvent) throws IOException {

        if (mouseEvent.getClickCount() == 2) {

            MultipleSelectionModel<String> langsSelectionModel = listView.getSelectionModel();
            String selectFileName = langsSelectionModel.selectedItemProperty().getValue();

            if (selectFileName != null) {

                if (Files.isDirectory(serverCurrentPath.resolve(selectFileName))) {
                    serverCurrentPath = serverCurrentPath.resolve(selectFileName);
                    dos.writeObject(new CurrentUserPath(serverCurrentPath.toString()));
                } else {
                    dos.writeObject(new FileRequestToLocal(selectFileName));
                }
                dos.flush();
            }
        }
    }

    public void keyPressed(KeyEvent keyEvent) throws IOException {

        MultipleSelectionModel<String> langsSelectionModel = listView.getSelectionModel();
        String selectFileName = langsSelectionModel.selectedItemProperty().getValue();

        if (selectFileName != null) {

            if (keyEvent.getCode().toString().equals("ENTER")) {

                if (Files.isDirectory(serverCurrentPath.resolve(selectFileName))) {
                    serverCurrentPath = serverCurrentPath.resolve(selectFileName);
                    dos.writeObject(new CurrentUserPath(serverCurrentPath.toString()));
                    dos.flush();
                }
            }
            if (keyEvent.getCode().toString().equals("DELETE")) {
                dos.writeObject(new DeleteFile(selectFileName));
                dos.flush();
            }
        }
        if (keyEvent.getCode().toString().equals("BACK_SPACE")) {

            if (serverCurrentPath.getParent() != null) {
                dos.writeObject(new CurrentUserPath(serverCurrentPath.getParent().toString()));
                dos.flush();
            }
        }
        langsSelectionModel.clearSelection();
    }
}
