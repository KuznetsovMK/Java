package com.geekbrains.model;

public class DeleteFile extends AbstractMessage {

//    private Path path;
    private String name;

    public DeleteFile(String name) {
        this.name = name;
        setType(Operation.DELETE_FILE);
    }

    public String getName() {
        return name;
    }
}
