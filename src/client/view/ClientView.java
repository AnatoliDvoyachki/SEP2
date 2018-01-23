package client.view;

import client.controller.ClientController;
import model.OrderList;
import model.ShoeList;

public interface ClientView {
	/**
	 * Used to initiate the client view
	 * 
	 * @param controller
	 *            the controller that acts on the client view
	 **/
	void start(ClientController controller);

	/**
	 * Used to change the status label in the ClientGUI
	 * 
	 * @param value
	 *            the message to be shown
	 * @param messageType
	 *            the type of message to be shown e.g. 1 - error message (red
	 *            text color), 2 - success message(green text color), 3 -
	 *            information message (blue text color)
	 * **/
	void show(String value, int messageType);

	/**
	 * Initializes the JTable component in the ClientGUI with the data from the
	 * shoe list passed as a parameter
	 * 
	 * @param list
	 *            the shoe list to be shown
	 * **/
	void showShoeList(ShoeList list);

	/**
	 * Initializes the JTable component in the ClientGUI with the data from the
	 * order list passed as a parameter
	 * 
	 * @param list
	 *            the list to be shown
	 **/
	void showOrderList(OrderList list);

	/**
	 * Used to collect input from the user. Number of parameters not predefined,
	 * because this method is used for multiple user input forms
	 * 
	 * @param what
	 *            the required input from the user
	 * @return an array of the input from the user
	 **/
	String[] get(String... what);
}
