package server.view;

import server.controller.*;


public interface ServerView {

	/**
	 * Used to initiate the server view
	 * 
	 * @param controller
	 *            the server controller that controlls the view
	 **/
	void start(ServerController controller);

	/**
	 * Prints the value passed as a parameter to the console
	 * 
	 * @param value
	 *            the value to be printed
	 **/
	void show(String value);

	/**
	 * Used to ask the user for input, and collect the input
	 * 
	 * @param what
	 *            The required input
	 * @return returns the collected input from the user
	 * **/
	String get(String what);
}