package com.geekbrains.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class LocalListMessage extends AbstractMessage {
    private final List<String> files;

    public LocalListMessage(Path dir) throws Exception {
        setType(Operation.LOCAL_LIST_MESSAGE);
        files = Files.list(dir).map(p -> p.getFileName().toString())
                .collect(Collectors.toList());
    }

    public List<String> getFiles() {
        return files;
    }
}
