package model;

import java.util.ArrayList;
import java.util.InputMismatchException;

/** Represents a shoe list object in the system **/
public class ShoeList {
	private ArrayList<Shoe> shoes = new ArrayList<>();

	/**
	 * Adds a shoe to the ShoeList
	 *
	 * @param shoe
	 *            The shoe to be added
	 **/
	public void add(Shoe shoe) {
		shoes.add(shoe);
	}

	/**
	 * Removes and returns a shoe based on the productId.
	 *
	 * @param productId
	 * @return
	 */
	public Shoe remove(int productId) {
		for (int i = 0; i < shoes.size(); i++) {
			if (shoes.get(i).getProductId() == productId) {
				return shoes.remove(i);
			}
		}
		throw new InputMismatchException();
	}

	/**
	 * Returns a shoe based on the index.
	 *
	 * @param index
	 * @return
	 */
	public Shoe get(int index) {
		return shoes.get(index);
	}

	@Override
	public String toString() {
		StringBuilder shoePrint = new StringBuilder();

		shoePrint.append(String.format("%-15s", "Product id"));
		shoePrint.append(String.format("%-20s", "Brand"));
		shoePrint.append(String.format("%-20s", "Model"));
		shoePrint.append(String.format("%-15s", "Color"));
		shoePrint.append(String.format("%-15s", "Size"));
		shoePrint.append(String.format("%-15s", "Quantity"));
		shoePrint.append(String.format("%-15s", "Price"));
		shoePrint.append(String.format("%-30s", "Description"));
		shoePrint.append("\n");

		for (Shoe shoe : shoes) {
			shoePrint.append(String.format("%-15s", shoe.getProductId()));
			shoePrint.append(String.format("%-20s", shoe.getBrand()));
			shoePrint.append(String.format("%-20s", shoe.getModel()));
			shoePrint.append(String.format("%-15s", shoe.getColor()));
			shoePrint.append(String.format("%-15s", shoe.getSize()));
			shoePrint.append(String.format("%-15s", shoe.getQuantity()));
			shoePrint.append(String.format("%-15s", shoe.getPrice()));
			shoePrint.append(String.format("%-30s", shoe.getDescription()));
			shoePrint.append("\n");
		}

		return shoePrint.toString();
	}

	/**
	 * Returns the number of shoes in this list
	 * 
	 * @return the number of shoes
	 **/
	public int size() {
		return shoes.size();
	}

}
