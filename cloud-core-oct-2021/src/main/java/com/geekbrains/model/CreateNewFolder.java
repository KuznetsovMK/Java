package com.geekbrains.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateNewFolder extends AbstractMessage {

    public CreateNewFolder(String serverClientDir) throws IOException {

        Path serverClientDirPath = Paths.get(serverClientDir);

        int i = 0;
        Path dirPath = serverClientDirPath.resolve("New_folder");
        while (Files.isDirectory(dirPath)) {
            dirPath = serverClientDirPath.resolve("New_folder" + i);
            i++;
        }
        Files.createDirectory(dirPath);
        setType(Operation.CREATE_FOLDER);
    }
}
