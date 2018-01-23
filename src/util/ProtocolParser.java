package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import model.*;

/**
 * Class intended to allow for easy reformatting of the protocol
 * following design changes by containing all protocol message
 * related logic.
 */
public class ProtocolParser {
	public static final String PRODUCTLIST_HEADER = "PRODUCTLIST";
	public static final String ORDERLIST_HEADER = "ORDERLIST";

	public static final String ORDER_HEADER = "ORDER";
	public static final String ORDER_RESPONSE_OK = "ORDER OK";
	public static final String ORDER_RESPONSE_NOT_OK = "ORDER NOTOK";

	public static final String LOGIN_HEADER = "LOGIN";
	public static final String LOGIN_RESPONSE_OK = "LOGIN OK";
	public static final String LOGIN_RESPONSE_NOT_OK = "LOGIN NOTOK";

	public static final String REGISTER_HEADER = "REGISTER";
	public static final String REGISTER_RESPONSE_OK = "REGISTER OK";
	public static final String REGISTER_RESPONSE_NOT_OK = "REGISTER NOTOK";

	// cannot be instantiated
	private ProtocolParser() {
	}

	/**
	 * Builds a request message for a ShoeList by breaking down a
	 * ShoeSearchCriteria, listing each criteria and it's value in the
	 * message.
	 * <p>
	 * Example message:
	 * PRODUCTLIST; sortPrice:0; sortSize:1; filterColor:black; filterBrand:Adidas
	 *
	 * @param searchCriteria ShoeSearchCriteria containing a map of all criteria keys and values.
	 * @return
	 */
	public static String shoeSearchMessage(ShoeSearchCriteria searchCriteria) {
		StringBuilder message = new StringBuilder();
		message.append(PRODUCTLIST_HEADER);
		// goes through every entry in the search criteria's map structure
		// and adds the key and value pair to the protocol message
		Map<String, String> listOfCriteria = searchCriteria.getCriteriaMap();
		for (Map.Entry<String, String> entry : listOfCriteria.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			message.append("; " + key + ":" + value);
		}
		return message.toString();
	}

	/**
	 * Builds a ShoeSearchCriteria by splitting the input into
	 * key and value pairs of criteria.
	 *
	 * @param message String message to be interpreted.
	 * @return
	 */
	public static ShoeSearchCriteria shoeSearchCriteriaFromMessage(String message) {
		ShoeSearchCriteria searchCriteria = new ShoeSearchCriteria();
		// if the condition is true then the list is empty
		if (!message.equals(PRODUCTLIST_HEADER)) {
			message = message.replace(PRODUCTLIST_HEADER, "");
			// splits the message then goes through each key value pair and
			// adds them as criteria
			String[] messageItems = message.split("; ");
			for (int i = 1; i < messageItems.length; i++) {
				String[] keyValuePair = messageItems[i].split(":");
				searchCriteria.putCriteria(keyValuePair[0], keyValuePair[1]);
			}
		}
		return searchCriteria;
	}

	/**
	 * Builds a response message for a ShoeList by breaking down
	 * a ShoeList into a message with all fields of each Shoe present.
	 * <p>
	 * Example message:
	 * PRODUCTLIST; 24:Nike:Free:Red:43.5:120:599.99:Best in line;
	 * 25:Adidas:Yeezy:Grey:39:20:799.99:Newest from Kanye West;
	 * 26:Nike:Kaishi:Orange:44.5:10:499.99:Light and agile
	 *
	 * @param shoeList ShoeList to be turned into protocol message.
	 * @return
	 */
	public static String shoeListMessage(ShoeList shoeList) {
		StringBuilder message = new StringBuilder();
		message.append(PRODUCTLIST_HEADER);
		for (int i = 0; i < shoeList.size(); i++) {
			Shoe shoe = shoeList.get(i);
			message.append("; ");
			message.append(shoe.getProductId() + ":"
					+ shoe.getBrand() + ":"
					+ shoe.getModel() + ":"
					+ shoe.getColor() + ":"
					+ shoe.getSize() + ":"
					+ shoe.getQuantity() + ":"
					+ shoe.getPrice() + ":"
					+ shoe.getDescription());
		}
		return message.toString();
	}

