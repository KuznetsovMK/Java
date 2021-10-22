package com.geekbrains.netty;

import com.geekbrains.model.AbstractMessage;
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

        //0 - path, 1 - cmd, 2 - option
        Path path = Paths.get(msg.getMessage().trim().split(" ")[0]);
        String cmd = msg.getMessage().trim().split(" ")[1];
        UserNavigator userNavigator = new UserNavigator(path);

        if (cmd.equals("ls")) {
            msg = msgBuilder(msg, userNavigator);

        }
        else if (path.resolve(cmd).toFile().isFile()) {
            Path filePath = path.resolve(cmd);
            File file = new File(String.valueOf(filePath));

            String fileName = file.getName();
            Long fileSize = Files.size(filePath);


            InputStream is = new FileInputStream(String.valueOf(filePath));
            OutputStream bos = new ByteArrayOutputStream();

            byte[] buffer = new byte[8129];
            int readBytes;
            while (true) {
                readBytes = is.read(buffer);
                if (readBytes == -1) {
                    break;
                }
                bos.write(buffer, 0, readBytes);
            }
//            String msgFromFile = bos.toString();
//            System.out.println(msgFromFile);

            StringBuilder stringBuilder = new StringBuilder()
                    .append(userNavigator.getCurrentPath())
                    .append("\n")
                    .append("File name: " + fileName)
                    .append("\n")
                    .append("Size: " + fileSize + " bytes")
                    .append("\n\n")
                    .append(bos.toString());

//            System.out.println(bos.toString());

            msg.setMessage(stringBuilder.toString());
        }

        else if (msg.getMessage().trim().split(" ").length > 2) {
            String option = msg.getMessage().trim().split(" ")[2];

            if (cmd.equals("cd")) {

                if (!option.equals("..") && Files.isDirectory(userNavigator.getCurrentPath().resolve(option))) {
                    userNavigator.setCurrentPath(Paths.get(option));
                    msg = msgBuilder(msg,userNavigator);
                }

                else if (option.equals("..") && (userNavigator.getCurrentPath() != userNavigator.getRoot())) {
                    userNavigator.getParentPath();
                    msg = msgBuilder(msg,userNavigator);
                }

                else {
                    msg = msgBuilder(msg, userNavigator);
                }
            }

            else  {
                msg = msgBuilder(msg, userNavigator);
            }
        }

        else {
            msg = msgBuilder(msg, userNavigator);
        }

//        log.debug("received: {}", msg);
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


