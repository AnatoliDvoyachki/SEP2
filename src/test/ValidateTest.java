package test;

import org.junit.Test;
import util.Validate;

import java.util.InputMismatchException;

public class ValidateTest {

	@Test
	public void validateAttributeNumber() throws Exception {
		Validate.validateAttributeNumber(1);
		Validate.validateAttributeNumber(2);
		Validate.validateAttributeNumber(6);
		Validate.validateAttributeNumber(7);
	}

	@Test (expected = InputMismatchException.class)
	public void validateAttributeNumberTooLow() throws Exception {
		Validate.validateAttributeNumber(0);
	}

	@Test (expected = InputMismatchException.class)
	public void validateAttributeNumberTooHigh() throws Exception {
		Validate.validateAttributeNumber(8);
	}

	@Test
	public void validateDoubleInput() throws Exception {
		Validate.validateDoubleInput("30.0", 2, 1);
		Validate.validateDoubleInput("6999.99", 5, 2);
		Validate.validateDoubleInput("999999.999999", 10, 10);
		Validate.validateDoubleInput("1.954321", 1, 6);
		Validate.validateDoubleInput(".8", 1, 1);
		Validate.validateDoubleInput("55", 2, 1);
	}

	@Test (expected = InputMismatchException.class)
	public void validateDoubleInputEmpty() throws Exception {
		Validate.validateDoubleInput("", 5, 5);
	}

	@Test (expected = InputMismatchException.class)
	public void validateDoubleInputNull() throws Exception {
		Validate.validateDoubleInput(null, 5, 5);
	}

	@Test (expected = InputMismatchException.class)
	public void validateDoubleInputNotADouble() throws Exception {
		Validate.validateDoubleInput("fe44.3", 5, 5);
	}

	@Test (expected = InputMismatchException.class)
	public void validateDoubleInputTooLongAfterDecimal() throws Exception {
		Validate.validateDoubleInput("44.55", 5, 1);
	}

	@Test (expected = InputMismatchException.class)
	public void validateDoubleInputTooLongBeforeDecimal() throws Exception {
		Validate.validateDoubleInput("9999999999.99", 8, 2);
	}

	@Test (expected = InputMismatchException.class)
	public void validateDoubleInputNoDecimalTooLongBeforeDecimal() throws Exception {
		Validate.validateDoubleInput("999", 2, 4);
	}

	@Test
	public void validateIntegerInput() throws Exception {
		Validate.validateIntegerInput("0");
		Validate.validateIntegerInput("1");
		Validate.validateIntegerInput("50");
		Validate.validateIntegerInput("9999999");
		Validate.validateIntegerInput("10000000");
	}

	@Test (expected = InputMismatchException.class)
	public void validateIntegerInputEmpty() throws Exception {
		Validate.validateIntegerInput("");
	}

	@Test (expected = InputMismatchException.class)
	public void validateIntegerInputNull() throws Exception {
		Validate.validateIntegerInput(null);
	}

	@Test (expected = InputMismatchException.class)
	public void validateIntegerInputNotAnInteger() throws Exception {
		Validate.validateIntegerInput("f6");
	}

	@Test
	public void validateTextualInput() throws Exception {
		Validate.validateTextualInput("Your name here", 245);
		Validate.validateTextualInput("Guy Manuel McLongest Namerino In'da World Abdul Jahmal Ahmed Assad Fajid Iversson", 245);
		Validate.validateTextualInput("Enter description", 20);
		Validate.validateTextualInput("abcdefghijklmnopqrstuvwxyz 0123456789 +-*/!#¤%&=?@£$€(){}[]_.,;:^|'\"", 200);
	}

	@Test (expected = InputMismatchException.class)
	public void validateTextualInputEmpty() throws Exception {
		Validate.validateTextualInput("", 5);
	}

	@Test (expected = InputMismatchException.class)
	public void validateTextualInputNull() throws Exception {
		Validate.validateTextualInput(null, 5);
	}

	@Test (expected = InputMismatchException.class)
	public void validateTextualInputTooLong() throws Exception {
		Validate.validateTextualInput("Push it to the limit", 5);
	}

	@Test
	public void validateEmailInput() throws Exception {
		Validate.validateEmailInput("jefferson@mail.com");
		Validate.validateEmailInput("eric_sutherland@via.dk");
		Validate.validateEmailInput("99923231@sweetpeaparadise.condor");
		Validate.validateEmailInput("%mif_tuk.two+one-you@yeaha.net");
	}

	@Test (expected = InputMismatchException.class)
	public void validateEmailInputNoCommercialAt() throws Exception {
		Validate.validateEmailInput("jeffersonmail.com");
	}

	@Test (expected = InputMismatchException.class)
	public void validateEmailInputInvalidSymbol() throws Exception {
		Validate.validateEmailInput("**jefferson**@mail.com");
	}

	@Test (expected = InputMismatchException.class)
	public void validateEmailInputRootTooShort() throws Exception {
		Validate.validateEmailInput("jefferson@mail.i");
	}

	@Test (expected = InputMismatchException.class)
	public void validateEmailInputEmptyBeforeCommercialAt() throws Exception {
		Validate.validateEmailInput("@mail.com");
	}

	@Test (expected = InputMismatchException.class)
	public void validateEmailInputNoDomain() throws Exception {
		Validate.validateEmailInput("jefferson@.com");
	}

	@Test (expected = InputMismatchException.class)
	public void validateEmailInputNoDot() throws Exception {
		Validate.validateEmailInput("jefferson@mailcom");
	}

	@Test (expected = InputMismatchException.class)
	public void validateEmailInputEmpty() throws Exception {
		Validate.validateEmailInput("");
	}

	@Test (expected = InputMismatchException.class)
	public void validateEmailInputNull() throws Exception {
		Validate.validateEmailInput(null);
	}
}