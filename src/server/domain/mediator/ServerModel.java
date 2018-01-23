package server.domain.mediator;

import model.Order;
import model.OrderList;
import model.OrderSearchCriteria;
import model.Shoe;
import model.ShoeList;
import model.ShoeSearchCriteria;
import model.User;
import model.UserList;

/** Defines what functionality can the ServerModelManager have **/
public interface ServerModel {

	/**
	 * Adds a new shoe to the SQLDatabase
	 * 
	 * @param shoe
	 *            the shoe to be added
	 **/
	void addShoe(Shoe shoe);

	/**
	 * Adds a new user to the SQLDatabase
	 * 
	 * @param user
	 *            the user to be added
	 * @return false, if a user with the same username already exists in the
	 *         datababase, otherwise true
	 **/
	boolean addUser(User user);

	/**
	 * Places a new order in the SQLDatabase
	 * 
	 * @param order
	 *            the order to be added
	 * @return 1 if the order is successfuly added. Otherwise, -1
	 **/
	int placeOrder(Order order);

	/**
	 * Removes and returns a shoe from the SQLDatabase
	 * 
	 * @param productId
	 *            the id of the shoe to be removed
	 * @return the removed shoe
	 **/
	Shoe removeShoe(int productId);

	/**
	 * Edits the attributes of an already existing shoe
	 * 
	 * @param attributeIndex
	 *            the index of the attribute that is going to be edited
	 * @param shoe
	 *            the edited shoe
	 **/
	void editShoe(int attributeIndex, Shoe shoe);

	/**
	 * Changes the delivery status of an existing order
	 * 
	 * @param orderId
	 *            the id of the order whose status will change
	 * @param status
	 *            the index of the new status
	 * 
	 * **/
	void changeDeliveryStatus(int orderId, String status);

	/**
	 * Queries the database, and returns a shoe list, containing shoes that
	 * match the criteria passed as a paramater
	 * 
	 * @param criteria
	 *            the criteria for database querying, e.g. SIZE, BRAND, QUANTITY
	 *            etc.
	 **/
	ShoeList getShoeList(ShoeSearchCriteria criteria);

	/**
	 * Queries the database, and returns an order list, containing order that
	 * match the criteria passed as a paramater
	 * 
	 * @param criteria
	 *            the criteria for database querying, e.g. STATUS, ORDERID etc.
	 **/
	OrderList getOrderList(OrderSearchCriteria criteria);

	/**
	 * Queries the database, and returns an order list containg all of the
	 * orders in the database
	 **/
	UserList getUserList();

	/**
	 * Used for the login functionality
	 * 
	 * @param username
	 *            the username
	 * @param userPassword
	 *            the password
	 * @return true, if the login is successful. Otherwise, false
	 **/
	boolean loginUser(String username, String userPassword);

	/**
	 * Returns a shoe with the product id passed as a parameter
	 * 
	 * @param productId
	 *            the id of the requested product
	 * @return the shoe. If the shoe does not exist, an exception is thrown
	 * **/
	Shoe getShoe(int productId);

	/**
	 * Used to close the connection between all the clients and the
	 * ServerModelManager. Also, closes the connection between the
	 * ServerModelManager and the SQLDatabase.
	 **/
	void closeConnection();

	/**
	 * Used when a client establishes a connection with the server.
	 * 
	 * @param client
	 *            the new client
	 * **/
	void addClient(ServerCommunication client);

}
