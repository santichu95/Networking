/***************************************************
 *
 * Author:      Jonathan Myers & Santiago Andaluz
 * Assignment:  Program 1
 * Class:       CSI 4321
 *
 ***************************************************/

package foodnetwork.serialization.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import foodnetwork.serialization.FoodItem;
import foodnetwork.serialization.FoodList;
import foodnetwork.serialization.FoodMessage;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.MealType;
import foodnetwork.serialization.MessageOutput;

/**
 * FoodListTest class using JUnit4 test cases to test the accuracy of package foodnetwork.serialization
 * @author Jonathan Myers & Santiago Andaluz
 * @version 1.2 Build 9/20/2016
 */
public class FoodListTest {
    @Test(expected=FoodNetworkException.class)
    public void testAddFoodItemNull() throws FoodNetworkException {
        FoodMessage req1 = new FoodList(1234567, 1234587);
        ((FoodList)req1).addFoodItem(null);
    }
	
    @Test(expected=FoodNetworkException.class)
    public void testModTimeLessThanTimestamp() throws FoodNetworkException {
        FoodMessage req1 = new FoodList(1234567, 1234587);
        ((FoodList)req1).setModifiedTimestamp(12345);
        
    }

	@Test
    public void testToStringEqualObjects() throws FoodNetworkException {
        FoodMessage req1 = new FoodList(1234567, 1234587);
        FoodMessage req2 = new FoodList(1234567, 1234587);
        assertEquals(req1.toString(), req2.toString());
    }

    @Test
    public void testHashCodeEqualObjectsSameCode() throws FoodNetworkException {
    	FoodMessage req1 = new FoodList(1234567, 1234587);
        FoodMessage req2 = new FoodList(1234567, 1234587);
        
        assertEquals(req1.hashCode(), req2.hashCode());
    }

    @Test
    public void testHashCodeConsistant() throws FoodNetworkException {
        FoodMessage req1 = new FoodList(1234567, 1234587);
        
        assertEquals(req1.hashCode(), req1.hashCode());
    }

    @Test
    public void testFoodListConstructorSuccess() throws FoodNetworkException {
        new FoodList(1234567, 1234587);
    }


    @Test(expected = FoodNetworkException.class)
    public void testFoodListConstructorInvalidTimestamp() throws FoodNetworkException {
        new FoodList(-1, 1234587);
    }
    
    @Test(expected = FoodNetworkException.class)
    public void testFoodListConstructorInvalidModifiedTimestamp() throws FoodNetworkException {
        new FoodList(1234567, -1);
    }

    @Test(expected=FoodNetworkException.class)
    public void testFoodListSetTimestampInvalid() throws FoodNetworkException {
        FoodMessage req = new FoodList(1234567, 1234587);

        req.setMessageTimestamp(-1);
    }
    
    @Test(expected=FoodNetworkException.class)
    public void testFoodListSetModifiedTimestampInvalid() throws FoodNetworkException {
        FoodMessage req = new FoodList(1234567, 1234587);

        ((FoodList)req).setModifiedTimestamp(-1);
    }

    @Test
    public void testFoodListGetTimestamp() throws FoodNetworkException {
        FoodMessage req = new FoodList(1234567, 1234587);
        
        assertEquals(1234567L, req.getMessageTimestamp());
    }
    
    @Test
    public void testFoodListGetModifiedTimestamp() throws FoodNetworkException {
        FoodMessage req = new FoodList(1234567, 1234587);
        
        assertEquals(1234587L, ((FoodList)req).getModifiedTimestamp());
    }
    
    @Test
    public void testFoodListEncode() throws FoodNetworkException, EOFException {
        
		// Create ByteArrayOutputStream to capture the output
		ByteArrayOutputStream b = new ByteArrayOutputStream();
        FoodMessage req = new FoodList(1234567, 1234587);
        ((FoodList)req).addFoodItem(new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        ((FoodList)req).addFoodItem(new FoodItem("Plum", MealType.Breakfast, 75L, "3.8"));
		MessageOutput mo = new MessageOutput(b);
		// Encode & test output
		req.encode(mo);
		byte[] bytes = b.toByteArray();
		String s = new String(bytes);
		assertEquals(s, "FN1.0 1234567 LIST 1234587 2 5 PeachS50 1.2 4 PlumB75 3.8 \n");
    }

	@Test(expected=FoodNetworkException.class)
	public void testFoodListEncodeFailure() throws FoodNetworkException, IOException{
		// Create FileOutputStream
		OutputStream os = new FileOutputStream("Bad");
		FoodMessage a = new FoodList(1234567, 1234587);
		MessageOutput mo = new MessageOutput(os);
		
		// Close the FileOutputStream before writing to it
		os.close();
		a.encode(mo);
	}
	
	@Test
	public void testFoodListGetRequest() throws FoodNetworkException {
        FoodMessage req = new FoodList(1234567, 1234587);
        assertEquals("LIST", req.getRequest());
	}
	
	@Test
	public void testGetFoodItemList() throws FoodNetworkException {
		FoodMessage req = new FoodList(1234567, 1234587);
		FoodItem f1 = new FoodItem("Peach", MealType.Snack, 50L, "1.2");
		FoodItem f2 = new FoodItem("Plum", MealType.Snack, 50L, "1.2");
		((FoodList)req).addFoodItem(f1);
		((FoodList)req).addFoodItem(f2);
		List<FoodItem> foodItemList = new ArrayList<FoodItem>();
		foodItemList.add(f1);
		foodItemList.add(f2);
		assertEquals(foodItemList,((FoodList)req).getFoodItemList());
	}
	
}
