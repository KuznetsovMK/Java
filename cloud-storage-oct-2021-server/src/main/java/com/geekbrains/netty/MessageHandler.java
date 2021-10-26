package com.geekbrains.netty;

import com.geekbrains.model.AbstractMessage;
import com.geekbrains.model.Operation;
import com.geekbrains.model.UserNavigator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<AbstractMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractMessage msg) throws Exception {

        //0 - path, 1 - cmd, 2 - operation, 3 - filename
        Path path = Paths.get(msg.getMessage().trim().split(" ")[0]);
        String cmd = msg.getMessage().trim().split(" ")[1];
        Operation operation = Operation.valueOf(msg.getMessage().trim().split(" ")[2]);
        String fileName = msg.getMessage().trim().split(" ")[3];

        UserNavigator userNavigator = new UserNavigator(path);
        StringBuilder stringBuilder = new StringBuilder();

        switch (operation) {
            case LOCAL_FILE_INFO:
                Path localFilePath = Paths.get("localroot").resolve(fileName);
                Long fileSize = Files.size(localFilePath);

                stringBuilder = new StringBuilder()
                        .append("localFileInfo")
                        .append("\n")
                        .append("File name: " + fileName + " Size: " + fileSize + " bytes");

                msg.setMessage(stringBuilder.toString());
                break;

            case SERVER_FILE_INFO:
                Path srvFilePath = path.resolve(fileName);

                if (srvFilePath.toFile().exists()) {
                    fileSize = Files.size(srvFilePath);

                    stringBuilder
                            .append("serverFileInfo")
                            .append("\n")
                            .append("File name: " + fileName + " Size: " + fileSize + " bytes");
                }
                msg.setMessage(stringBuilder.toString());
                break;

            case DELETE:
                srvFilePath = path.resolve(fileName);

                if (srvFilePath.toFile().exists()) {
                    Files.delete(srvFilePath);
                }
                msg = msgBuilder(msg, userNavigator);
                break;

            case TO_LOCAL:
                srvFilePath = path.resolve(fileName);
                localFilePath = Paths.get("localroot").resolve(fileName);

                InputStream is = new FileInputStream(String.valueOf(srvFilePath));
                OutputStream os = new FileOutputStream(String.valueOf(localFilePath));

                byte[] buffer = new byte[8129];
                int readBytes;
                while (true) {
                    readBytes = is.read(buffer);
                    if (readBytes == -1) {
                        break;
                    }
                    os.write(buffer, 0, readBytes);
                }
                is.close();
                os.close();

                msg = msgBuilder(msg, userNavigator);
                break;

            case TO_SERVER:
                 srvFilePath = path.resolve(fileName);
                 localFilePath = Paths.get("localroot").resolve(fileName);

                if (!srvFilePath.toFile().exists()) {
                    Files.createFile(srvFilePath);
                }

                 is = new FileInputStream(String.valueOf(localFilePath));
                 os = new FileOutputStream(String.valueOf(srvFilePath));

                buffer = new byte[8129];
                while (true) {
                    readBytes = is.read(buffer);
                    if (readBytes == -1) {
                        break;
                    }
                    os.write(buffer, 0, readBytes);
                }

                msg = msgBuilder(msg, userNavigator);
                is.close();
                os.close();
                break;
        }

        ctx.writeAndFlush(msg);
    }

    public AbstractMessage msgBuilder(AbstractMessage msg, UserNavigator userNavigator) {
        File file = new File(String.valueOf(userNavigator.getCurrentPath()));
        String[] fileList = file.list();

        StringBuilder stringBuilder = new StringBuilder()
                .append(userNavigator.getCurrentPath())
                .append("\n").append("..").append("\n");

        if (fileList != null) {
            for (String s : fileList) {
                stringBuilder.append(s).append("\n");
            }
        }

        msg.setMessage(stringBuilder.toString().trim());
        return msg;
    }


}


