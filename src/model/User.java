package model;

import util.Validate;

/** Represents a user in the system **/
public class User {

	public static final int USERNAME_CHARACTER_LIMIT = 30;
	public static final int PASSWORD_CHARACTER_LIMIT = 30;
	public static final int FULLNAME_CHARACTER_LIMIT = 70;
	public static final int ADDRESS_CHARACTER_LIMIT = 245;

	private String username;
	private String password;
	private String name;
	private String address;
	private String email;

	/**
	 * Constructor both for creating users in the application and pulling object
	 * from the persistence.
	 *
	 * @param username
	 * @param password
	 * @param name
	 * @param address
	 * @param email
	 */
	public User(String username, String password, String name, String address,
			String email) {
		setUsername(username);
		setPassword(password);
		setName(name);
		setAddress(address);
		setEmail(email);
	}

	/***
	 * Returns the username of the user
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/***
	 * Returns the password of the user
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/***
	 * Returns the name of the user
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/***
	 * Returns the address of the user
	 * 
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/***
	 * Returns the email of the user
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Validates and sets the username passed as a parameter
	 *
	 * @param username
	 *            The username
	 **/
	public void setUsername(String username) {
		Validate.validateTextualInput(username, USERNAME_CHARACTER_LIMIT);
		this.username = username;
	}

	/**
	 * Validates and sets the user password passed as a parameter
	 *
	 * @param password
	 *            The user password
	 **/
	public void setPassword(String password) {
		Validate.validateTextualInput(password, PASSWORD_CHARACTER_LIMIT);
		this.password = password;
	}

	/**
	 * Validates and sets the real name passed as a parameter
	 *
	 * @param name
	 *            The real name of the user
	 **/
	public void setName(String name) {
		Validate.validateTextualInput(name, FULLNAME_CHARACTER_LIMIT);
		this.name = name;
	}

	/**
	 * Validates and sets the user address passed as a parameter
	 *
	 * @param address
	 *            The user address
	 **/
	public void setAddress(String address) {
		Validate.validateTextualInput(address, ADDRESS_CHARACTER_LIMIT);
		this.address = address;
	}

	/**
	 * Validates and sets the user email passed as a parameter
	 *
	 * @param email
	 *            The user email
	 **/
	public void setEmail(String email) {
		Validate.validateEmailInput(email);
		this.email = email;
	}

	@Override
	public String toString() {
		StringBuilder userPrint = new StringBuilder();
		userPrint.append(String.format("%-32s", username));
		userPrint.append(String.format("%-32s", password));
		userPrint.append(String.format("%-32s", name));
		userPrint.append(String.format("%-32s", address));
		userPrint.append(String.format("%-32s", email));
		return userPrint.toString();
	}
}
