package test;

import model.Order;
import model.OrderList;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import static org.junit.Assert.*;

public class OrderListTest {
	private OrderList orderList;

	@Before
	public void setUp() {
		orderList = new OrderList();
	}

	@Test
	public void add() {
		Order order01 = new Order(4103, 323, "May Delaney", "Beachway 12",
				"typecaster", new Date(), 5, "Shipped");
		Order order02 = new Order(20132, 24, "Man Tiller", "Waystreeet 5",
				"eclaire", new Date(), 1, "Shipped");

		orderList.add(order01);
		orderList.add(order02);
		
		orderList.add(order01);
		orderList.add(order02);
		assertEquals(order01, orderList.get(0));
		assertEquals(order02, orderList.get(1));
	}

	@Test
	public void get() {
		Order order01 = new Order(4103, 323, "May Delaney", "Beachway 12",
				"typecaster", new Date(), 5, "Shipped");
		Order order02 = new Order(20132, 24, "Man Tiller", "Waystreeet 5",
				"eclaire", new Date(), 1, "Shipped");

		orderList.add(order01);
		orderList.add(order02);
		assertEquals(order01, orderList.get(0));
		assertEquals(order02, orderList.get(1));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void get_invalid() {
		Order order01 = new Order(4103, 323, "May Delaney", "Beachway 12",
				"typecaster", new Date(), 5, "Shipped");
		Order order02 = new Order(20132, 24, "Man Tiller", "Waystreeet 5",
				"eclaire", new Date(), 1, "Shipped");

		orderList.add(order01);
		orderList.add(order02);
		assertEquals(order01, orderList.get(3));
	}

	@Test
	public void remove() {
		Order order01 = new Order(4103, 323, "May Delaney", "Beachway 12",
				"typecaster", new Date(), 5, "Shipped");
		Order order02 = new Order(20132, 24, "Man Tiller", "Waystreeet 5",
				"eclaire", new Date(), 1, "Shipped");

		orderList.add(order01);
		orderList.add(order02);
		assertEquals(order01, orderList.remove(4103));
	}


}