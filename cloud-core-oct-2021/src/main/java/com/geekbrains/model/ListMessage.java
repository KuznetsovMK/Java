package com.geekbrains.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ListMessage extends AbstractMessage {

    private final List<String> files;

    public ListMessage(Path dir) throws Exception {
        setType(Operation.SERVER_LIST_MESSAGE);

        files = Files.list(dir)
                .map(x -> x.getFileName().toString())
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> getFiles() {
        return files;
    }
}
