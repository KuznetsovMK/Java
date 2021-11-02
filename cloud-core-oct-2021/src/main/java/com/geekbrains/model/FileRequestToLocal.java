package com.geekbrains.model;

public class FileRequestToLocal extends AbstractMessage {

    private final String name;

    public FileRequestToLocal(String name) {
        this.name = name;
        setType(Operation.FILE_REQUEST_TO_LOCAL);
    }

    public String getName() {
        return name;
    }
}
