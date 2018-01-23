package client.domain.mediator;

import util.ProtocolParser;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import model.*;

/**
 * Class responsible sending client requests and recieving server responses
 */
public class ClientModelManager implements ClientModel {
	private Socket clientSocket;
	private ObjectOutputStream outToServer;
	private ObjectInputStream inFromServer;

	/** Constructor for the ClientModelManager **/
	public ClientModelManager(String ip, int port) {
		try {
			this.clientSocket = new Socket(ip, port);
			this.outToServer = new ObjectOutputStream(
					clientSocket.getOutputStream());
			this.inFromServer = new ObjectInputStream(
					clientSocket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ShoeList listShoes(ShoeSearchCriteria criteria) {
		// creates a server request based on the implementation in the protocol
		String request = ProtocolParser.shoeSearchMessage(criteria);
		String response;

		response = sendRequest(request.toString());

		// creates an order list from the protocol response message
		String responseHeader = response.split("; ")[0];

		switch (responseHeader) {
		case ProtocolParser.PRODUCTLIST_HEADER:
			// creates a shoe list from the protocol response message
			return ProtocolParser.shoeListFromMessage(response);
		default:
			// returns an empty list if the response does not contain the right
			// header
			return new ShoeList();
		}
	}

	@Override
	public OrderList listOrders(String username) {
		// creates a server request based on the implementation in the protocol
		String request = ProtocolParser.orderSearchMessage(username);
		String response;

		response = sendRequest(request.toString());

		String responseHeader = response.split("; ")[0];

		switch (responseHeader) {
		case ProtocolParser.ORDERLIST_HEADER:
			// creates an order list from the protocol response message
			return ProtocolParser.orderListFromMessage(response);
		default:
			// returns an empty list if the response does not contain the right
			// header
			return new OrderList();
		}

	}

	@Override
	public String putOrder(Order newOrder) {
		// creates a server request based on the implementation in the protocol
		String request = ProtocolParser.orderMessage(newOrder);
		String response;

		response = sendRequest(request.toString());

		// course of action decided by response
		String responseHeader = response.split("; ")[0];
		switch (responseHeader) {
		case ProtocolParser.ORDER_RESPONSE_OK:
			// the order id will be the message following the header
			return "Order confirmed - " + response.split("; ")[1];
		case ProtocolParser.ORDER_RESPONSE_NOT_OK:
			return "Order denied - invalid information";
		default:
			return "Unknown protocol response message";
		}
	}

	@Override
	public String login(String username, String password) {
		// creates a server request based on the implementation in the protocol
		String request = ProtocolParser.loginMessage(username, password);
		String response;

		response = sendRequest(request.toString());

		// course of action decided by response
		switch (response) {
		case ProtocolParser.LOGIN_RESPONSE_OK:
			return "Login successful";
		case ProtocolParser.LOGIN_RESPONSE_NOT_OK:
			return "Login failed - username and password mismatch";
		default:
			return "Unknown protocol response message";
		}
	}

	@Override
	public String registerUser(User newUser) {
		// creates a server request based on the implementation in the protocol
		String request = ProtocolParser.registerMessage(newUser);
		String response;

		response = sendRequest(request.toString());

		// course of action decided by response
		switch (response) {
		case ProtocolParser.REGISTER_RESPONSE_OK:
			return "Registration successful";
		case ProtocolParser.REGISTER_RESPONSE_NOT_OK:
			return "Registration failed - username already in use";
		default:
			return "Unknown protocol response message";
		}
	}

	private String sendRequest(String request) {
		String response = null;

		try {
			outToServer.writeObject(request.toString());
			response = (String) inFromServer.readObject();
		} catch (SocketException e) {
			System.out.println("Server closed");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
}
