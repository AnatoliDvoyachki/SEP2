package server.controller;

import model.Order;
import model.OrderSearchCriteria;
import model.Shoe;
import model.ShoeSearchCriteria;
import server.view.*;
import server.domain.mediator.ServerModel;
import util.Parse;
import util.Validate;

/**
 * Controls the data flow into the ServerModelManager and updates the ServerView
 **/
public class ServerController {

	private ServerModel model;
	private ServerView view;

	public static final String EXECUTE_LIST_SHOES = "List Shoes";
	public static final String EXECUTE_LIST_ORDERS = "List Orders";
	public static final String EXECUTE_LIST_CUSTOMERS = "List Customers";
	public static final String EXECUTE_ADD_SHOE = "Add Shoe";
	public static final String EXECUTE_CHANGE_SHOE_DATA = "Change Shoe Data";
	public static final String EXECUTE_CHANGE_DELIVERY_STATUS = "Change delivery status";
	public static final String EXECUTE_REMOVE_SHOE = "Remove Shoe";
	public static final String EXECUTE_QUIT = "Quit";
	public static final String EXECUTE_SEARCH_SHOES = "Search Shoes";
	public static final String EXECUTE_SEARCH_ORDERS = "Search Orders";

	/** Used to instantiate the ServerController **/
	public ServerController(ServerModel model, ServerView view) {
		this.model = model;
		this.view = view;
	}

	/**
	 * Used to start specific functionalities available on the server side
	 * 
	 * @param what
	 *            the functionality to be executed
	 **/
	public void execute(String what) {

		switch (what) {

		case EXECUTE_LIST_SHOES:
			view.show(model.getShoeList(new ShoeSearchCriteria()).toString());
			break;

		case EXECUTE_LIST_ORDERS:
			view.show(model.getOrderList(new OrderSearchCriteria()).toString());
			break;

		case EXECUTE_LIST_CUSTOMERS:
			view.show(model.getUserList().toString());
			break;

		case EXECUTE_ADD_SHOE:
			executeAddShoe();
			break;

		case EXECUTE_CHANGE_SHOE_DATA:
			executeEditShoe();
			break;

		case EXECUTE_CHANGE_DELIVERY_STATUS:
			executeChangeDeliveryStatus();
			break;

		case EXECUTE_REMOVE_SHOE:
			executeRemoveShoe();
			break;

		case EXECUTE_SEARCH_SHOES:
			executeSearchShoes();
			break;
		case EXECUTE_SEARCH_ORDERS:
			executeSearchOrders();
			break;

		case EXECUTE_QUIT:
			model.closeConnection();
			System.exit(0);
			break;

		default:
			break;
		}
	}

	private void executeSearchOrders() {
		view.show("Enter nothing to skip step");

		// fills a search criteria object with key value pairs of different
		// search criteria
		OrderSearchCriteria searchCriteria = new OrderSearchCriteria();

		try {
			searchCriteria.putCriteria(OrderSearchCriteria.SORT_QUANTITY,
					view.get("sort by QUANTITY. 0: low-high, 1: high-low"));
			searchCriteria.putCriteria(OrderSearchCriteria.SORT_DATE, view
					.get("sort by DATE. 0: oldest-newest, 1: newest-oldest"));
			searchCriteria.putCriteria(OrderSearchCriteria.FILTER_ORDER_ID,
					view.get("criteria for ORDER ID"));
			searchCriteria.putCriteria(OrderSearchCriteria.FILTER_STATUS,
					view.get("criteria for STATUS"));
			searchCriteria.putCriteria(OrderSearchCriteria.FILTER_USERNAME,
					view.get("criteria for USERNAME"));

		} catch (Exception e) {
			view.show("Invalid input");
		}

		view.show(model.getOrderList(searchCriteria).toString());

	}

