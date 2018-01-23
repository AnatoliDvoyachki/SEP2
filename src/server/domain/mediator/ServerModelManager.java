package server.domain.mediator;

import server.persistence.Persistence;
import server.persistence.SQLDatabase;

import java.util.ArrayList;

import model.Order;
import model.OrderList;
import model.OrderSearchCriteria;
import model.Shoe;
import model.ShoeList;
import model.ShoeSearchCriteria;
import model.User;
import model.UserList;

/**
 * Class responsible for handling server functionality
 */
public class ServerModelManager implements ServerModel {
	private Persistence database = SQLDatabase.getInstance();
	private ArrayList<ServerCommunication> clients;

	public ServerModelManager(int port) {
		new Thread(new ServerConnection(this, port)).start();
		clients = new ArrayList<>();
	}

	@Override
	public synchronized void addShoe(Shoe shoe) {
		database.addShoe(shoe);
	}

	@Override
	public synchronized boolean addUser(User newUser) {
		if (database.userExists(newUser.getUsername())) {
			return false;
		} else {
			database.addUser(newUser);
			return true;
		}
	}

	@Override
	public synchronized int placeOrder(Order newOrder) {
		// if no billing name is entered, the user default is input
		if (newOrder.getBillingName().equals("")) {
			newOrder.setBillingName(database.getUserBillingName(newOrder
					.getUsername()));
		}
		// if no billing address is entered, the user default is input
		if (newOrder.getBillingAddress().equals("")) {
			newOrder.setBillingAddress(database.getUserBillingAddress(newOrder
					.getUsername()));
		}
		// checks if the stock is high enough and returns an orderId if it is
		// if not then returns -1
		int response = -1;
		boolean validProductId = database.shoeExists(newOrder.getProductId());
		if (validProductId) {
			int quantityInStock = database.getQuantity(newOrder.getProductId());
			if (quantityInStock >= newOrder.getQuantity()) {
				int orderId = database.addOrder(newOrder);
				database.decreaseQuantity(newOrder.getProductId(),
						newOrder.getQuantity());
				response = orderId;
			}
		}
		return response;
	}

	@Override
	public synchronized Shoe removeShoe(int productId) {
		return database.removeShoe(productId);
	}

	@Override
	public synchronized void editShoe(int attributeIndex, Shoe newShoe) {
		database.editShoe(attributeIndex, newShoe);
	}

	@Override
	public synchronized ShoeList getShoeList(ShoeSearchCriteria criteria) {
		return database.getShoeList(criteria);
	}

	@Override
	public synchronized OrderList getOrderList(OrderSearchCriteria criteria) {
		return database.getOrderList(criteria);
	}

	@Override
	public synchronized UserList getUserList() {
		return database.getUserList();
	}

	@Override
	public synchronized void changeDeliveryStatus(int orderId, String status) {
		database.changeDeliveryStatus(orderId, status);
	}

	@Override
	public synchronized boolean loginUser(String username, String userPassword) {
		return database.credentialsMatch(username, userPassword);

	}

	@Override
	public synchronized Shoe getShoe(int productId) {
		return database.getShoe(productId);
	}

	@Override
	public synchronized void closeConnection() {
		for (ServerCommunication client : clients) {
			client.close();
		}
		database.closeConnection();
	}

	public synchronized void addClient(ServerCommunication client) {
		clients.add(client);
	}

}
