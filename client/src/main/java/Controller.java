import java.io.IOException;
import java.net.Socket;

public class Controller {
    private Socket socket;

    public void connect() {
        try {
            socket = new Socket("localhost", 8189);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
