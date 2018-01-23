package test;

import model.Shoe;
import model.ShoeList;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShoeListTest {
   private ShoeList shoeList;

   @Before
   public void setUp() throws Exception {
      shoeList = new ShoeList();
   }

   @Test
   public void add() throws Exception {
      Shoe shoe01 = new Shoe(1, "Nike", "Freerun", "Black",
            42.00, 100, 700.00, "Best ones");
      Shoe shoe02 = new Shoe(2, "Adidas", "Classic", "White",
            43.00, 200, 500.00, "The old ones");

      shoeList.add(shoe01);
      shoeList.add(shoe02);
      assertEquals(shoe01, shoeList.get(0));
      assertEquals(shoe02, shoeList.get(1));
   }

   @Test
   public void get() throws Exception {
      Shoe shoe01 = new Shoe(1, "Nike", "Freerun", "Black",
            42.00, 100, 700.00, "Best ones");
      Shoe shoe02 = new Shoe(2, "Adidas", "Classic", "White",
            43.00, 200, 500.00, "The old ones");

      shoeList.add(shoe01);
      shoeList.add(shoe02);
      assertEquals(shoe01, shoeList.get(0));
      assertEquals(shoe02, shoeList.get(1));
   }
   
   @Test
   public void remove() throws Exception {
      Shoe shoe01 = new Shoe(1, "Nike", "Freerun", "Black",
            42.00, 100, 700.00, "Best ones");
      Shoe shoe02 = new Shoe(2, "Adidas", "Classic", "White",
            43.00, 200, 500.00, "The old ones");

      shoeList.add(shoe01);
      shoeList.add(shoe02);
      assertEquals(shoe01, shoeList.remove(1));
      assertEquals(shoe02, shoeList.remove(2));
   }

}
