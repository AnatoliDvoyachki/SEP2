package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;

/** Represents an order list object in the system **/
public class OrderList {
	private ArrayList<Order> orders = new ArrayList<>();

	/**
	 * Adds an order to the order list
	 * 
	 * @param order
	 *            the order to be added
	 **/
	public void add(Order order) {
		orders.add(order);
	}

	/**
	 * Removes and returns an order based on the orderId.
	 *
	 * @param orderId
	 * @return Order
	 */
	public Order remove(int orderId) {
		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getOrderId() == orderId) {
				return orders.remove(i);
			}
		}
		throw new InputMismatchException();
	}

	/**
	 * Returns and order based on the orderId
	 *
	 * @param index
	 * @return Order
	 */
	public Order get(int index) {
		return orders.get(index);
	}

	/**
	 * Returns the list of orders
	 * 
	 * @return order list
	 **/
	public ArrayList<Order> getList() {
		return orders;
	}

	@Override
	public String toString() {
		StringBuilder orderPrint = new StringBuilder();

		orderPrint.append(String.format("%-12s", "Order id"));
		orderPrint.append(String.format("%-15s", "Product id"));
		orderPrint.append(String.format("%-30s", "Billing name"));
		orderPrint.append(String.format("%-30s", "Billing address"));
		orderPrint.append(String.format("%-30s", "Username"));
		orderPrint.append(String.format("%-15s", "Order date"));
		orderPrint.append(String.format("%-12s", "Quantity"));
		orderPrint.append(String.format("%-10s", "Status"));
		orderPrint.append("\n");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (Order order : orders) {
			orderPrint.append(String.format("%-12s", order.getOrderId()));
			orderPrint.append(String.format("%-15s", order.getProductId()));
			orderPrint.append(String.format("%-30s", order.getBillingName()));
			orderPrint
					.append(String.format("%-30s", order.getBillingAddress()));
			orderPrint.append(String.format("%-30s", order.getUsername()));
			orderPrint.append(String.format("%-15s",
					dateFormat.format(order.getOrderDate())));
			orderPrint.append(String.format("%-12s", order.getQuantity()));
			orderPrint.append(String.format("%-10s", order.getStatus()));
			orderPrint.append("\n");
		}

		return orderPrint.toString();
	}

	/**
	 * Returns the number of orders in the list
	 * 
	 * @return the number of orders
	 **/
	public int size() {
		return orders.size();
	}

	/**
	 * This method is used to format the data of the object into a protocol
	 * message
	 * 
	 * @return the protocol message
	 **/
	public String toStringTable() {
		StringBuilder sb = new StringBuilder();

		int shoeListSize = orders.size();
		for (int i = 0; i < shoeListSize; i++) {
			sb.append("; ");
			sb.append(orders.get(i).toStringRow());
		}

		return sb.toString();
	}
}
