package com.geekbrains.model;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocalListMessage extends AbstractMessage {
    private List<String> files;
    private String pathName;

    public LocalListMessage(Path dir) throws Exception {
        setType(Operation.LOCAL_LIST_MESSAGE);

        files = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            for (File file : dir.toFile().listFiles()) {
                if (file.isDirectory() && i == 0) {
                    files.add(file.getName());
                }
                if (!file.isDirectory() && i == 1) {
                    files.add(file.getName());
                }
            }
        }

        pathName = String.valueOf(dir);
    }

    public List<String> getFiles() {
        return files;
    }

    public String getPathName() {
        return pathName;
    }
}
