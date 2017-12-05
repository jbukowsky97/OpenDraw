import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server responsible for handling client messages
 * 
 * @author Troy Madsen
 */
public class Server extends Thread {
	
	/** The port of this server */
	private int port;
	
	/** Thread responsible for forwarding updates to clients */
	private Forwarder forwarder;
	
	/**
	 * Creates a Server object on the given port
	 * 
	 * @param port Port to open Server on
	 */
	public Server(int port) {
		this.port = port;
	}
	
	/**
	 * Server operation
	 */
	public void run() {
		// Start the Forwarder
		forwarder.start();
		
		// Socket to receive new connections to the server
		ServerSocket welcomeSocket = null;
		
		// New socket being opened
		Socket controlSocket = null;
		
		System.out.println("Awaiting connections\n");
		try {
			welcomeSocket = new ServerSocket(port);

			// Continually wait for new connections
			while (true) {
				// Accept new conncetion
				controlSocket = welcomeSocket.accept();

				// Create a new thread to handle each client
				ClientHandler handler = new ClientHandler(controlSocket, forwarder);

				// Start the thread
				handler.start();
			}
		} catch (Exception e) {
			System.out.println("Error occurred");
		} finally {
			// Clean up resources
			try {
				if (controlSocket != null) controlSocket.close();
			} catch (Exception e) {
				// Fail quietly
			}

			try {
				if (welcomeSocket != null) welcomeSocket.close();
			} catch (Exception e) {
				// Fail quietly
			}
		}
	}
	
	/**
	 * Entry point for the Server program
	 * 
	 * @param args Input parameters
	 */
	public void main(String[] args) {
		// Start a new Server
		Server server = new Server(Integer.parseInt(args[0]));
		server.start();
	}
	
}
