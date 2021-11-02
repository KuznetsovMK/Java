package com.geekbrains.model;


public class FileMessageToLocal extends AbstractMessage {

    private static final int BUTCH_SIZE = 8192;

    private final String name;
    private final long size;
    private final byte[] bytes;
    private final boolean isFirstButch;
    private final int endByteNum;
    private final boolean isFinishBatch;

    public FileMessageToLocal(String name,
                              long size,
                              byte[] bytes,
                              boolean isFirstButch,
                              int endByteNum,
                              boolean isFinishBatch) {
        this.name = name;
        this.size = size;
        this.bytes = bytes;
        this.isFirstButch = isFirstButch;
        this.endByteNum = endByteNum;
        this.isFinishBatch = isFinishBatch;
        setType(Operation.FILE_MESSAGE_TO_LOCAL);
    }

    public String getName() {
        return name;
    }

    public boolean isFirstButch() {
        return isFirstButch;
    }

    public int getEndByteNum() {
        return endByteNum;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public boolean isFinishBatch() {
        return isFinishBatch;
    }
}
