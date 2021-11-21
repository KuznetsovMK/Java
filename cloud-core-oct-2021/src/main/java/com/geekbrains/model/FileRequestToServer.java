package com.geekbrains.model;

public class FileRequestToServer extends AbstractMessage {

    private final String name;

    public FileRequestToServer(String name) {
        this.name = name;
        setType(Operation.FILE_REQUEST_TO_SERVER);
    }

    public String getName() {
        return name;
    }
}