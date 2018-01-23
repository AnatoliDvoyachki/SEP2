package model;

import java.util.ArrayList;
import java.util.InputMismatchException;

/** Represents a user list object in the system **/
public class UserList {
	private ArrayList<User> users = new ArrayList<>();

	/**
	 * Adds a user to the user list
	 * 
	 * @param user
	 *            the user to be added
	 * */
	public void add(User user) {
		users.add(user);
	}

	/**
	 * Returns a user based on the username
	 *
	 * @param username
	 * @return User
	 */
	public User get(String username) {
		for (int i = 0; i < users.size(); i++) {
			if (username.equals(users.get(i).getUsername())) {
				return users.get(i);
			}
		}
		throw new InputMismatchException();
	}

	/**
	 * Removes and returns a user based on the username.
	 *
	 * @param username
	 * @return
	 */
	public User remove(String username) {
		for (int i = 0; i < users.size(); i++) {
			if (username.equals(users.get(i).getUsername())) {
				return users.remove(i);
			}
		}
		throw new InputMismatchException();
	}

	@Override
	public String toString() {
		StringBuilder userPrint = new StringBuilder();

		userPrint.append(String.format("%-32s", "Username"));
		userPrint.append(String.format("%-32s", "Password"));
		userPrint.append(String.format("%-32s", "Name"));
		userPrint.append(String.format("%-32s", "Address"));
		userPrint.append(String.format("%-32s", "Email"));
		userPrint.append("\n");

		for (User user : users) {
			userPrint.append(String.format("%-32s", user.getUsername()));
			userPrint.append(String.format("%-32s", user.getPassword()));
			userPrint.append(String.format("%-32s", user.getName()));
			userPrint.append(String.format("%-32s", user.getAddress()));
			userPrint.append(String.format("%-32s", user.getEmail()));
			userPrint.append("\n");
		}

		return userPrint.toString();
	}

}
