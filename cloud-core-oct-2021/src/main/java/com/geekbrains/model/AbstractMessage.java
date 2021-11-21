package com.geekbrains.model;

import java.io.Serializable;

public class AbstractMessage implements Serializable {

    private Operation type;

    protected void setType(Operation type) {
        this.type = type;
    }

    public Operation getType() {
        return type;
    }

}
