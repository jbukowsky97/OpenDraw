import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
	
	/** Control socket for communicating with the client */
	private Socket controlSocket;

	/** Control output stream for sending responses to the client */
	private DataOutputStream outToClient;

	/** Control input stream for receiving commands from the client */
	private BufferedReader inFromClient;
	
	/** Thread responsible for forwarding messages to all clients */
	private Forwarder forwarder;
	
	/**
	 * Provides the clients IP address
	 * 
	 * @return The IP address of the client
	 */
	public String getIP() {
		return controlSocket.getInetAddress().toString();
	}
	
	/**
	 * Provides the OutputStream of to this client
	 * 
	 * @return The DataOutputStream of this object
	 */
	public DataOutputStream getOutputStream() {
		return outToClient;
	}
	
	/** 
	* Closes the connection to the client
	*/
	private void quit() {
		// Clean up resources
		try {
			if (controlSocket != null) controlSocket.close();
		} catch (Exception e) {
			// Fail quietly
		}

		controlSocket = null;
	}
	
	/**
	* A helper method to determine if the server is connected to a client
	*
	* @return Whether the server is connected to a client
	*/
	private boolean isConnected() {
		return controlSocket != null && controlSocket.isConnected() && !controlSocket.isClosed();
	}
	
	/**
	* Operation of this thread
	*/
	public void run() {
		String address = controlSocket.getInetAddress().toString();
		System.out.println(address + " connected");

		while (isConnected()) {
			try {
				// Wait for a command from the client
				while (!inFromClient.ready());

				// Command
				String command = inFromClient.readLine();
				String[] params = command.split(" ");

				// params[0] -> command
				if (params[0].toLowerCase().equals("line")) {
					// params[1] -> x1
					// params[2] -> y1
					// params[3] -> x2
					// params[4] -> y2
					// params[5] -> Red
					// params[6] -> Green
					// params[7] -> Blue
//					int x1 = Integer.parseInt(params[1]);
//					int y1 = Integer.parseInt(params[2]);
//					int x2 = Integer.parseInt(params[3]);
//					int y2 = Integer.parseInt(params[4]);
//					int red = Integer.parseInt(params[5]);
//					int green = Integer.parseInt(params[6]);
//					int blue = Integer.parseInt(params[7]);
					
					forwarder.addUpdate(command);
				} else if (params[0].toLowerCase().equals("clear")) {
					forwarder.addUpdate(command);
				} else if (params[0].toLowerCase().equals("quit")) {
					quit();
				}
			} catch (Exception e) {
				// Fail quietly
			}
		}

		System.out.println(address + " disconnected\n");
	}
	
	/** 
	* Create a ThreadHost object with the provided client connection
	*/
	public ClientHandler(Socket controlSocket, Forwarder forwarder) {
		this.controlSocket = controlSocket;
		this.forwarder = forwarder;

		try {
			outToClient = new DataOutputStream(controlSocket.getOutputStream());
			inFromClient = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
		} catch (Exception e) {
			// Close the connection if communications cannot be established
			quit();
		}
	}
	
}