package com.geekbrains.model;

public class RenameFile extends AbstractMessage {

    private String oldName;
    private String newName;

    public RenameFile(String oldName, String newName) {
        this.oldName = oldName;
        this.newName = newName;
        setType(Operation.RENAME_FILE);
    }

    public String getOldName() {
        return oldName;
    }

    public String getNewName() {
        return newName;
    }
}
