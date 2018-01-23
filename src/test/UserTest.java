package test;

import model.User;

import org.junit.Before;
import org.junit.Test;

import java.util.InputMismatchException;

import static org.junit.Assert.*;

public class UserTest {
	private User user;

	@Before
	public void setUp() throws Exception {
		user = new User("dandylion", "bestfriend", "John Kiegelson", "Namestreet 10", "johnsroom@gmail.com");
	}

	@Test
	public void getUsername() throws Exception {
		assertEquals("dandylion", user.getUsername());
	}

	@Test
	public void getPassword() throws Exception {
		assertEquals("bestfriend", user.getPassword());
	}

	@Test
	public void getName() throws Exception {
		assertEquals("John Kiegelson", user.getName());
	}

	@Test
	public void getAddress() throws Exception {
		assertEquals("Namestreet 10", user.getAddress());
	}

	@Test
	public void getEmail() throws Exception {
		assertEquals("johnsroom@gmail.com", user.getEmail());
	}

	@Test
	public void setUsername() throws Exception {
		user.setUsername("fart");
		assertEquals("fart", user.getUsername());
	}

	@Test (expected = InputMismatchException.class)
	public void setUsernameInvalidInputLength() throws Exception {
		user.setUsername("andersontopofthelineexclusiverepresentative");
	}

	@Test (expected = InputMismatchException.class)
	public void setUsernameInvalidInputLength2() throws Exception {
		user.setUsername("");
	}

	@Test (expected = InputMismatchException.class)
	public void setUsernameInvalidInputNull() throws Exception {
		user.setUsername(null);
	}

	@Test
	public void setPassword() throws Exception {
		user.setPassword("girlfriend");
		assertEquals("girlfriend", user.getPassword());
	}

	@Test (expected = InputMismatchException.class)
	public void setPasswordInputLength() throws Exception {
		user.setPassword("password1234567890johnjackjanejeff");
	}

	@Test (expected = InputMismatchException.class)
	public void setPasswordInputLength2() throws Exception {
		user.setPassword("");
	}

	@Test (expected = InputMismatchException.class)
	public void setPasswordInputNull() throws Exception {
		user.setPassword(null);
	}

	@Test
	public void setName() throws Exception {
		user.setName("Jeff Server");
		assertEquals("Jeff Server", user.getName());
	}

	@Test (expected = InputMismatchException.class)
	public void setNameInvalidInputLength() throws Exception {
		user.setName("Omar Ahmad Bahir Kalil Mehmet Jarah Murad Amal Jalaal Adir Fadil Hakim Waheed Samir Karim Saalim Seyyid Bashaarat");
	}

	@Test (expected = InputMismatchException.class)
	public void setNameInvalidInputLength2() throws Exception {
		user.setName("");
	}

	@Test (expected = InputMismatchException.class)
	public void setNameInvalidInputNull() throws Exception {
		user.setName(null);
	}

	@Test
	public void setAddress() throws Exception {
		user.setAddress("Nextdoor 123");
		assertEquals("Nextdoor 123", user.getAddress());
	}

	@Test (expected = InputMismatchException.class)
	public void setAddressInvalidInputLength() throws Exception {
		user.setAddress("The Two Thousand One Hundred Twentieth Something Road With A Name Like This In The City With The Most Roads In The World Therefore Demanding A Ridiculously Long And Specific Name Like This As To Avoid Having One Or More Roads With The Same Name 2054");
	}

	@Test (expected = InputMismatchException.class)
	public void setAddressInvalidInputLength2() throws Exception {
		user.setAddress("");
	}

	@Test (expected = InputMismatchException.class)
	public void setAddressInvalidInputNull() throws Exception {
		user.setAddress(null);
	}

	@Test
	public void setEmail() throws Exception {
		user.setEmail("fallenhero@wc3.net");
		assertEquals("fallenhero@wc3.net", user.getEmail());
	}

	@Test (expected = InputMismatchException.class)
	public void setEmailInvalidInputNoCommercialAt() throws Exception {
		user.setEmail("jeffersonmail.com");
	}

	@Test (expected = InputMismatchException.class)
	public void setEmailInvalidInputInvalidSymbol() throws Exception {
		user.setEmail("**jefferson**@mail.com");
	}

	@Test (expected = InputMismatchException.class)
	public void setEmailInvalidInputRootTooShort() throws Exception {
		user.setEmail("jefferson@mail.i");
	}

	@Test (expected = InputMismatchException.class)
	public void setEmailInvalidInputEmpty() throws Exception {
		user.setEmail("");
	}

	@Test (expected = InputMismatchException.class)
	public void setEmailInvalidInputNull() throws Exception {
		user.setEmail(null);
	}

	@Test
	public void testToString() throws Exception {
		assertEquals(String.format("%-32s%-32s%-32s%-32s%-32s", user.getUsername(), user.getPassword(), user.getName(), user.getAddress(), user.getEmail()), user.toString());
	}

}