package util;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

import model.Order;

/** Utility class, used for user input validation **/
public class Validate {

	// cannot be instantiated
	private Validate() {}

	/**
	 * Checks a String input to ensure that it validates as a double.
	 * Use arguments to limit the amount of digits on either side of the decimal.
	 *
	 * @param input               String input to be validated.
	 * @param digitsBeforeDecimal maximum number of digits before the decimal.
	 * @param digitsAfterDecimal  maximum number of digits after the decimal.
	 */
	public static void validateDoubleInput(String input,
			int digitsBeforeDecimal, int digitsAfterDecimal) {
		if (input == null) {
			throw new InputMismatchException("null value");
		}

		Pattern validDoubleRegex = Pattern.compile("^[0-9]{1,"
				+ digitsBeforeDecimal + "}|[0-9]{0," + digitsBeforeDecimal
				+ "}\\.[0-9]{0," + digitsAfterDecimal + "}$");

		if (!validDoubleRegex.matcher(input).matches()) {
			throw new InputMismatchException(
					"number too low, too high or too precise");
		}
	}

	/**
	 * Checks a String input to ensure that it validates as an integer.
	 *
	 * @param input String input to be validated.
	 */
	public static void validateIntegerInput(String input) {
		if (input == null) {
			throw new InputMismatchException("null value");
		}

		Pattern validIntegerRegex = Pattern.compile("^[0-9]+$");

		if (!validIntegerRegex.matcher(input).matches()) {
			throw new InputMismatchException("not a whole number");
		}
	}

	/**
	 * Checks a String input to ensure that it is no longer than the specified
	 * limit and that it is not empty.
	 *
	 * @param input          String input to be validated.
	 * @param characterLimit Maximum number of characters for the input.
	 */
	public static void validateTextualInput(String input, int characterLimit) {
		if (input == null) {
			throw new InputMismatchException("null value");
		}

		Pattern validTextRegex = Pattern.compile(".{1," + characterLimit + "}");

		if (!validTextRegex.matcher(input).matches()) {
			throw new InputMismatchException("invalid text length");
		}
	}

	/**
	 * Checks a String input to ensure that is matches a common email pattern.
	 *
	 * @param input String input to be validated.
	 */
	public static void validateEmailInput(String input) {
		if (input == null) {
			throw new InputMismatchException("null value");
		}

		Pattern validEmailRegex = Pattern.compile(
				"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);

		if (!validEmailRegex.matcher(input).matches()) {
			throw new InputMismatchException("invalid email");
		}
	}

	/**
	 * Validates an input for a column index when editing a shoe listing.
	 *
	 * @param attributeIndex Index of chosen column.
	 */
	public static void validateAttributeNumber(int attributeIndex) {
		if (attributeIndex > 7 || attributeIndex < 1) {
			throw new InputMismatchException();
		}
	}

	/**
	 * Validates an input for a status change of an order.
	 *
	 * @param status Updated status.
	 */
	public static void validateOrderStatus(String status) {
		if (status == null) {
			throw new InputMismatchException("null value");
		} else if (Arrays.asList(Order.STATUS_ALLOWED_VALUES).contains(status) == false) {
			throw new InputMismatchException("invalid status");
		}
	}
}
