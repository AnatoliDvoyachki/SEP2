package util;

/** Utility class, used for type conversion **/
public class Parse {

	// cannot be instantiated
	private Parse() {}

	/**
	 * Parses the string argument as a signed decimal integer
	 * 
	 * @param number
	 *            A String containing the int representation to be parsed
	 * @return The integer value represented by the argument in decimal
	 **/
	public static int toInt(String number) {
		return Integer.parseInt(number);
	}

	/**
	 * Returns a new double initialized to the value represented by the
	 * specified String
	 * 
	 * @param number
	 *            The string to be parsed
	 * @return the double value represented by the string argument
	 **/
	public static double toDouble(String number) {
		return Double.parseDouble(number);
	}
}
