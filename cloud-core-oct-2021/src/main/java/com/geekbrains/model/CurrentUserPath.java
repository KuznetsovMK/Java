package com.geekbrains.model;

public class CurrentUserPath extends AbstractMessage {

    private String name;

    public CurrentUserPath(String name) {
        this.name = name;
        setType(Operation.CHANGE_PATH);
    }

    public String getPath() {
        return name;
    }
}
