package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import util.Validate;

/** Represents an order object in the system **/
public class Order {

	public static final String STATUS_UNPROCESSED = "Unprocessed";
	public static final String STATUS_SHIPPED = "Shipped";
	public static final String STATUS_DELIVERED = "Delivered";
	public static final String[] STATUS_ALLOWED_VALUES = { STATUS_UNPROCESSED,
			STATUS_SHIPPED, STATUS_DELIVERED };

	private int orderId;
	private int productId;
	private String billingName;
	private String billingAddress;
	private String username;
	private Date orderDate;
	private int quantity;
	private String status;

	/**
	 * Constructor for pulling object from the persistence. Takes all fields of
	 * the class as parameters.
	 *
	 * @param orderId
	 * @param productId
	 * @param billingName
	 * @param billingAddress
	 * @param username
	 * @param orderDate
	 * @param quantity
	 * @param status
	 */
	public Order(int orderId, int productId, String billingName,
			String billingAddress, String username, Date orderDate,
			int quantity, String status) {
		this.orderId = orderId;
		this.productId = productId;
		this.billingName = billingName;
		this.billingAddress = billingAddress;
		this.username = username;
		this.orderDate = orderDate;
		this.quantity = quantity;
		if (status != null) {
			this.status = status;
		} else {
			this.status = "Unprocessed";
		}
	}

	/**
	 * Constructor for creating object within the application. Includes
	 * billingName and billingAddress, which are only required if the user of
	 * the system decides to specify them.
	 *
	 * @param productId
	 * @param billingName
	 * @param billingAddress
	 * @param username
	 * @param quantity
	 */
	public Order(int productId, String billingName, String billingAddress,
			String username, int quantity) {
		setProductId(productId);
		setBillingName(billingName);
		setBillingAddress(billingAddress);
		setUsername(username);
		this.orderDate = new Date();
		setQuantity(quantity);
		this.status = "Unprocessed";
	}

	/**
	 * Constructor for creating object within the application. This constructor
	 * is used in cases in which the user does not want to specify billingName
	 * or billingAddress.
	 *
	 * @param productId
	 * @param username
	 * @param quantity
	 */
	public Order(int productId, String username, int quantity) {
		setProductId(productId);
		setUsername(username);
		billingName = "";
		billingAddress = "";
		this.orderDate = new Date();
		setQuantity(quantity);
		this.status = "Unprocessed";
	}

	/**
	 * Returns the order id of the order
	 * 
	 * @return the order id
	 **/
	public int getOrderId() {
		return orderId;
	}

	/**
	 * Returns the product id of the ordered item
	 * 
	 * @return the product id
	 **/
	public int getProductId() {
		return productId;
	}

	/**
	 * Returns the billing name of the order
	 * 
	 * @return the billing name
	 **/
	public String getBillingName() {
		return billingName;
	}

	/**
	 * Returns the billing address of the order
	 * 
	 * @return the billing address
	 * 
	 **/
	public String getBillingAddress() {
		return billingAddress;
	}

	/**
	 * Returns the username of the owner of the order
	 * 
	 * @return the username
	 **/
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the order date of the order
	 * 
	 * @return the order date
	 **/
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * Returns the quantity of products that are ordered
	 * 
	 * @return the quantity
	 **/
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Returns the status of the order
	 * 
	 * @return the status
	 **/
	public String getStatus() {
		return status;
	}

	/**
	 * Validates and sets the product id passed as a parameter
	 * 
	 * @param productId
	 *            The product id
	 * **/
	public void setProductId(int productId) {
		Validate.validateIntegerInput(Integer.toString(productId));
		this.productId = productId;
	}

	/**
	 * Sets the billing name of the order
	 * 
	 * @param billingName
	 *            the billing name
	 **/
	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}

	/**
	 * Sets the billing address of the order
	 * 
	 * @param billingAddress
	 *            the billing address
	 **/
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	/**
	 * Validates and sets the username passed as a parameter
	 *
	 * @param username
	 *            The username
	 **/
	public void setUsername(String username) {
		Validate.validateTextualInput(username, User.USERNAME_CHARACTER_LIMIT);
		this.username = username;
	}

	/**
	 * Validates and sets the quantity passed as a parameter
	 *
	 * @param quantity
	 *            The quantity
	 **/
	public void setQuantity(int quantity) {
		Validate.validateIntegerInput(Integer.toString(quantity));
		this.quantity = quantity;
	}

	/**
	 * Validates and sets the status passed as a parameter
	 * 
	 * @param status
	 *            The status
	 * **/
	public void setStatus(String status) {
		Validate.validateOrderStatus(status);
		this.status = status;
	}

	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder orderPrint = new StringBuilder();
		orderPrint.append(String.format("%-12s", orderId));
		orderPrint.append(String.format("%-15s", productId));
		orderPrint.append(String.format("%-30s", billingName));
		orderPrint.append(String.format("%-30s", billingAddress));
		orderPrint.append(String.format("%-30s", username));
		orderPrint.append(String.format("%-15s", dateFormat.format(orderDate)));
		orderPrint.append(String.format("%-12s", quantity));
		orderPrint.append(String.format("%-10s", status));
		return orderPrint.toString();
	}

	/**
	 * This method is used to format the data of the object into a protocol
	 * message
	 **/
	public String toStringRow() {
		return orderId + ":" + productId + ":" + billingName + ":"
				+ billingAddress + ":" + username + ":" + orderDate.toString()
				+ ":" + quantity + ":" + status;
	}
}
