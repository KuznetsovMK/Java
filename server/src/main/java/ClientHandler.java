import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = in.readUTF();
//                sendMessage(msg);
                if (msg.startsWith("/")) {
                    executeCommand(msg);
                    continue;
                }
                server.broadcastMessage(username + ": " + msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String getUsername() {
        return username;
    }

    private void executeCommand(String cmd) {
        if (cmd.startsWith("/login ")) {
            String usernameFromLogin = cmd.split("\\s")[1];
            username = usernameFromLogin;
            sendMessage("/login_ok " + username);
            server.subscribe(this);
            return;
        }
    }

    public void sendMessage(String message) {
//        System.out.println(message);
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
