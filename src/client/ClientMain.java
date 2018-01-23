package client;

import client.controller.ClientController;
import client.domain.mediator.ClientModel;
import client.domain.mediator.ClientModelManager;
import client.view.ClientGUI;
import client.view.ClientView;

/** Runs the client **/
public class ClientMain {
	public static void main(String[] args) {
		final int port = 4488;
		final String ip = "localhost";

		ClientModel model = new ClientModelManager(ip, port);
		ClientView view = new ClientGUI();
		ClientController controller = new ClientController(model, view);

		view.start(controller);

	}
}
