import java.io.*;
import java.net.Socket;

public class Client {

    private String ip;
    private int port;

    private Socket socket;
    private DataOutputStream out;
    private BufferedReader in;

    private boolean running;

    private ClientGUI clientGUI;

    public Client(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
        running = false;
    }

    public void connect(String ip, int port) throws IOException {
        running = true;
        socket = new Socket(ip, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    listen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    public void sendCommand(String command) throws IOException {
        if (running) {
            out.writeBytes(command + "\n");
            out.flush();
        }
    }

    private void listen() throws IOException {
        while (running) {
            String command = in.readLine();
            clientGUI.process(command);
        }
    }
}