package model;

import util.Validate;

/** Represents a shoe object in the system **/
public class Shoe {

	public static final int BRAND_CHARACTER_LIMIT = 50;
	public static final int MODEL_CHARACTER_LIMIT = 50;
	public static final int COLOR_CHARACTER_LIMIT = 15;
	public static final int[] SIZE_DECIMAL_LIMIT = { 5, 1 };
	public static final int[] PRICE_DECIMAL_LIMIT = { 8, 2 };

	private int productId;
	private String brand;
	private String model;
	private String color;
	private double size;
	private int quantity;
	private double price;
	private String description;

	/**
	 * Constructor for pulling object from the persistence. Takes all fields of
	 * the class as parameters.
	 *
	 * @param productId
	 * @param brand
	 * @param model
	 * @param color
	 * @param size
	 * @param quantity
	 * @param price
	 * @param description
	 */
	public Shoe(int productId, String brand, String model, String color,
			double size, int quantity, double price, String description) {
		this.productId = productId;
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.size = size;
		this.quantity = quantity;
		this.price = price;
		if (description != null) {
			this.description = description;
		} else {
			this.description = "";
		}
	}

	/**
	 * Constructor for creating object within the application. Includes only
	 * required fields.
	 *
	 * @param brand
	 * @param model
	 * @param color
	 * @param size
	 * @param quantity
	 * @param price
	 */
	public Shoe(String brand, String model, String color, double size,
			int quantity, double price, String description) {
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.size = size;
		this.quantity = quantity;
		this.price = price;
		this.description = description;
	}

	/**
	 * Returns the product id of the shoe
	 * 
	 * @return the product id
	 **/
	public int getProductId() {
		return productId;
	}

	/**
	 * Returns the brand of the shoe
	 * 
	 * @return the brand
	 **/
	public String getBrand() {
		return brand;
	}

	/**
	 * Returns the model of the shoe
	 * 
	 * @return the model
	 **/
	public String getModel() {
		return model;
	}

	/**
	 * Returns the color of the shoe
	 * 
	 * @return the color
	 **/
	public String getColor() {
		return color;
	}

	/**
	 * Returns the size of the shoe
	 * 
	 * @return the size
	 * **/
	public double getSize() {
		return size;
	}

	/**
	 * Returns the quantity shoe
	 * 
	 * @return the quantity
	 **/
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Returns the price of the shoe
	 * 
	 * @return the price
	 **/
	public double getPrice() {
		return price;
	}

	/**
	 * Returns the description of the shoe
	 * 
	 * @return the description
	 **/
	public String getDescription() {
		return description;
	}

	/**
	 * Validates and sets the shoe brand passed as a parameter
	 * 
	 * @param brand
	 *            The shoe brand
	 **/
	public void setBrand(String brand) {
		Validate.validateTextualInput(brand, BRAND_CHARACTER_LIMIT);
		this.brand = brand;
	}

	/**
	 * Validates and sets the shoe model passed as a parameter
	 * 
	 * @param model
	 *            The shoe model
	 **/
	public void setModel(String model) {
		Validate.validateTextualInput(model, MODEL_CHARACTER_LIMIT);
		this.model = model;
	}

	/**
	 * Validates and sets the shoe color passed as a parameter
	 * 
	 * @param color
	 *            The shoe color
	 **/
	public void setColor(String color) {
		Validate.validateTextualInput(color, COLOR_CHARACTER_LIMIT);
		this.color = color;
	}

	/**
	 * Validates and sets the shoe size passed as a parameter
	 * 
	 * @param size
	 *            The shoe size
	 **/
	public void setSize(double size) {
		Validate.validateDoubleInput(Double.toString(size),
				SIZE_DECIMAL_LIMIT[0], SIZE_DECIMAL_LIMIT[1]);
		this.size = size;
	}

	/**
	 * Validates and sets the shoe quantity passed as a parameter
	 * 
	 * @param quantity
	 *            The shoe quantity
	 **/
	public void setQuantity(int quantity) {
		Validate.validateIntegerInput(Integer.toString(quantity));
		this.quantity = quantity;
	}

	/**
	 * Validates and sets the shoe price passed as a parameter
	 * 
	 * @param price
	 *            The shoe price
	 **/
	public void setPrice(double price) {
		Validate.validateDoubleInput(Double.toString(price),
				PRICE_DECIMAL_LIMIT[0], PRICE_DECIMAL_LIMIT[1]);
		this.price = price;
	}

	/**
	 * Sets the shoe description, passed as a parameter
	 * 
	 * @param description
	 *            the shoe description to be set
	 **/
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder shoePrint = new StringBuilder();

		shoePrint.append(String.format("%-10s", productId));
		shoePrint.append(String.format("%-20s", brand + "(1)"));
		shoePrint.append(String.format("%-20s", model + "(2)"));
		shoePrint.append(String.format("%-15s", color + "(3)"));
		shoePrint.append(String.format("%-15s", size + "(4)"));
		shoePrint.append(String.format("%-15s", quantity + "(5)"));
		shoePrint.append(String.format("%-15s", price + "(6)"));
		shoePrint.append(String.format("%-30s", description + "(7)"));

		return shoePrint.toString();
	}

	/**
	 * This method is used to format the data of the object into a protocol
	 * message
	 **/
	public Object toStringRow() {
		return productId + ":" + brand + ":" + model + ":" + color + ":" + size
				+ ":" + quantity + ":" + price + ":" + description;
	}

}
