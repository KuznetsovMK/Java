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

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastMessage("Пользователь " + clientHandler.getUsername() + " Подключился. \n");
        broadcastClientList();
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(message);
        }
    }

    public void broadcastClientList() {
        StringBuilder stringBuilder = new StringBuilder("/clients_list ");
        for (ClientHandler clientHandler : clients) {
            stringBuilder.append(clientHandler.getUsername()).append(" ");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        String clientsList = stringBuilder.toString();
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(clientsList);
        }
    }
}
