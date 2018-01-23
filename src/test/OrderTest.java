package test;

import model.Order;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;

import static org.junit.Assert.*;

public class OrderTest {
	private Order order;

	@Before
	public void setUp() throws Exception {
		order = new Order(4103, 323, "May Delaney", "Beachway 12", "typecaster", new Date(), 5, "Shipped");
	}

	@Test
	public void getOrderId() throws Exception {
		assertEquals(4103, order.getOrderId());
	}

	@Test
	public void getProductId() throws Exception {
		assertEquals(323, order.getProductId());
	}

	@Test
	public void getBillingName() throws Exception {
		assertEquals("May Delaney", order.getBillingName());
	}

	@Test
	public void getBillingAddress() throws Exception {
		assertEquals("Beachway 12", order.getBillingAddress());
	}

	@Test
	public void getUsername() throws Exception {
		assertEquals("typecaster", order.getUsername());
	}

	@Test
	public void getOrderDate() throws Exception {
		assertEquals(new Date(), order.getOrderDate());
	}

	@Test
	public void getQuantity() throws Exception {
		assertEquals(5, order.getQuantity());
	}

	@Test
	public void getStatus() throws Exception {
		assertEquals("Shipped", order.getStatus());
	}

	@Test
	public void setProductId() throws Exception {
		order.setProductId(4000);
		assertEquals(4000, order.getProductId());
	}

	@Test (expected = InputMismatchException.class)
	public void setProductIdInvalidInput() throws Exception {
		order.setProductId(-1);
	}

	@Test
	public void setBillingName() throws Exception {
		order.setBillingName("Miss Turner");
		assertEquals("Miss Turner", order.getBillingName());
	}

	@Test
	public void setBillingName2() throws Exception {
		order.setBillingName("Omar Ahmad Bahir Kalil Mehmet Jarah Murad Amal Jalaal Adir Fadil Hakim Waheed Samir Karim Saalim Seyyid Bashaarat");
	}

	@Test 
	public void setBillingName3() throws Exception {
		order.setBillingName(null);
	}

	@Test
	public void setBillingAddress() throws Exception {
		order.setBillingAddress("Longsome Road 11");
		assertEquals("Longsome Road 11", order.getBillingAddress());
	}

	@Test
	public void setBillingAddress2() throws Exception {
		order.setBillingAddress("The Two Thousand One Hundred Twentieth Something Road With A Name Like This In The City With The Most Roads In The World Therefore Demanding A Ridiculously Long And Specific Name Like This As To Avoid Having One Or More Roads With The Same Name 2054");
	}

	@Test
	public void setBillingAddress3() throws Exception {
		order.setBillingAddress("");
	}

	@Test
	public void setBillingAddress4() throws Exception {
		order.setBillingAddress(null);
	}

	@Test
	public void setUsername() throws Exception {
		order.setUsername("nextinline");
		assertEquals("nextinline", order.getUsername());
	}

	@Test (expected = InputMismatchException.class)
	public void setUsernameInvalidInputLength() throws Exception {
		order.setUsername("andersontopofthelineexclusiverepresentative");
	}

	@Test (expected = InputMismatchException.class)
	public void setUsernameInvalidInputLength2() throws Exception {
		order.setUsername("");
	}

	@Test (expected = InputMismatchException.class)
	public void setUsernameInvalidInputNull() throws Exception {
		order.setUsername(null);
	}

	@Test
	public void setQuantity() throws Exception {
		order.setQuantity(51);
		assertEquals(51, order.getQuantity());
	}

	@Test (expected = InputMismatchException.class)
	public void setQuantityInvalidInput() throws Exception {
		order.setQuantity(-1);
	}

	@Test
	public void setStatus() throws Exception {
		order.setStatus("Shipped");
		order.setStatus("Unprocessed");
		order.setStatus("Delivered");
		assertEquals("Delivered", order.getStatus());
	}

	@Test (expected = InputMismatchException.class)
	public void setStatusInvalidInput() throws Exception {
		order.setStatus("Unshipped");
	}

	@Test
	public void toStringTest() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals(String.format("%-12s%-15s%-30s%-30s%-30s%-15s%-12s%-10s", order.getOrderId(), order.getProductId(), order.getBillingName(), order.getBillingAddress(), order.getUsername(), dateFormat.format(order.getOrderDate()), order.getQuantity(), order.getStatus()), order.toString());
	}
}