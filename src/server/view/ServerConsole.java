package server.view;

import java.util.Scanner;

import server.controller.*;

/** Server console menu implementation **/
public class ServerConsole implements ServerView {

	private Scanner in;

	/** Used to instantiate a ServerConsole **/
	public ServerConsole() {
		in = new Scanner(System.in);
	}

	@Override
	public void start(ServerController controller) {

		int menuSelection;
		do {
			menuSelection = menu();
			switch (menuSelection) {
			case 1:
				controller.execute(ServerController.EXECUTE_LIST_SHOES);
				break;
			case 2:
				controller.execute(ServerController.EXECUTE_LIST_ORDERS);
				break;
			case 3:
				controller.execute(ServerController.EXECUTE_LIST_CUSTOMERS);
				break;
			case 4:
				controller.execute(ServerController.EXECUTE_ADD_SHOE);
				break;
			case 5:
				controller
						.execute(ServerController.EXECUTE_CHANGE_DELIVERY_STATUS);
				break;
			case 6:
				controller.execute(ServerController.EXECUTE_CHANGE_SHOE_DATA);
				break;
			case 7:
				controller.execute(ServerController.EXECUTE_REMOVE_SHOE);
				break;
			case 8:
				controller.execute(ServerController.EXECUTE_SEARCH_SHOES);
				break;
			case 9:
				controller.execute(ServerController.EXECUTE_SEARCH_ORDERS);
				break;
			case 0:
				controller.execute(ServerController.EXECUTE_QUIT);
				break;
			default:
				show("Invalid input");
				break;
			}
			System.out.println("\nPress ENTER to continue...");
			in.nextLine();
		} while (menuSelection != 0);

	}

	@Override
	public void show(String value) {
		System.out.println(value);

	}

	@Override
	public String get(String what) {

		System.out.print("Please enter " + what + ": ");
		String input = in.nextLine();
		return input;

	}

	private int menu() {
		System.out.println("VIA Shoe");
		System.out.println("--------------");
		System.out.println("1) List all shoes");
		System.out.println("2) List all orders");
		System.out.println("3) List all customers");
		System.out.println("4) Add new shoe");
		System.out.println("5) Change delivery status");
		System.out.println("6) Edit shoe data");
		System.out.println("7) Remove shoe");
		System.out.println("8) Search shoes");
		System.out.println("9) Search orders");
		System.out.println("0) Quit");
		System.out.println();
		System.out.println("Select an item 0-9: ");
		try {
			int selection = in.nextInt();
			in.nextLine();
			return selection;
		} catch (Exception e) {
			in.nextLine();
			return -1;
		}
	}

}
