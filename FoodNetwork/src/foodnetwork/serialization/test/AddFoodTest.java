/***************************************************
 *
 * Author:      Jonathan Myers & Santiago Andaluz
 * Assignment:  Program 1
 * Class:       CSI 4321
 *
 ***************************************************/

package foodnetwork.serialization.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

import foodnetwork.serialization.AddFood;
import foodnetwork.serialization.FoodItem;
import foodnetwork.serialization.FoodMessage;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.GetFood;
import foodnetwork.serialization.MealType;
import foodnetwork.serialization.MessageOutput;

/**
 * AddFoodTest class using JUnit4 test cases to test the accuracy of package foodnetwork.serialization
 * @author Jonathan Myers & Santiago Andaluz
 * @version 1.2 Build 9/18/2016
 */
public class AddFoodTest {
    @Test
    public void testToStringEqualObjects() throws FoodNetworkException {
        AddFood test1 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        AddFood test2 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        
        String one = test1.toString(), two = test2.toString();
        
        assertEquals(one,two);
    }
    
    @Test
    public void testEqualsFoodMessageAndAddFood() throws FoodNetworkException {
        FoodMessage test1 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        AddFood test2 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        
        assertEquals(test1, test2);
    }

    @Test
    public void testEqualsDifferentObjects() throws FoodNetworkException {
        GetFood test1 = new GetFood(1234567);
        AddFood test2 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        
        assertFalse(test2.equals(test1));
    }
    
    
    @Test
    public void testEqualsSameObject() throws FoodNetworkException{
        AddFood test = new AddFood(System.currentTimeMillis(), new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        assertEquals(test,test);
    }
    
    @Test
    public void testHashCodeConsistant() throws FoodNetworkException {
        AddFood test = new AddFood(System.currentTimeMillis(), new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        int hash1 = test.hashCode(), hash2 = test.hashCode();
        
        assertEquals(hash1, hash2);
    }
    
    @Test
    public void testHashCodeEqualObjectsSameNumber() throws FoodNetworkException {
        AddFood test1 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        AddFood test2 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));

        int hash1 = test1.hashCode();
        int hash2 = test2.hashCode();
        
        assertEquals(hash1,hash2);
    }
    
    @Test
    public void testEqualsFoodItemDiff() throws FoodNetworkException {
        AddFood test1 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        AddFood test2 = new AddFood(1234567, new FoodItem("Apple", MealType.Snack, 50L, "1.2"));
        
        assertNotEquals(test1,test2);
    }

    @Test
    public void testEqualsSymmertric() throws FoodNetworkException {
        AddFood test1 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        AddFood test2 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        
        assertEquals(test1,test2);
        assertEquals(test2,test1);
    }

    @Test
    public void testEqualsReflexsive() throws FoodNetworkException {
        AddFood test1 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        
        assertEquals(test1,test1);
        assertEquals(test1,test1);
    }

    @Test
    public void testEqualsNull() throws FoodNetworkException {
        AddFood test1 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        
        assertFalse(test1.equals(null));
    }

    /**
	 * Tests AddFood(Long timeStamp, FoodItem foodItem)
	 * @throws FoodNetworkException if timeStamp is invalid
	 */
	@Test(expected=FoodNetworkException.class)
	public void testAddFoodInvalidTimestamp() throws FoodNetworkException {
		new AddFood(-1, new FoodItem("Peach", MealType.Snack, 50L, "3.8"));
	}
	
	/**
	 * Tests AddFood(Long timeStamp, FoodItem foodItem)
	 * @throws FoodNetworkException if FoodItem is null
	 */
	@Test(expected=FoodNetworkException.class)
	public void testAddFoodNullFoodItem() throws FoodNetworkException {
		new AddFood(1234567, null);
	}
	
	/**
	 * Tests AddFood(Long timeStamp, FoodItem foodItem)
	 * @throws FoodNetworkException if any parameters are invalid
	 */
	@Test
	public void testAddFoodConstructorSuccess() throws FoodNetworkException {
		new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "3.8"));
	}
	
	/**
	 * Tests AddFood.encode(MessageOutput out)
	 * @throws FoodNetworkException if serialization output fails
	 */
	@Test(expected=FoodNetworkException.class)
	public void testAddFoodEncodeFails() throws FoodNetworkException, IOException {
		// Create FileOutputStream
		OutputStream os = new FileOutputStream("Bad");
		AddFood a = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "3.8"));
		MessageOutput mo = new MessageOutput(os);
		
		// Close the FileOutputStream before writing to it
		os.close();
		a.encode(mo);
	}
	
	/**
	 * Tests AddFood.encode(MessageOutput out)
	 * @throws FoodNetworkException if serialization output fails
	 */
	@Test
	public void testAddFoodEncodePasses() throws FoodNetworkException, IOException {
		// Create ByteArrayOutputStream to capture the output
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		AddFood a = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "3.8"));
		MessageOutput mo = new MessageOutput(b);
		// Encode & test output
		a.encode(mo);
		byte[] bytes = b.toByteArray();
		String s = new String(bytes);
		assertEquals(s, "FN1.0 1234567 ADD 5 PeachS50 3.8 \n");
	}
	
	/**
	 * Tests AddFood.setFoodItem(FoodItem foodItem)
	 * @throws FoodNetworkException if foodItem validation fails
	 */
	@Test(expected=FoodNetworkException.class)
	public void testAddFoodSetFoodItemFail() throws FoodNetworkException {
		AddFood a = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "3.8"));
		a.setFoodItem(null);
	}
	
	/**
	 * Tests AddFood.setFoodItem(FoodItem foodItem)
	 * @throws FoodNetworkException if foodItem validation fails
	 */
	@Test
	public void testAddFoodSetFoodItemSuccess() throws FoodNetworkException {
		AddFood a = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "3.8"));
		a.setFoodItem(new FoodItem("Apple", MealType.Breakfast, 100L, "1.0"));
	}
	
	/**
	 * Tests AddFood.getRequest()
	 * @throws FoodNetworkException if foodItem validation fails
	 */
	@Test
	public void testAddFoodGetRequestSuccess() throws FoodNetworkException {
		assertEquals(new AddFood(1234567, 
				new FoodItem("Peach", MealType.Snack, 50L, "3.8")).getRequest(), "ADD");
	}
	
	/**
	 * Tests AddFood.equals()
	 * @throws FoodNetworkException if foodItem validation fails
	 */
	@Test
	public void testAddFoodEqualsFailure() throws FoodNetworkException {
		assertNotEquals(new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "3.8")),
				new AddFood(1234566, new FoodItem("Peach", MealType.Snack, 50L, "3.8")));
	}
	
	/**
	 * Tests AddFood.equals()
	 * @throws FoodNetworkException if foodItem validation fails
	 */
	@Test
	public void testAddFoodEqualsSuccess() throws FoodNetworkException {
		assertEquals(new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "3.8")),
				new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "3.8")));
	}
	
	/**
	 * Tests AddFood.getMessageTimestamp
	 * @throws FoodNetworkException if foodItem validation fails
	 */
	@Test
	public void testAddFoodMessageTimestampGetter() throws FoodNetworkException {
		assertEquals(new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "3.8")).getMessageTimestamp(),
				1234567);
	}

	/**
	 * Tests AddFood.getFoodItem()
	 * @throws FoodNetworkException if foodItem validation fails
	 */
	@Test
	public void testAddFoodFoodItemGetter() throws FoodNetworkException {
		assertEquals(new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "3.8")).getFoodItem(),
				new FoodItem("Peach", MealType.Snack, 50L, "3.8"));
	}
}
