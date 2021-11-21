package com.geekbrains.model;

public class SystemMessage extends AbstractMessage {


    private String message;

    public SystemMessage(String message) {
        this.message = message;
        setType(Operation.SYSTEM_MESSAGE);
    }

    public String getMessage() {
        return message;
    }
}