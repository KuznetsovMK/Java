package com.geekbrains.model;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ListMessage extends AbstractMessage {

    private List<String> files;
    private String pathName;

    public ListMessage(Path dir) throws Exception {

        if (dir != null) {
            setType(Operation.SERVER_LIST_MESSAGE);

            files = Files.list(dir)
                    .map(x -> x.getFileName().toString())
                    .sorted()
                    .collect(Collectors.toList());

            pathName = String.valueOf(dir);
        } else {
            setType(Operation.EXIT_REQUEST);
        }

    }

    public List<String> getFiles() {
        return files;
    }

    public String getPathName() {
        return pathName;
    }
}
