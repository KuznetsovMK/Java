package com.geekbrains.model;

import java.nio.file.Path;

public class OpenFile extends AbstractMessage{

    private String path;

    public OpenFile(Path file) {
        setType(Operation.OPEN_FILE);
        this.path = file.toString();
    }

    public String getPath() {
        return path;
    }
}
