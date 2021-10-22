package com.geekbrains.model;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserNavigator implements Serializable {

    private final Path root = Paths.get("root");
    private Path currentPath = null;

    public UserNavigator(Path currentPath) {

        this.currentPath = currentPath;
    }

    public Path getRoot() {
        return root;
    }

    public Path getCurrentPath() {
        return currentPath != null ? currentPath : root;
    }

    public void getParentPath() {
        if (currentPath != null) {
            this.currentPath = currentPath.getParent();
        }
    }

    public void setCurrentPath(Path currentPath) {
        if (currentPath != null) {
            this.currentPath = this.currentPath.resolve(currentPath);
        } else {
            this.currentPath = root.resolve(currentPath);
        }
    }
}

