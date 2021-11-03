package com.geekbrains.netty;

import com.geekbrains.model.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<AbstractMessage> {

    private Path serverClientDir;
    private byte[] buffer;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        buffer = new byte[8192];
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractMessage msg) throws Exception {
        log.debug("Read: {}", msg);

        switch (msg.getType()) {

            case FILE_REQUEST_TO_LOCAL:

                sendFileToLocal((FileRequestToLocal) msg, ctx);
                break;

            case FILE_MESSAGE_TO_SERVER:

                FileMessageToServer fileMessageToServer = (FileMessageToServer) msg;
                Path file = serverClientDir.resolve(fileMessageToServer.getName());

                if (fileMessageToServer.isFirstButch()) {
                    Files.deleteIfExists(file);
                }

                try (FileOutputStream os = new FileOutputStream(file.toFile(), true)) {
                    os.write(fileMessageToServer.getBytes(), 0, fileMessageToServer.getEndByteNum());
                }

                if (fileMessageToServer.isFinishBatch()) {
                    ctx.writeAndFlush(new ListMessage(serverClientDir));
                    ctx.writeAndFlush(new SystemMessage("Upload successfully!"));
                }
                break;

            case DELETE_FILE:

                //Не удаляет папку, если в текущей сессии был переход в нее.

                DeleteFile deleteFile = (DeleteFile) msg;
                Path executionFilePath = serverClientDir.resolve(deleteFile.getName());

                if (executionFilePath.toFile().exists()) {
                    try {

                        if (executionFilePath.toFile().isDirectory()) {
                            serverClientDir = clearFolder(executionFilePath);
                        }
                        Files.delete(executionFilePath);

                    } catch (Exception e) {
                        e.printStackTrace();
                        ctx.writeAndFlush(new SystemMessage("Dir " + deleteFile.getName() + " будет удалена после завершения работы сервера!"));
                    }
                }

                ctx.writeAndFlush(new ListMessage(serverClientDir));
                break;

            case RENAME_FILE:

                RenameFile renameFile = (RenameFile) msg;
                Path newFilePath = serverClientDir.resolve(renameFile.getNewName());
                Path oldFilePath = serverClientDir.resolve(renameFile.getOldName());

                File newFileName = new File(String.valueOf(newFilePath));
                File oldFileName = new File(String.valueOf(oldFilePath));

                if (oldFileName.renameTo(newFileName)) {
                } else {
                    ctx.writeAndFlush(new SystemMessage("Filename " + renameFile.getNewName() + " already exist"));
                }
                ctx.writeAndFlush(new ListMessage(serverClientDir));
                break;

            case CHANGE_PATH:

                CurrentUserPath currentUserPath = (CurrentUserPath) msg;
                serverClientDir = Paths.get(currentUserPath.getPath());
                ctx.writeAndFlush(new ListMessage(serverClientDir));
                break;

            case CREATE_FOLDER:

                ctx.writeAndFlush(new ListMessage(serverClientDir));
                break;

            case REGISTRATION:

                UserRegistry userRegistry = (UserRegistry) msg;

                if (!DataBaseQuery.isRegistered(userRegistry.getLogin())) {

                    DataBaseQuery.addNewUser(userRegistry.getLogin(), userRegistry.getPassword());
                    serverClientDir = Paths.get(userRegistry.getLogin());
                    Files.createDirectory(serverClientDir);
                    ctx.writeAndFlush(new ListMessage(serverClientDir));
                    ctx.writeAndFlush(new CurrentUserPath(serverClientDir.toString()));
                    ctx.writeAndFlush(new LoginOK());
                    ctx.writeAndFlush(new SystemMessage("Registration successfully!"));
                } else {
                    ctx.writeAndFlush(new SystemMessage("Login already is use!"));
                }
                break;

            case LOGIN:

                UserLogin userLogin = (UserLogin) msg;

                if (DataBaseQuery.checkLogin(userLogin.getLogin(), userLogin.getPassword())) {

                    serverClientDir = Paths.get(userLogin.getLogin());

                    if (!Files.isDirectory(serverClientDir)) {
                        Files.createDirectory(serverClientDir);
                    }
                    ctx.writeAndFlush(new ListMessage(serverClientDir));
                    ctx.writeAndFlush(new CurrentUserPath(serverClientDir.toString()));
                    ctx.writeAndFlush(new LoginOK());
                    ctx.writeAndFlush(new SystemMessage("Login OK!"));
                } else {
                    ctx.writeAndFlush(new SystemMessage("Login fail!"));
                }
                break;
        }
        ctx.writeAndFlush(msg);
    }

    private void sendFileToLocal(FileRequestToLocal msg, ChannelHandlerContext ctx) throws Exception {

        boolean isFirstButch = true;
        Path filePath = serverClientDir.resolve(msg.getName());
        long size = Files.size(filePath);

        try (FileInputStream is = new FileInputStream(serverClientDir.resolve(msg.getName()).toFile())) {

            int read;
            while ((read = is.read(buffer)) != -1) {

                FileMessageToLocal message = new FileMessageToLocal(filePath.getFileName().toString(),
                        size, buffer, isFirstButch, read, is.available() <= 0);
                ctx.writeAndFlush(message);
                isFirstButch = false;
            }
        } catch (Exception e) {
//            log.error("e:", e);
        }
    }

    private Path clearFolder(Path dir) throws IOException {

        File[] files = dir.toFile().listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                serverClientDir = clearFolder(Paths.get(String.valueOf(file)));
            }
            Files.delete(Paths.get(String.valueOf(file)));
        }
        return dir.getParent();
    }
}


