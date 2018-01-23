package server.persistence;

import model.Order;
import model.OrderList;
import model.OrderSearchCriteria;
import model.Shoe;
import model.ShoeList;
import model.ShoeSearchCriteria;
import model.User;
import model.UserList;

/**
 * This interface defines the operations that will be implemented in the
 * SQLDatabase class
 **/
public interface Persistence {

	/**
	 * This method returns a list of all shoes in the database with the
	 * specified constraints
	 *
	 * @param criteria The criteria by which the order list will be formed
	 * @return All shoes in the database with the specified criteria
	 **/
	ShoeList getShoeList(ShoeSearchCriteria criteria);

	/**
	 * This method returns all users in the database
	 *
	 * @return All users in the database
	 **/
	UserList getUserList();

	/**
	 * This method returns a list of all orders in the database with the
	 * specified constraints
	 *
	 * @param criteria The criteria by which the order list will be formed
	 * @return All orders in the database with the specified criteria
	 **/
	OrderList getOrderList(OrderSearchCriteria criteria);

	/**
	 * This method adds a shoe object to the database
	 *
	 * @param shoe The shoe that is going to be added
	 **/
	void addShoe(Shoe shoe);

	/**
	 * This method removes a shoe from the databse and then returns it. The shoe
	 * is identified by the product id.
	 *
	 * @param productId The id of the shoe that is going to be removed
	 * @return The shoe that was removed
	 **/
	Shoe removeShoe(int productId);

	/**
	 * This method increases the quantity of an existing product in the
	 * database.
	 *
	 * @param productId The id of the product that we want to increase the quantity of
	 * @param quantity  The amount by which we want to increase the quantity
	 **/
	void increaseQuantity(int productId, int quantity);

	/**
	 * This method decreases the quantity of an existing product in the databse
	 *
	 * @param productId The id of the product that we want to decrease the quantity of
	 * @param quantity  The amount by which we want to reduce the quantity
	 **/
	void decreaseQuantity(int productId, int quantity);

	/**
	 * This method returns the quantity of a product with the specified id
	 *
	 * @param productId The id of the product whose quantity is going to be returned
	 * @return The quantity
	 **/
	int getQuantity(int productId);

	/**
	 * This method checks if the username and the user password match a single
	 * account. This method is used for the login functionality
	 *
	 * @param username     The username
	 * @param userPassword
	 * @return True, if the credentials match. Otherwise, false.
	 **/
	boolean credentialsMatch(String username, String userPassword);

	/**
	 * This method checks if an user with the specified username exists in the
	 * database
	 *
	 * @param username The user's username
	 * @return true, if the user exists. Otherwise, false.
	 **/
	boolean userExists(String username);

	/**
	 * This method checks if an order with the specified order id exists in the
	 * database
	 *
	 * @param orderId The order's id
	 * @return true, if the order exists. Otherwise, false.
	 **/
	boolean orderExists(int orderId);

	/**
	 * This method checks if a shoe with the specified username exists in the
	 * database
	 *
	 * @param productId The shoe's product id
	 * @return true, if the shoe exists. Otherwise, false.
	 **/
	boolean shoeExists(int productId);

	/**
	 * This method adds a new user to the database
	 *
	 * @param user The user that is going to be added
	 **/
	void addUser(User user);

	/**
	 * This method returns the real name of the account with the specified
	 * username
	 *
	 * @param username The username of the account
	 * @return The full name of the user
	 **/
	String getUserBillingName(String username);

	/**
	 * This method returns the address of the account with the specified
	 * username
	 *
	 * @param username The username of the account
	 * @return The address of the user
	 **/
	String getUserBillingAddress(String username);

	/**
	 * This method adds an order to the database
	 *
	 * @param order The order that is going to be added to the database
	 **/
	int addOrder(Order order);

	/**
	 * This method terminates the connection to the database
	 **/
	void closeConnection();

	/**
	 * This method changes the delivery status of an existing order
	 *
	 * @param orderId The id of the order whose status will be changed
	 * @param status  The new status of the order. 3 legal statuses: Unprocessed,
	 *                Shipped, Delivered.
	 **/
	void changeDeliveryStatus(int orderId, String status);

	/**
	 * This method edits an already exiting product in the databse
	 *
	 * @param change The index of the attribute that is going to be edited
	 * @param shoe   Instance of shoe containing new values
	 **/
	void editShoe(int change, Shoe shoe);

	/**
	 * This method returns the shoe with the specified product id
	 *
	 * @param productId The id of the shoe
	 * @return The shoe
	 **/
	Shoe getShoe(int productId);
}
