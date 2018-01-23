package test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import model.Order;
import model.OrderList;
import model.OrderSearchCriteria;
import model.Shoe;
import model.ShoeList;
import model.ShoeSearchCriteria;
import model.User;
import org.junit.Before;
import org.junit.Test;

import util.ProtocolParser;

public class ProtocolTest {

	private String hashMapToString(HashMap<String, String> hashmap) {
		String out = "";
		for (Map.Entry<String, String> entry : hashmap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			out += key + ":" + value + " ";
		}
		return out;
	}

	@Before
	public void setUp() throws Exception {

	}

	// should be renamed to buildShoeSearchMessage
	@Test
	public void testBuildShoeListMessage_ShoeSearchCriteria_1() {
		ShoeSearchCriteria shoeSearchCriteria = new ShoeSearchCriteria();

		shoeSearchCriteria.putCriteria(ShoeSearchCriteria.SORT_SIZE, "1");

		String expected = "PRODUCTLIST; sortSize:1";

		String actual = ProtocolParser.shoeSearchMessage(shoeSearchCriteria);
		assertEquals(expected, actual);
	}

	@Test
	public void testBuildShoeListMessage_ShoeSearchCriteria_2() {
		ShoeSearchCriteria shoeSearchCriteria = new ShoeSearchCriteria();

		shoeSearchCriteria.putCriteria(ShoeSearchCriteria.SORT_SIZE, "1");
		shoeSearchCriteria.putCriteria(ShoeSearchCriteria.SORT_PRICE, "0");
		shoeSearchCriteria
				.putCriteria(ShoeSearchCriteria.FILTER_COLOR, "black");
		shoeSearchCriteria.putCriteria(ShoeSearchCriteria.FILTER_BRAND,
				"Adidas");

		String expected = "PRODUCTLIST; sortSize:1; filterBrand:Adidas; filterColor:black; sortPrice:0";

		String actual = ProtocolParser.shoeSearchMessage(shoeSearchCriteria);
		assertEquals(expected, actual);
	}

	@Test
	public void testBuildShoeSearchCriteriaFromMessage_1() {
		String message = "PRODUCTLIST; sortPrice:1";

		ShoeSearchCriteria shoeSearchCriteria = ProtocolParser
				.shoeSearchCriteriaFromMessage(message);

		String expected = "sortPrice:1 ";
		String actual = hashMapToString(shoeSearchCriteria.getCriteriaMap());
		assertEquals(expected, actual);
	}

	@Test
	public void testBuildShoeSearchCriteriaFromMessage_2() {
		String message = "PRODUCTLIST; sortPrice:0; sortSize:1; filterColor:black; filterBrand:Adidas";

		ShoeSearchCriteria shoeSearchCriteria = ProtocolParser
				.shoeSearchCriteriaFromMessage(message);

		String expected = "sortSize:1 filterBrand:Adidas filterColor:black sortPrice:0 ";
		String actual = hashMapToString(shoeSearchCriteria.getCriteriaMap());
		assertEquals(expected, actual);
	}

	@Test
	public void testBuildShoeSearchCriteriaFromMessage_3() {
		String message = "PRODUCTLIST";

		ShoeSearchCriteria shoeSearchCriteria = ProtocolParser
				.shoeSearchCriteriaFromMessage(message);

		String expected = "";
		String actual = hashMapToString(shoeSearchCriteria.getCriteriaMap());
		assertEquals(expected, actual);
	}

	@Test
	public void testBuildShoeSearchCriteriaFromMessage_4() {
		String message = "PRODUCTLIST; sortXXX:0";

		ShoeSearchCriteria shoeSearchCriteria = ProtocolParser
				.shoeSearchCriteriaFromMessage(message);

		String expected = "sortXXX:0 ";
		String actual = hashMapToString(shoeSearchCriteria.getCriteriaMap());
		assertEquals(expected, actual);
	}

	@Test
	public void testBuildShoeSearchCriteriaFromMessage_5() {
		String message = "PRODUCTLIST;  sortXXX : 0";

		ShoeSearchCriteria shoeSearchCriteria = ProtocolParser
				.shoeSearchCriteriaFromMessage(message);

		String expected = " sortXXX : 0 ";
		String actual = hashMapToString(shoeSearchCriteria.getCriteriaMap());
		assertEquals(expected, actual);
	}

	@Test
	public void testBuildShoeListMessage_1() {
		Shoe shoe01 = new Shoe(1, "Nike", "Freerun", "Black", 42.00, 100,
				700.00, "Best ones");
		Shoe shoe02 = new Shoe(2, "Adidas", "Classic", "White", 43.00, 200,
				500.00, "The old ones");

		ShoeList shoeList = new ShoeList();
		shoeList.add(shoe01);
		shoeList.add(shoe02);

		String expected = "PRODUCTLIST; "
				+ "1:Nike:Freerun:Black:42.0:100:700.0:Best ones; "
				+ "2:Adidas:Classic:White:43.0:200:500.0:The old ones";
		assertEquals(expected, ProtocolParser.shoeListMessage(shoeList));
	}

	@Test
	public void testBuildShoeListMessage_2() {

		ShoeList shoeList = new ShoeList();

		String expected = "PRODUCTLIST";
		assertEquals(expected, ProtocolParser.shoeListMessage(shoeList));
	}

	@Test
	public void testBuildShoeListMessage_3() {
		Shoe shoe01 = new Shoe(1, "Nike", "Freerun", "Black", 42.00, 100,
				700.00, "Best ones");

		ShoeList shoeList = new ShoeList();
		shoeList.add(shoe01);

		String expected = "PRODUCTLIST; "
				+ "1:Nike:Freerun:Black:42.0:100:700.0:Best ones";
		assertEquals(expected, ProtocolParser.shoeListMessage(shoeList));
	}

