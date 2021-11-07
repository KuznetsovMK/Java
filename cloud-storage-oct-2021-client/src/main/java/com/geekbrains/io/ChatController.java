package com.geekbrains.io;

import com.geekbrains.model.*;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
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


@Slf4j
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
    public Button newFolderButton;
    public Button addDirButton;
    public ContextMenu contextMenu;
    private Socket socket;
    private ObjectDecoderInputStream dis;
    private ObjectEncoderOutputStream dos;
    private Path localUserPath;
    private Path serverCurrentPath;
    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;
    private Stage primaryStage;
    private Desktop desktop;
    private String selectFileName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Image imgAddDirButton = new Image("com/geekbrains/io/img/add_24.png");
            addDirButton.graphicProperty().setValue(new ImageView(imgAddDirButton));
            addDirButton.setTooltip(new Tooltip("Add files"));
            Image imgToServerButton = new Image("com/geekbrains/io/img/upload_24.png");
            toServerButton.graphicProperty().setValue(new ImageView(imgToServerButton));
            toServerButton.setTooltip(new Tooltip("Upload file"));
            Image imgDeleteButton = new Image("com/geekbrains/io/img/del_24.png");
            deleteButton.graphicProperty().setValue(new ImageView(imgDeleteButton));
            deleteButton.setTooltip(new Tooltip("Delete file"));
            Image imgToLocalButton = new Image("com/geekbrains/io/img/download_24.png");
            toLocalButton.graphicProperty().setValue(new ImageView(imgToLocalButton));
            toLocalButton.setTooltip(new Tooltip("Download file"));
            Image imgNewFolderButton = new Image("com/geekbrains/io/img/newFolder_24.png");
            newFolderButton.graphicProperty().setValue(new ImageView(imgNewFolderButton));
            newFolderButton.setTooltip(new Tooltip("Create new folder"));
            Image imgBackButton = new Image("com/geekbrains/io/img/back_24.png");
            backButton.graphicProperty().setValue(new ImageView(imgBackButton));
            backButton.setTooltip(new Tooltip("Back"));

            fileChooser = new FileChooser();
            directoryChooser = new DirectoryChooser();
            desktop = Desktop.getDesktop();

