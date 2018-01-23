package client.domain.mediator;

import model.*;

public interface ClientModel {

	/**
	 * Used by the customer to create a new order. This method sends a protocol
	 * message, and recieves a protocol response.
	 * 
	 * @param newOrder
	 *            the newly created order
	 * @return the response message to the order creation attempt e.g Order
	 *         confirmed, Order denied etc.
	 **/
	String putOrder(Order newOrder);

	/**
	 * Returns a list of shoes, containing shoes that match the criteria passed
	 * as a parameter. This method sends a protocol request and recieves a
	 * protocol response.
	 * 
	 * @param criteria
	 *            the shoe criteria
	 * @return the shoes that match the criteria
	 * 
	 * **/
	ShoeList listShoes(ShoeSearchCriteria criteria);

	/**
	 * Returns a list of orders, that have been created by the user with the
	 * username passed as a parameter. This method sends a protocol request and
	 * recieves a protocol response.
	 * 
	 * @param username
	 *            the username of the user
	 * @returns the order list with the orders matching the criteria
	 **/
	OrderList listOrders(String username);

	/**
	 * Used to register a new user in the system. This method sends a protocol
	 * message, and recieves a protocol response.
	 * 
	 * @param newUser
	 *            the new user to be registered
	 * @return a confirmation or denial message e.g. Registration successful,
	 *         Registration failed
	 **/
	String registerUser(User newUser);

	/**
	 * Used for the login functionality. This method sends a protocol message,
	 * and recieves a protocol response.
	 * 
	 * @param username
	 *            the user's username
	 * @param password
	 *            the user's password
	 * @return the response to the login attempt e.g. Login successful, Login
	 *         failed - username and password mismatch etc.
	 **/
	String login(String username, String password);
}
