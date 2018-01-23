package server;

import server.controller.ServerController;
import server.view.ServerConsole;
import server.view.ServerView;
import server.domain.mediator.ServerModel;
import server.domain.mediator.ServerModelManager;

/** Runs the server **/
public class ServerMain {
	public static void main(String[] args) {
		final int port = 4488;

		ServerModel model = new ServerModelManager(port);
		ServerView view = new ServerConsole();
		ServerController controller = new ServerController(model, view);

		view.start(controller);
	}
}
