package client.controller;

import client.domain.mediator.ClientModel;
import client.view.ClientView;
import model.*;

import java.util.InputMismatchException;
import java.util.concurrent.CancellationException;
import javax.swing.JOptionPane;

/** Controls the data flow into the ClientModelManager and updates the ClientGUI **/
public class ClientController {

	// preset execute orders
	public static final String EXECUTE_LIST_ALL = "List All";
	public static final String EXECUTE_LIST_SOME = "List Some";
	public static final String EXECUTE_ORDER = "Order Shoe";
	public static final String EXECUTE_LOGIN = "Login";
	public static final String EXECUTE_REGISTER = "Register";
	public static final String EXECUTE_LIST_ORDERS = "List Orders";
	public static final String EXECUTE_QUIT = "Quit";
	public static final int ERROR_MESSAGE = 1;
	public static final int SUCCESS_MESSAGE = 2;
	public static final int INFO_MESSAGE = 3;

	// model and view to be controlled
	private ClientModel model;
	private ClientView view;

	private boolean loggedIn = false;
	private String username = "";

	/**
	 * Used to instantiate the ClientController
	 ***/
	public ClientController(ClientModel model, ClientView view) {
		this.model = model;
		this.view = view;
	}

	/**
	 * Used to start a sequence, responsible for a specific functionality on the
	 * client side
	 * 
	 * @param what
	 *            the sequence to be started
	 **/
	public void execute(String what) {
		switch (what) {
		case EXECUTE_LIST_ALL:
			executeListAll();
			break;

		case EXECUTE_LIST_SOME:
			executeListSome();
			break;

		case EXECUTE_ORDER:
			executeOrder();
			break;

		case EXECUTE_LOGIN:
			executeLogin();
			break;

		case EXECUTE_REGISTER:
			executeRegister();
			break;

		case EXECUTE_LIST_ORDERS:
			executeListOrders();
			break;

		case EXECUTE_QUIT:
			executeQuit();
			break;
		}
	}

	private void executeListAll() {
		// empty search criteria yield a full listing
		ShoeList shoeList = model.listShoes(new ShoeSearchCriteria());
		view.showShoeList(shoeList);
		view.show(shoeList.size() + " items found",
				ClientController.INFO_MESSAGE);
	}

	private void executeListSome() {
		try {
			// fills a search criteria object with key value pairs of different
			// search criteria
			ShoeSearchCriteria searchCriteria = new ShoeSearchCriteria();

			String[] shoeSearchInputs = view.get("Brand", "Model", "Color",
					"Size");

			searchCriteria.putCriteria(ShoeSearchCriteria.FILTER_BRAND,
					shoeSearchInputs[0]);
			searchCriteria.putCriteria(ShoeSearchCriteria.FILTER_MODEL,
					shoeSearchInputs[1]);
			searchCriteria.putCriteria(ShoeSearchCriteria.FILTER_COLOR,
					shoeSearchInputs[2]);
			searchCriteria.putCriteria(ShoeSearchCriteria.FILTER_SIZE,
					shoeSearchInputs[3]);

			ShoeList shoeList = model.listShoes(searchCriteria);
			view.showShoeList(shoeList);
			view.show(shoeList.size() + " items found",
					ClientController.INFO_MESSAGE);
		} catch (Exception e) {
			// nothing happens upon cancelling
		}
	}

	private void executeOrder() {
		if (loggedIn) {
			try {
				// collects user input
				String[] orderInputs = view.get("Product id", "Quantity",
						"Billing name", "Billing address");
				int orderProductIdInput = Integer.parseInt(orderInputs[0]);
				int orderQuantityInput = Integer.parseInt(orderInputs[1]);
				String orderBillingNameInput = orderInputs[2];
				String orderBillingAddressInput = orderInputs[3];

				// attempts to create an order
				Order newOrder = new Order(orderProductIdInput, username,
						orderQuantityInput);
				newOrder.setBillingName(orderBillingNameInput);
				newOrder.setBillingAddress(orderBillingAddressInput);

				int confirmStatus = JOptionPane.showConfirmDialog(null,
						"Confirm purchase?");

				if (confirmStatus != JOptionPane.YES_OPTION)
					throw new CancellationException(
							"Process aborted by the user");

				// puts the order in the model to send
				String orderResponse = model.putOrder(newOrder);
				if (orderResponse.startsWith("Order confirmed"))
					view.show(orderResponse, ClientController.SUCCESS_MESSAGE);
				else
					view.show(orderResponse, ClientController.ERROR_MESSAGE);

			} catch (CancellationException ce) {
				view.show("Purchase cancelled", ClientController.INFO_MESSAGE);
			} catch (InputMismatchException e) {
				// nothing happens upon cancelling
			}
		} else {
			view.show("You need to be logged in to buy an item",
					ClientController.ERROR_MESSAGE);
		}
	}

	private void executeLogin() {
		try {
			// collects user input
			String[] loginInputs = view.get("Username", "Password");
			String loginUsernameInput = loginInputs[0];
			String loginPasswordInput = loginInputs[1];

			// attempts to login
			String loginResponse = model.login(loginUsernameInput,
					loginPasswordInput);

			if (loginResponse.startsWith("Login successful")) {
				username = loginUsernameInput;
				loggedIn = true;
				view.show(loginResponse, ClientController.SUCCESS_MESSAGE);
			} else if (loginResponse.startsWith("Login failed"))
				view.show(loginResponse, ClientController.ERROR_MESSAGE);
		} catch (Exception e) {
			// nothing happens upon cancelling

		}
	}

	private void executeRegister() {
		try {
			// collects user input
			String[] registerInputs = view.get("Username", "Password",
					"Full name", "Address", "E-mail");
			String registerUsernameInput = registerInputs[0];
			String registerPasswordInput = registerInputs[1];
			String registerFullNameInput = registerInputs[2];
			String registerAddressInput = registerInputs[3];
			String registerEmailInput = registerInputs[4];
			// attempts to create a user
			User newUser = new User(registerUsernameInput,
					registerPasswordInput, registerFullNameInput,
					registerAddressInput, registerEmailInput);

			// passes the registration to the model
			String registerResponse = model.registerUser(newUser);

			if (registerResponse.startsWith("Registration successful")) {
				view.show("Registration successful",
						ClientController.SUCCESS_MESSAGE);
				loggedIn = true;
			} else
				view.show("Registration unsuccessful",
						ClientController.ERROR_MESSAGE);

			username = registerUsernameInput;

		} catch (Exception e) {
			// nothing happens upon cancelling
		}
	}

	private void executeListOrders() {
		if (loggedIn) {
			OrderList orderList = model.listOrders(username);
			view.showOrderList(orderList);
			view.show(orderList.size() + " items found",
					ClientController.INFO_MESSAGE);
		} else {
			view.show("You need to be logged in to show your orders",
					ClientController.ERROR_MESSAGE);
		}
	}

	private void executeQuit() {
		System.exit(0);
	}
}
