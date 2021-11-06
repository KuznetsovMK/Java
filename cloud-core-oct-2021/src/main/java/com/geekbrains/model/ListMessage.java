package com.geekbrains.model;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ListMessage extends AbstractMessage {

    private List<String> files;
    private String pathName;

    public ListMessage(Path dir) throws Exception {

        files = new ArrayList<>();

        if (dir != null) {
            setType(Operation.SERVER_LIST_MESSAGE);


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
        } else {
            setType(Operation.EXIT_REQUEST);
        }

    }

    public List<String> getFiles() {
        return files;
    }

    public String getPathName() {
        return pathName;
    }
}