//            cm = new ContextMenu();
//            MenuItem menuItem1 = new MenuItem("Open");
//            menuItem1.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent event) {
//                    System.out.println(event.toString());
//                    System.out.println("addEventHandler");
//                    if (event.getButton() == MouseButton.PRIMARY) {
//                        System.out.println("openFile()" );
//                    }
//
//                }
//            });
//            MenuItem menuItem2 = new MenuItem("Delete");
//            MenuItem menuItem3 = new MenuItem("Download");
//            cm.getItems().addAll(menuItem1, menuItem2, menuItem3);

            socket = new Socket("localhost", 8189);
            dos = new ObjectEncoderOutputStream(socket.getOutputStream());
            dis = new ObjectDecoderInputStream(socket.getInputStream());
            System.out.println("OK");

            listView.setCellFactory(TextFieldListCell.forListView());

            Thread readThread = new Thread(() -> {

                try {
                    while (true) {
                        AbstractMessage message = (AbstractMessage) dis.readObject();
                        checkMsg(message);
                    }
                } catch (Exception e) {
                    log.error("e:", e);
                } finally {
                    disconnect();
                }
            });

            readThread.setDaemon(true);
            readThread.start();
        } catch (IOException e) {
            log.error("e:", e);
//            e.printStackTrace();
        }
    }


    private void checkMsg(AbstractMessage msg) throws Exception {
        log.debug("checkMsg {}", msg);

        switch (msg.getType()) {

            case SERVER_LIST_MESSAGE:

                ListMessage listMessage = (ListMessage) msg;

                Platform.runLater(() -> {
                    listView.getItems().clear();
                    listView.getItems().addAll(listMessage.getFiles());
                });

                serverCurrentPath = Paths.get(listMessage.getPathName());
                curPathFiled.clear();
                curPathFiled.setText(String.valueOf(serverCurrentPath));
                break;

            case LOCAL_LIST_MESSAGE:

                LocalListMessage localListMessage = (LocalListMessage) msg;

                log.debug(localListMessage.getPathName());

                localPathField.clear();
                localPathField.setText(localListMessage.getPathName());

                Platform.runLater(() -> {
                    userlistView.getItems().clear();
                    userlistView.getItems().addAll(localListMessage.getFiles());
                });
                break;

            case FILE_MESSAGE_TO_LOCAL:

                FileMessageToLocal fileMessageToLocal = (FileMessageToLocal) msg;
                log.debug(String.valueOf(fileMessageToLocal));

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
                    input.clear();
                    input.setText("Download successfully!");
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
                    log.error("e:", e);
                }
                break;

            case SYSTEM_MESSAGE:

                SystemMessage systemMessage = (SystemMessage) msg;

                if (systemMessage.getMessage() != null) {
                    input.clear();
                    input.setText(systemMessage.getMessage());
                }
                break;

            case LOGIN_OK:

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
                newFolderButton.setVisible(true);
                addDirButton.setVisible(true);
                break;

            case OPEN_FILE:
                OpenFile of = (OpenFile) msg;
                openFile(new File(of.getPath()));
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

        if (selectFileName != null || localUserPath != null) {

            if (!Files.isDirectory(serverCurrentPath.resolve(selectFileName))) {
                primaryStage = (Stage) toLocalButton.getScene().getWindow();
                fileChooser.setInitialFileName(selectFileName);
                File file = fileChooser.showSaveDialog(primaryStage);

                if (file != null) {
                    localUserPath = Paths.get(file.getParent());

                    dos.writeObject(new FileRequestToLocal(selectFileName));
                    dos.flush();
                }
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

    public void backButtonAction(ActionEvent actionEvent) throws Exception {

        if (serverCurrentPath.getParent() != null) {
            dos.writeObject(new ListMessage(serverCurrentPath.getParent()));
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
        passwordField.clear();

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

    public void listViewMouseClicked(MouseEvent mouseEvent) throws Exception {

        MultipleSelectionModel<String> langsSelectionModel = listView.getSelectionModel();
        selectFileName = langsSelectionModel.selectedItemProperty().getValue();

        if (selectFileName != null) {

            if (mouseEvent.getClickCount() == 2) {

                if (Files.isDirectory(serverCurrentPath.resolve(selectFileName))) {
                    serverCurrentPath = serverCurrentPath.resolve(selectFileName);
                    dos.writeObject(new ListMessage(serverCurrentPath));
                } else {
                    dos.writeObject(new FileRequestToLocal(selectFileName));
                }
                dos.flush();
            }
        }
    }

    public void logout(ActionEvent actionEvent) throws Exception {
        dos.writeObject(new ListMessage(null));
        dos.flush();
        serverCurrentPath = null;
        listView.getItems().clear();
        curPathFiled.clear();

        loginField.setVisible(true);
        passwordField.setVisible(true);
        loginButton.setVisible(true);
        registrationButton.setVisible(true);

        listView.setEditable(false);

        localPathField.setVisible(false);
        userlistView.setVisible(false);
        toLocalButton.setVisible(false);
        toServerButton.setVisible(false);
        deleteButton.setVisible(false);
        backButton.setVisible(false);
        addDirButton.setVisible(false);
        newFolderButton.setVisible(false);
    }

    public void disconnect() {

        if (dos != null) {
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (dos != null) {
            try {
                dos.close();
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

    public void chooseDir(ActionEvent actionEvent) throws Exception {

        primaryStage = (Stage) addDirButton.getScene().getWindow();
        File file = directoryChooser.showDialog(primaryStage);
        localUserPath = Paths.get(String.valueOf(file));

        if (localUserPath != null) {
            dos.writeObject(new LocalListMessage(localUserPath));
            dos.flush();
        }

    }

    private void openFile(File file) {

        try {
            this.desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void keyPressedServerListView(KeyEvent keyEvent) throws Exception {

        MultipleSelectionModel<String> langsSelectionModel = listView.getSelectionModel();
        String selectFileName = langsSelectionModel.selectedItemProperty().getValue();

        if (selectFileName != null) {

            if (keyEvent.getCode().toString().equals("ENTER")) {

                if (Files.isDirectory(serverCurrentPath.resolve(selectFileName))) {
                    serverCurrentPath = serverCurrentPath.resolve(selectFileName);
                    dos.writeObject(new ListMessage(serverCurrentPath));
                    dos.flush();
                } else {
                    openFile(serverCurrentPath.resolve(selectFileName).toFile());
                }
            }
            if (keyEvent.getCode().toString().equals("DELETE")) {
                dos.writeObject(new DeleteFile(selectFileName));
                dos.flush();
            }
        }
        if (keyEvent.getCode().toString().equals("BACK_SPACE")) {

            if (serverCurrentPath.getParent() != null) {
                dos.writeObject(new ListMessage(serverCurrentPath.getParent()));
                dos.flush();
            }
        }
        langsSelectionModel.clearSelection();
    }


    public void keyPressedLocalListView(KeyEvent keyEvent) {

        MultipleSelectionModel<String> langsSelectionModel = userlistView.getSelectionModel();
        String selectFileName = langsSelectionModel.selectedItemProperty().getValue();

        if (selectFileName != null) {

            if (keyEvent.getCode().toString().equals("ENTER")) {

                if (!Files.isDirectory(localUserPath.resolve(selectFileName))) {
                    openFile(localUserPath.resolve(selectFileName).toFile());
                }
            }
        }
        langsSelectionModel.clearSelection();
    }

    public void contextMenuOpen(ActionEvent actionEvent) throws IOException {
        if (selectFileName != null || serverCurrentPath != null) {
            dos.writeObject(new OpenFile(serverCurrentPath.resolve(selectFileName)));
            dos.flush();
        }

    }
}
