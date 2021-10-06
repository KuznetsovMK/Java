import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private int port;
    private List<ClientHandler> clients;
    private static final ExecutorService service = Executors.newCachedThreadPool();

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Port: " + port);
            while (true) {
                System.out.println("Waiting for a new client...");
                Socket socket = serverSocket.accept();
                System.out.println("Client was connected");
                service.execute(new ClientHandler(socket, this));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