	/**
	 * Builds a ShoeList from a response message by essentially splitting
	 * the input into rows first then columns.
	 *
	 * @param message String message to be interpreted.
	 * @return
	 */
	public static ShoeList shoeListFromMessage(String message) {
		ShoeList responseShoeList = new ShoeList();
		// if the condition is true then the list is empty
		if (!message.equals(PRODUCTLIST_HEADER)) {
			String[] resultRows = message.split("; ");
			for (int i = 1; i < resultRows.length; i++) {
				String[] rowData = resultRows[i].split(":");
				Shoe currentShoe = null;
				if (rowData.length == 8) {
					currentShoe = new Shoe(
							Integer.parseInt(rowData[0]),
							rowData[1],
							rowData[2],
							rowData[3],
							Double.parseDouble(rowData[4]),
							Integer.parseInt(rowData[5]),
							Double.parseDouble(rowData[6]),
							rowData[7]
					);

				} else if (rowData.length == 7) {
					currentShoe = new Shoe(
							Integer.parseInt(rowData[0]),
							rowData[1],
							rowData[2],
							rowData[3],
							Double.parseDouble(rowData[4]),
							Integer.parseInt(rowData[5]),
							Double.parseDouble(rowData[6]),
							""
					);
				}
				responseShoeList.add(currentShoe);
			}
		}
		return responseShoeList;
	}

	/**
	 * Builds a request message for an OrderList of a specific
	 * user, by passing a username.
	 * message.
	 * <p>
	 * Example message:
	 * ORDERLIST; filterUsername:aragorn
	 *
	 * @param username String of username for which orders are desired.
	 * @return
	 */
	public static String orderSearchMessage(String username) {
		StringBuilder request = new StringBuilder();
		request.append(ORDERLIST_HEADER);
		request.append("; filterUsername:" + username);
		return request.toString();
	}

	/**
	 * Builds an OrderSearchCriteria by splitting the input into
	 * key and value pairs of criteria.
	 *
	 * @param message String message to be interpreted.
	 * @return
	 */
	public static OrderSearchCriteria orderSearchCriteriaFromMessage(String message) {
		OrderSearchCriteria searchCriteria = new OrderSearchCriteria();
		// if the condition is true then the list is empty
		if (!message.equals(ORDERLIST_HEADER)) {
			message = message.replace(ORDERLIST_HEADER, "");
			// splits the message then goes through each key value pair and
			// adds them as criteria
			String[] messageItems = message.split("; ");
			for (int i = 1; i < messageItems.length; i++) {
				String[] keyValuePair = messageItems[i].split(":");
				searchCriteria.putCriteria(keyValuePair[0], keyValuePair[1]);
			}
		}
		return searchCriteria;
	}

	/**
	 * Builds a response message for a OrderList by breaking down
	 * a OrderList into a message with all fields of each Order present.
	 * <p>
	 * Example message:
	 * ORDERLIST; 1104:14:Lars Highway:Lonesome Road 10:aragorn::599.99:Best in line;
	 * 25:Adidas:Yeezy:Grey:39:20:799.99:Newest from Kanye West;
	 * 26:Nike:Kaishi:Orange:44.5:10:499.99:Light and agile
	 *
	 * @param orderList OrderList to be turned into protocol message.
	 * @return
	 */
	public static String orderListMessage(OrderList orderList) {
		StringBuilder message = new StringBuilder();
		message.append(ORDERLIST_HEADER);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<Order> orders = orderList.getList();
		for (int i = 0; i < orders.size(); i++) {
			Order order = orders.get(i);
			message.append("; ");
			message.append(order.getOrderId() + ":"
					+ order.getProductId() + ":"
					+ order.getBillingName() + ":"
					+ order.getBillingAddress() + ":"
					+ order.getUsername() + ":"
					+ dateFormat.format(order.getOrderDate()) + ":"
					+ order.getQuantity() + ":"
					+ order.getStatus());
		}
		return message.toString();
	}

