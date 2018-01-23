package test;

import model.Shoe;

import org.junit.Before;
import org.junit.Test;

import java.util.InputMismatchException;

import static org.junit.Assert.*;

public class ShoeTest {
	private Shoe shoe;

	@Before
	public void setUp() throws Exception {
		shoe = new Shoe(1, "Nike", "Freerun", "Black", 42.00, 100, 700.00,
				"Best ones");
	}

	@Test
	public void getProductId() throws Exception {
		assertEquals(1, shoe.getProductId());
	}

	@Test
	public void getBrand() throws Exception {
		assertEquals("Nike", shoe.getBrand());
	}

	@Test
	public void getModel() throws Exception {
		assertEquals("Freerun", shoe.getModel());
	}

	@Test
	public void getColor() throws Exception {
		assertEquals("Black", shoe.getColor());
	}

	@Test
	public void getSize() throws Exception {
		assertEquals(42.00, shoe.getSize(), 0.001);
	}

	@Test
	public void getQuantity() throws Exception {
		assertEquals(100, shoe.getQuantity());
	}

	@Test
	public void getPrice() throws Exception {
		assertEquals(700.00, shoe.getPrice(), 0.001);
	}

	@Test
	public void getDescription() throws Exception {
		assertEquals("Best ones", shoe.getDescription());
	}

	@Test
	public void setBrand() throws Exception {
		shoe.setBrand("Adidas");
		assertEquals("Adidas", shoe.getBrand());
	}

	@Test(expected = InputMismatchException.class)
	public void setBrandInvalidInputLength() throws Exception {
		shoe.setBrand("Adidas and Nike and Converse Collaboration Spring Fashion Collection for Fashion Fanatics of the New Year");
	}

	@Test(expected = InputMismatchException.class)
	public void setBrandInvalidInputLength2() throws Exception {
		shoe.setBrand("");
	}

	@Test(expected = InputMismatchException.class)
	public void setBrandInvalidInputNull() throws Exception {
		shoe.setBrand(null);
	}

	@Test
	public void setModel() throws Exception {
		shoe.setModel("Classic");
		assertEquals("Classic", shoe.getModel());
	}

	@Test(expected = InputMismatchException.class)
	public void setModelInvalidInputLength() throws Exception {
		shoe.setModel("Yall Dont Know Bout These Best Goddamn Shoes In The Whole Wide World Never Gonna Give You Up Edition");
	}

	@Test(expected = InputMismatchException.class)
	public void setModelInvalidInputLength2() throws Exception {
		shoe.setModel("");
	}

	@Test(expected = InputMismatchException.class)
	public void setModelInvalidInputNull() throws Exception {
		shoe.setModel(null);
	}

	@Test
	public void setColor() throws Exception {
		shoe.setColor("White");
		assertEquals("White", shoe.getColor());
	}

	@Test(expected = InputMismatchException.class)
	public void setColorInvalidInputLength() throws Exception {
		shoe.setColor("Purbluellowredgreen");
	}

	@Test(expected = InputMismatchException.class)
	public void setColorInvalidInputLength2() throws Exception {
		shoe.setColor("");
	}

	@Test(expected = InputMismatchException.class)
	public void setColorInvalidInputNull() throws Exception {
		shoe.setColor(null);
	}

	@Test
	public void setSize() throws Exception {
		shoe.setSize(43.50);
		assertEquals(43.50, shoe.getSize(), 0.001);
	}

	@Test(expected = InputMismatchException.class)
	public void setSizeInvalidInput() throws Exception {
		shoe.setSize(900000.5);
	}

	@Test(expected = InputMismatchException.class)
	public void setSizeInvalidInput2() throws Exception {
		shoe.setSize(40.55);
	}

	@Test
	public void setQuantity() throws Exception {
		shoe.setQuantity(150);
		assertEquals(150, shoe.getQuantity());
	}

	@Test(expected = InputMismatchException.class)
	public void setQuantityInvalidInput() throws Exception {
		shoe.setQuantity(-50);
	}

	@Test
	public void setPrice() throws Exception {
		shoe.setPrice(800.50);
		assertEquals(800.50, shoe.getPrice(), 0.001);
	}

	@Test(expected = InputMismatchException.class)
	public void setPriceInvalidInput() throws Exception {
		shoe.setPrice(99999999999999.99);
	}

	@Test(expected = InputMismatchException.class)
	public void setPriceInvalidInput2() throws Exception {
		shoe.setPrice(599.105);
	}

	@Test(expected = InputMismatchException.class)
	public void setPriceInvalidInput3() throws Exception {
		shoe.setPrice(-99.99);
	}

	@Test
	public void setDescription() throws Exception {
		shoe.setDescription("Classics");
		assertEquals("Classics", shoe.getDescription());
	}

	@Test
	public void toStringTest() {
		assertEquals(String.format("%-10s%-20s%-20s%-15s%-15s%-15s%-15s%-30s",
				Integer.toString(shoe.getProductId()), shoe.getBrand()+"(1)",
				shoe.getModel()+"(2)", shoe.getColor()+"(3)",
				Double.toString(shoe.getSize())+"(4)",
				Integer.toString(shoe.getQuantity())+"(5)",
				Double.toString(shoe.getPrice())+"(6)", shoe.getDescription()+"(7)"),
				shoe.toString());
	}

}