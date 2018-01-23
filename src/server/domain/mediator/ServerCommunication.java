package server.domain.mediator;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.*;
import util.ProtocolParser;

/**
 * Thread class responsible for communicating with a client.
 */
public class ServerCommunication implements Runnable {
	private boolean connected;
	private ServerModel model;
	private Socket clientSocket;
	private ObjectOutputStream outToClient;
	private ObjectInputStream inFromClient;

	/** Used to instantiate a ServerCommunication **/
	public ServerCommunication(Socket clientSocket, ServerModel model) {
		connected = true;
		this.model = model;
		this.clientSocket = clientSocket;
		try {
			outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
			inFromClient = new ObjectInputStream(clientSocket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (connected) {
			try {
				String request = (String) inFromClient.readObject();

				if (request.startsWith(ProtocolParser.PRODUCTLIST_HEADER)) {
					handleProductListRequest(request);

				} else if (request.startsWith(ProtocolParser.ORDERLIST_HEADER)) {
					handleOrderListRequest(request);

				} else if (request.startsWith(ProtocolParser.ORDER_HEADER)) {
					handleNewOrderRequest(request);

				} else if (request.startsWith(ProtocolParser.LOGIN_HEADER)) {
					handleLoginRequest(request);

				} else if (request.startsWith(ProtocolParser.REGISTER_HEADER)) {
					handleRegisterRequest(request);

				} else {
					// does nothing on incorrect headers

				}
			} catch (Exception e) {
				break;
			}
		}
	}

	private void handleProductListRequest(String request) {
		// creates a search criteria map based on the request
		ShoeSearchCriteria searchCriteria = ProtocolParser
				.shoeSearchCriteriaFromMessage(request);

		// gets a shoe list based on the criteria and translates it into a
		// protocol message
		ShoeList shoeList = model.getShoeList(searchCriteria);
		String response = ProtocolParser.shoeListMessage(shoeList);
		sendResponse(response);
	}

	private void handleOrderListRequest(String request) {
		// creates a search criteria map based on the request
		OrderSearchCriteria searchCriteria = ProtocolParser
				.orderSearchCriteriaFromMessage(request);

		// gets an order list based on the criteria and translates it into a
		// protocol message
		OrderList orderList = model.getOrderList(searchCriteria);
		String response = ProtocolParser.orderListMessage(orderList);
		sendResponse(response);
	}

	private void handleNewOrderRequest(String request) {
		// creates a new order based on the request
		Order newOrder = ProtocolParser.orderFromMessage(request);

		// if the order id is positive real number then it is a
		// valid order id and the order was successfully stored in the
		// persistence
		String response;
		int orderId = model.placeOrder(newOrder);
		if (orderId >= 0) {
			response = ProtocolParser.oderOkMessage(orderId);
		} else {
			response = ProtocolParser.ORDER_RESPONSE_NOT_OK;
		}
		sendResponse(response);
	}

	private void handleLoginRequest(String request) {
		// creates a pair of login credentials based on the request
		String[] loginCredentials = ProtocolParser
				.loginCredentialsFromMessage(request);

		// if the boolean returned is true then the user was successfully logged
		// in
		String response;
		boolean accepted = model.loginUser(loginCredentials[0],
				loginCredentials[1]);
		if (accepted) {
			response = ProtocolParser.LOGIN_RESPONSE_OK;
		} else {
			response = ProtocolParser.LOGIN_RESPONSE_NOT_OK;
		}
		sendResponse(response);
	}

	private void handleRegisterRequest(String request) {
		// creates a new user based on the request
		User newUser = ProtocolParser.userFromMessage(request);

		// if the boolean is true then the user was successfully registered
		String response;
		boolean accepted = model.addUser(newUser);
		if (accepted) {
			response = ProtocolParser.REGISTER_RESPONSE_OK;
		} else {
			response = ProtocolParser.REGISTER_RESPONSE_NOT_OK;
		}
		sendResponse(response);
	}

	private void sendResponse(String response) {
		try {
			outToClient.writeObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Invoke to close connection to client matching the thread.
	 */
	public void close() {
		try {
			clientSocket.close();
			connected = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
