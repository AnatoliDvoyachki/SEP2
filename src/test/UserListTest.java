package test;

import model.User;
import model.UserList;

import org.junit.Before;
import org.junit.Test;

import java.util.InputMismatchException;

import static org.junit.Assert.*;

public class UserListTest {
	private UserList userList;

	@Before
	public void setUp() throws Exception {
		userList = new UserList();
	}

	@Test
	public void add() throws Exception {
		User user01 = new User("dandylion", "bestfriend", "John Kiegelson", "Namestreet 10", "johnsroom@gmail.com");
		User user02 = new User("dirk", "jackster1212", "Annie Moore", "Paradise Lane 13", "notlovingyou@mail.com");

		userList.add(user01);
		userList.add(user02);
		assertEquals(user01, userList.get("dandylion"));
		assertEquals(user02, userList.get("dirk"));
	}

	@Test
	public void get() throws Exception {
		User user01 = new User("dandylion", "bestfriend", "John Kiegelson", "Namestreet 10", "johnsroom@gmail.com");

		userList.add(user01);
		assertEquals(user01, userList.get("dandylion"));
	}

	@Test(expected = InputMismatchException.class)
	public void getNonexistingUsername() throws Exception {
		User user01 = new User("dandylion", "bestfriend", "John Kiegelson", "Namestreet 10", "johnsroom@gmail.com");

		userList.add(user01);
		assertEquals(user01, userList.get("americanized"));
	}

	@Test
	public void remove() throws Exception {
		User user01 = new User("dandylion", "bestfriend", "John Kiegelson", "Namestreet 10", "johnsroom@gmail.com");

		userList.add(user01);
		assertEquals(user01, userList.remove("dandylion"));
	}

	@Test(expected = InputMismatchException.class)
	public void removeNonexistingUsername() throws Exception {
		User user01 = new User("dandylion", "bestfriend", "John Kiegelson", "Namestreet 10", "johnsroom@gmail.com");

		userList.add(user01);
		assertEquals(user01, userList.remove("girlscout"));
	}
}