	/**
	 * Builds an OrderList from a response message by essentially splitting
	 * the input into rows first then columns.
	 *
	 * @param message String message to be interpreted
	 * @return
	 */
	public static OrderList orderListFromMessage(String message) {
		OrderList responseOrderList = new OrderList();
		// if the condition is true then the list is empty
		if (!message.equals(ORDERLIST_HEADER)) {
			String[] resultRows = message.split("; ");
			for (int i = 1; i < resultRows.length; i++) {
				String[] rowData = resultRows[i].split(":");

				String string = rowData[5];
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				Date date = null;
				try {
					date = format.parse(string);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				Order currentOrder = new Order(
						Integer.parseInt(rowData[0]),
						Integer.parseInt(rowData[1]),
						rowData[2],
						rowData[3],
						rowData[4],
						date,
						Integer.parseInt(rowData[6]),
						rowData[7]
				);
				responseOrderList.add(currentOrder);
			}
		}
		return responseOrderList;
	}

	/**
	 * Builds a request message to place an order on the server
	 * from an Order created locally.
	 * <p>
	 * Example message:
	 * ORDER; productId:0001; quantity:1; username:garkomann
	 * ; billingName:Mark Eriksson; billingAddress:Whereverway 10
	 *
	 * @param newOrder Order created from user inputs.
	 * @return
	 */
	public static String orderMessage(Order newOrder) {
		StringBuilder message = new StringBuilder();
		message.append(ORDER_HEADER);
		message.append("; productId:" + newOrder.getProductId());
		message.append("; quantity:" + newOrder.getQuantity());
		message.append("; username:" + newOrder.getUsername());

		if (!newOrder.getBillingName().equals("")) {
			message.append("; billingName:" + newOrder.getBillingName());
		}
		if (!newOrder.getBillingAddress().equals("")) {
			message.append("; billingAddress:" + newOrder.getBillingAddress());
		}

		return message.toString();
	}

	/**
	 * Builds a new order from a request message.
	 *
	 * @param message String message to be interpreted as an Order.
	 * @return
	 */
	public static Order orderFromMessage(String message) {
		String[] messageItem = message.split("; ");


		int productId = 0;
		String username = null;
		int quantity = 0;
		String billingName = null;
		String billingAddress = null;

		for (int i = 1; i < messageItem.length; i++) {

			if (messageItem[i].startsWith("productId:")) {
				productId = Parse.toInt(messageItem[i].replace(
						"productId:", ""));
			} else if (messageItem[i].startsWith("username:")) {
				username = messageItem[i].replace("username:", "");
			} else if (messageItem[i].startsWith("billingName:")) {
				billingName = messageItem[i].replace(
						"billingName:", "");
			} else if (messageItem[i].startsWith("billingAddress:")) {
				billingAddress = messageItem[i].replace(
						"billingAddress:", "");
			} else if (messageItem[i].startsWith("quantity:")) {
				quantity = Parse.toInt(messageItem[i].replace(
						"quantity:", ""));
			}
		}

		Order newOrder = new Order(productId, username, quantity);
		if (billingName != null) {
			newOrder.setBillingName(billingName);
		}
		if (billingAddress != null) {
			newOrder.setBillingAddress(billingAddress);
		}
		return newOrder;
	}

	/**
	 * Builds a request to a new order using the order id of the order.
	 * <p>
	 * Example message:
	 * ORDER OK; orderId:54301
	 *
	 * @param orderId Integer order id of the new Order.
	 * @return
	 */
	public static String oderOkMessage(int orderId) {
		return ORDER_RESPONSE_OK + "; orderId:" + orderId;
	}

	/**
	 * Builds a request message for logging in on a user
	 * from a pair of user credentials.
	 * <p>
	 * Example message:
	 * LOGIN; username:userfriendly; password:me123
	 *
	 * @param username String of inputted username.
	 * @param password String of inputted password.
	 * @return
	 */
	public static String loginMessage(String username, String password) {
		StringBuilder message = new StringBuilder();
		message.append(LOGIN_HEADER);
		message.append("; username:" + username);
		message.append("; password:" + password);
		return message.toString();
	}

	/**
	 * Builds a pair of login credentials from a 'LOGIN' protocol message.
	 * Returned as a string array with two indices - username and password.
	 *
	 * @param message String message to be interpreted as login credentials.
	 * @return
	 */
	public static String[] loginCredentialsFromMessage(String message) {
		message = message.replace(LOGIN_HEADER + "; ", "");
		String[] messageList = message.split("; ");
		String[] loginCredentials = {messageList[0].replace("username:", ""),
				messageList[1].replace("password:", "")};
		return loginCredentials;
	}

	/**
	 * Builds a request message for registering a new user
	 * from a User created locally.
	 * <p>
	 * Example message:
	 * REGISTER; username:garkomann; password:me123; name:Mark Eriksson;
	 * address:Whereverway 10; email:manster@mail.dk
	 *
	 * @param newUser User created from user inputs.
	 * @return
	 */
	public static String registerMessage(User newUser) {
		StringBuilder message = new StringBuilder();
		message.append(REGISTER_HEADER);
		message.append("; username:" + newUser.getUsername());
		message.append("; password:" + newUser.getPassword());
		message.append("; name:" + newUser.getName());
		message.append("; address:" + newUser.getAddress());
		message.append("; email:" + newUser.getEmail());
		return message.toString();
	}

	/**
	 * Builds a new user from a request message.
	 *
	 * @param message String message to be interpreted as User.
	 * @return
	 */
	public static User userFromMessage(String message) {
		String[] messageItems = message.split("; ");

		String username = null;
		String password = null;
		String name = null;
		String address = null;
		String email = null;

		for (int i = 1; i < messageItems.length; i++) {

			if (messageItems[i].startsWith("username:")) {
				username = messageItems[i].replace("username:", "");
			} else if (messageItems[i].startsWith("password:")) {
				password = messageItems[i].replace("password:", "");
			} else if (messageItems[i].startsWith("name:")) {
				name = messageItems[i].replace("name:", "");
			} else if (messageItems[i].startsWith("address:")) {
				address = messageItems[i].replace("address:", "");
			} else if (messageItems[i].startsWith("email:")) {
				email = messageItems[i].replace("email:", "");
			}
		}

		return new User(username, password, name, address,
				email);
	}
}
