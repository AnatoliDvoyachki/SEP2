package server.domain.mediator;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Thread class listening for incoming hosts and assigning them a socket.
 */
public class ServerConnection implements Runnable {
	private ServerModel model;
	private ServerSocket welcomeSocket;

	/** Used to instantiate a ServeConnection **/
	public ServerConnection(ServerModel model, int port) {
		this.model = model;
		try {
			welcomeSocket = new ServerSocket(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket clientSocket = welcomeSocket.accept();
				ServerCommunication newClient = new ServerCommunication(
						clientSocket, model);
				new Thread(newClient).start();
				model.addClient(newClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
