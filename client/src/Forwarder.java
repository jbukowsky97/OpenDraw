import java.util.*;

/**
 * This class is responsible for forwarding all messages to other messages
 * 
 * @author Troy Madsen
 */
public class Forwarder extends Thread {
	
	/** Clients to forward messages to */
	private ArrayList<ClientHandler> clients;
	
	/** List of updaes to forward to clients */
	private List<String> updates;
	
	/**
	 * Creates a Forwarder object
	 */
	public Forwarder() {
		clients = new ArrayList<>();
		updates = Collections.synchronizedList(new LinkedList<String>());
	}
	
	/**
	 * Adds the client to the list of clients to forward commands to
	 * 
	 * @param client The new client to forward messages to
	 */
	public void subscribeClient(ClientHandler client) {
		clients.add(client);
	}
	
	/**
	 * Removes the client from the list of clients to forward commands to
	 * 
	 * @param client The client to remove from the forwarding list
	 */
	public void unsubscribeClient(ClientHandler client) {
		clients.remove(client);
	}
	
	/**
	 * Adds the provided update to the list of update to forward
	 * 
	 * @param update String of the update to forward to clients
	 */
	public synchronized void addUpdate(String update) {
		updates.add(update);
	}
	
	/**
	 * The operation of the Forwarder class. Forwards all updates to all subscribed clients.
	 */
	public void run() {
		while (true) {
			// Wait for the there to be updates to send
			while (updates.size() == 0);

			String update = updates.get(0) + "\n";
			updates.remove(0);

			for (ListIterator<ClientHandler> iter = clients.listIterator(); iter.hasNext();) {
				System.out.println("Sending: " + iter.next().getIP());
				try {
					System.out.println("SENDING:\t" + update);
					iter.next().getOutputStream().writeBytes(update);
				} catch (Exception e) {
					// Fail quietly
				}
			}
		}
	}

}