package com.geekbrains.nio;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;


// ls -> список файлов в текущей папке +
// cat file -> вывести на экран содержание файла +
// cd path -> перейти в папку
// touch file -> создать пустой файл
public class NioServer {

    private final Path path;
    private Path currentPath;
    private ServerSocketChannel server;
    private Selector selector;
    private ByteBuffer buffer;
    private SocketChannel channel;


    public NioServer() throws Exception {
        currentPath = null;
        buffer = ByteBuffer.allocate(256);
        path = Path.of("root");
        ;
        server = ServerSocketChannel.open(); // accept -> SocketChannel
        server.bind(new InetSocketAddress(8189));
        selector = Selector.open();
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);

        while (server.isOpen()) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    handleAccept(key);
                }
                if (key.isReadable()) {
                    handleRead(key);
                }
                iterator.remove();
            }
        }
    }

    private void handleRead(SelectionKey key) throws Exception {

        channel = (SocketChannel) key.channel();
        StringBuilder sb = new StringBuilder();

        while (true) {
            int read = channel.read(buffer);
            if (read == -1) {
                channel.close();
                return;
            }
            if (read == 0) {
                break;
            }
            buffer.flip();
            while (buffer.hasRemaining()) {
                sb.append((char) buffer.get());
            }
            buffer.clear();
        }

        if (sb.toString().startsWith("ls")) {
            viewPath();

        } else if (sb.toString().startsWith("cat")) {
            viewFile(sb.toString().trim().split(" ")[1]);

        } else if (sb.toString().split(" ")[0].equals("cd")) {
            changePath(sb.toString().trim().split(" ")[1]);

        } else if (sb.toString().startsWith("cd..")) {
            getParentPath();

        } else if (sb.toString().split(" ")[0].equals("touch")) {
            createNewFile(sb.toString().trim().split(" ")[1]);

        } else {
            String result = "[From server]: " + sb.toString();
            channel.write(ByteBuffer.wrap(result.getBytes(StandardCharsets.UTF_8)));
        }
    }

    private void createNewFile(String s) throws IOException {

        if (!Files.exists(currentPath.resolve(s))) {
            Files.createFile(Path.of(String.valueOf(currentPath)).resolve(s));
            channel.write(ByteBuffer.wrap(("File was create.\n\r").getBytes(StandardCharsets.UTF_8)));
        }
    }

    private void getParentPath() {
        System.out.println("getParentPath");
        if (currentPath != null) {
            this.currentPath = currentPath.getParent();
            System.out.println("currentPath " + currentPath);
        }
    }

    private void changePath(String s) {
        System.out.println("changePath");
        if (!s.equals("..") && Files.isDirectory(path.resolve(s))) {
            this.currentPath = Path.of(String.valueOf(path.resolve(s)));
            System.out.println("currentPath " + currentPath);
        }
        System.out.println("rootPath: " + path);
    }

    private void viewFile(String s) throws IOException {
        if (currentPath != null && (Path.of(String.valueOf(currentPath), s).toFile().isFile())) {
            String str = Files.readString(Path.of(String.valueOf(currentPath), s), StandardCharsets.UTF_8);
            channel.write(ByteBuffer.wrap((str + "\n\r").getBytes(StandardCharsets.UTF_8)));
        } else {
            String wrongStr = "Wrong file path\n\r";
            channel.write(ByteBuffer.wrap((wrongStr).getBytes(StandardCharsets.UTF_8)));
        }
    }

    private void viewPath() throws IOException {
        if (currentPath != null) {
            if (Files.isDirectory(currentPath)) {
                File file = new File(String.valueOf(currentPath));
                String[] files = file.list();

                //Первыми на экран выввожу папки
                for (String str : files) {
                    if (Files.isDirectory((currentPath).resolve(str))) {
                        channel.write(ByteBuffer.wrap((str + "\n\r").getBytes(StandardCharsets.UTF_8)));
                    }
                }

                //Затем файлы
                for (String str : files) {
                    if (!Files.isDirectory((currentPath).resolve(str))) {
                        channel.write(ByteBuffer.wrap((str + "\n\r").getBytes(StandardCharsets.UTF_8)));
                    }
                }
            }
        } else  {
            File file = new File(String.valueOf(path));
            String[] files = file.list();

            //Первыми на экран выввожу папки
            for (String str : files) {
                if (Files.isDirectory(Path.of(String.valueOf(path), str))) {
                    channel.write(ByteBuffer.wrap((str + "\n\r").getBytes(StandardCharsets.UTF_8)));
                }
            }

            //Затем файлы
            for (String str : files) {
                if (!Files.isDirectory(Path.of(String.valueOf(path), str))) {
                    channel.write(ByteBuffer.wrap((str + "\n\r").getBytes(StandardCharsets.UTF_8)));
                }
            }
        }
    }

    private void handleAccept(SelectionKey key) throws Exception {
        SocketChannel channel = server.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ, "!");
    }


    public static void main(String[] args) throws Exception {
        new NioServer();
    }


}