	@Test
	public void testBuildShoeListFromMessage_1() {
		String message = "PRODUCTLIST; "
				+ "1:Nike:Freerun:Black:42.0:100:700.0:Best ones; "
				+ "2:Adidas:Classic:White:43.0:200:500.0:The old ones";

		Shoe shoe01 = new Shoe(1, "Nike", "Freerun", "Black", 42.00, 100,
				700.00, "Best ones");
		Shoe shoe02 = new Shoe(2, "Adidas", "Classic", "White", 43.00, 200,
				500.00, "The old ones");
		ShoeList expected = new ShoeList();
		expected.add(shoe01);
		expected.add(shoe02);

		ShoeList actual = ProtocolParser.shoeListFromMessage(message);

		assertEquals(expected.toString(), actual.toString());
	}

	@Test
	public void testBuildShoeListFromMessage_2() {
		String message = "PRODUCTLIST";

		ShoeList expected = new ShoeList();

		ShoeList actual = ProtocolParser.shoeListFromMessage(message);

		assertEquals(expected.toString(), actual.toString());
	}

	// should be renamed to buildOrderSearchMessage
	@Test
	public void testBuildOrderListMessage_String_1() {

		String expected = "ORDERLIST; filterUsername:joe";
		String actual = ProtocolParser.orderSearchMessage("joe");

		assertEquals(expected, actual);
	}

	@Test
	public void testBuildOrderSearchCriteriaFromMessage_1() {
		String message = "ORDERLIST; filterUsername:joe";

		OrderSearchCriteria searchCriteria = ProtocolParser
				.orderSearchCriteriaFromMessage(message);

		String expected = "filterUsername:joe ";
		String actual = hashMapToString(searchCriteria.getCriteriaMap());
		assertEquals(expected, actual);
	}

	/*
	 * i don't know how to make this pass. this method is getting orderList from
	 * database from database. when sample OrderList with sample date is created
	 * like this, seems to work differently
	 */
	@Test
	public void testBuildOrderListMessage_OrderList() {

		// creating sample dates
		String dateString1 = "2017-01-01";
		// String dateString2 = "2017-31-01";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date1 = null;
		// Date date2 = null;
		try {
			date1 = format.parse(dateString1);
			// date2 = format.parse(dateString2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Order order01 = new Order(1, 101, "john doe", "horsens 20", "john9",
				date1, 1, "Unprocessed");

		OrderList orderList = new OrderList();
		orderList.add(order01);

		String expected = "ORDERLIST; 1:101:john doe:horsens 20:john9:2017-01-01:1:Unprocessed";
		String actual = ProtocolParser.orderListMessage(orderList);
		assertEquals(expected, actual);
	}

	@Test
	public void testBuildOrderListFromMessage() {

		String message = "ORDERLIST; 1:101:john doe:horsens 20:john9:2017-01-01:1:Unprocessed";

		// creating sample dates
		String dateString1 = "2017-01-01";
		// String dateString2 = "2017-31-01";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date1 = null;
		try {
			date1 = format.parse(dateString1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Order order01 = new Order(1, 101, "john doe", "horsens 20", "john9",
				date1, 1, "Unprocessed");
		OrderList expected = new OrderList();
		expected.add(order01);

		OrderList actual = ProtocolParser.orderListFromMessage(message);

		assertEquals(expected.toString(), actual.toString());
	}

	@Test
	public void testBuildNewOrderMessage_Order() {
		Order order = new Order(1, "joshua", "hawai", "j1000", 1);

		String expected = "ORDER; productId:1; quantity:1; username:j1000; billingName:joshua; billingAddress:hawai";
		String actual = ProtocolParser.orderMessage(order);

		assertEquals(expected, actual);
	}

	@Test
	public void testBuildNewOrderFromMessage() {
		String message = "ORDER; productId:1; quantity:1; username:j1000; billingName:joshua; billingAddress:hawai";

		Order expected = new Order(1, "joshua", "hawai", "j1000", 1);
		Order actual = ProtocolParser.orderFromMessage(message);

		assertEquals(expected.toString(), actual.toString());
	}

	// should be renamed to buildOrderOkMessage
	@Test
	public void testBuildNewOrderMessage_int() {
		String expected = "ORDER OK; orderId:54301";
		String actual = ProtocolParser.oderOkMessage(54301);

		assertEquals(expected, actual);
	}

	@Test
	public void testBuildLoginMessage() {
		String expected = "LOGIN; username:userfriendly; password:me123";
		String actual = ProtocolParser.loginMessage("userfriendly", "me123");

		assertEquals(expected, actual);
	}

	@Test
	public void testBuildLoginCredentialsFromMessage() {
		String message = "LOGIN; username:userfriendly; password:me123";

		String[] expected = { "userfriendly", "me123" };
		String[] actual = ProtocolParser.loginCredentialsFromMessage(message);

		assertEquals(Arrays.toString(expected), Arrays.toString(actual));
	}

	@Test
	public void testBuildRegisterMessage() {
		String expected = "REGISTER; username:moby_dick; password:password_1; "
				+ "name:Saul; address:neverland; " + "email:saul@email.com";

		User user01 = new User("moby_dick", "password_1", "Saul", "neverland",
				"saul@email.com");

		assertEquals(expected, ProtocolParser.registerMessage(user01));
	}

	@Test
	public void testBuildNewUserFromMessage() {
		String message = "REGISTER; username:moby_dick; password:password_1; "
				+ "name:Saul; address:neverland; " + "email:saul@email.com";

		User user01 = new User("moby_dick", "password_1", "Saul", "neverland",
				"saul@email.com");

		assertEquals(user01.toString(),
				ProtocolParser.userFromMessage(message).toString());

	}

}