	private void executeSearchShoes() {
		view.show("Enter nothing to skip step");

		// fills a search criteria object with key value pairs of different
		// search criteria
		ShoeSearchCriteria searchCriteria = new ShoeSearchCriteria();

		try {
			searchCriteria.putCriteria(ShoeSearchCriteria.SORT_PRICE,
					view.get("sort for PRICE. 0: low-high, 1: high-low"));
			searchCriteria.putCriteria(ShoeSearchCriteria.SORT_SIZE,
					view.get("sort for SIZE. 0: low-high, 1: high-low"));
			searchCriteria.putCriteria(ShoeSearchCriteria.FILTER_SIZE,
					view.get("criteria for SIZE"));
			searchCriteria.putCriteria(ShoeSearchCriteria.FILTER_COLOR,
					view.get("criteria for COLOR"));
			searchCriteria.putCriteria(ShoeSearchCriteria.FILTER_BRAND,
					view.get("criteria for BRAND"));
			searchCriteria.putCriteria(ShoeSearchCriteria.FILTER_MODEL,
					view.get("criteria for MODEL"));
		} catch (Exception e) {
			view.show("Invalid input");
		}

		view.show(model.getShoeList(searchCriteria).toString());
	}

	private void executeAddShoe() {
		try {
			String brand = view.get("brand");
			Validate.validateTextualInput(brand, Shoe.BRAND_CHARACTER_LIMIT);
			String shoeModel = view.get("model");
			Validate.validateTextualInput(shoeModel, Shoe.MODEL_CHARACTER_LIMIT);
			String color = view.get("color");
			Validate.validateTextualInput(color, Shoe.COLOR_CHARACTER_LIMIT);
			String size = view.get("size");
			Validate.validateDoubleInput(size, Shoe.SIZE_DECIMAL_LIMIT[0],
					Shoe.SIZE_DECIMAL_LIMIT[1]);
			String quantity = view.get("quantity");
			Validate.validateIntegerInput(quantity);
			String price = view.get("price");
			Validate.validateDoubleInput(price, Shoe.PRICE_DECIMAL_LIMIT[0],
					Shoe.PRICE_DECIMAL_LIMIT[1]);
			String description = view.get("description");

			Shoe newShoe = new Shoe(brand, shoeModel, color,
					Parse.toDouble(size), Parse.toInt(quantity),
					Parse.toDouble(price), description);

			model.addShoe(newShoe);
			view.show("Shoe successfully added!");
		} catch (Exception e) {
			view.show("Invalid input");
		}
	}

	private void executeRemoveShoe() {
		try {
			int removeProductId = Parse.toInt(view
					.get("id of product to delete"));
			model.removeShoe(removeProductId);
			view.show("Shoe successfully removed");
		} catch (Exception e) {
			view.show("Invalid Input");
		}
	}

	private void executeEditShoe() {
		try {
			int productId = Parse.toInt(view.get("product id"));
			// requests the desired shoe from the model
			Shoe tmpShoe = model.getShoe(productId);

			view.show(tmpShoe.toString());

			int attributeIndex = Parse.toInt(view
					.get("index of attribute to change"));
			Validate.validateAttributeNumber(attributeIndex);
			String newValue = view.get("new value");

			switch (attributeIndex) {

			case 1:
				tmpShoe.setBrand(newValue);
				break;
			case 2:
				tmpShoe.setModel(newValue);
				break;
			case 3:
				tmpShoe.setColor(newValue);
				break;
			case 4:
				tmpShoe.setSize(Parse.toDouble(newValue));
				break;
			case 5:
				tmpShoe.setQuantity(Parse.toInt(newValue));
				break;
			case 6:
				tmpShoe.setPrice(Parse.toDouble(newValue));
				break;
			case 7:
				tmpShoe.setDescription(newValue);
				break;
			default:
				break;
			}
			model.editShoe(attributeIndex, tmpShoe);
			view.show("Shoe successfully edited");
		} catch (Exception e) {
			view.show("Invalid input");
		}
		view.show("");
	}

	private void executeChangeDeliveryStatus() {
		try {
			int orderId = Parse.toInt(view.get("order id"));
			view.show("Possible delivery statuses:");
			view.show("(1) " + Order.STATUS_UNPROCESSED);
			view.show("(2) " + Order.STATUS_SHIPPED);
			view.show("(3) " + Order.STATUS_DELIVERED);
			String index = view.get("index of the new delivery status");
			model.changeDeliveryStatus(orderId, index);
			view.show("Order status successfully changed");
		} catch (Exception e) {
			view.show("Invalid input");
		}
	}

}
