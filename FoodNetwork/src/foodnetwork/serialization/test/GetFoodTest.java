/***************************************************
 *
 * Author:      Jonathan Myers & Santiago Andaluz
 * Assignment:  Program 1
 * Class:       CSI 4321
 *
 ***************************************************/
package foodnetwork.serialization.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

import foodnetwork.serialization.FoodMessage;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.GetFood;
import foodnetwork.serialization.MessageOutput;

/**
 * GetFoodTest class using JUnit4 test cases to test the accuracy of package foodnetwork.serialization
 * @author Jonathan Myers and Santiago Andaluz
 * @version 1.1 Build 9/18/2016
 */
public class GetFoodTest {
    @Test
    public void testToStringEqualObjects() throws FoodNetworkException {
        FoodMessage req1 = new GetFood(1234567);
        FoodMessage req2 = new GetFood(1234567);
        
        assertEquals(req1.toString(), req2.toString());
    }

    @Test
    public void testHashCodeEqualObjectsSameCode() throws FoodNetworkException {
        FoodMessage req1 = new GetFood(1234567);
        FoodMessage req2 = new GetFood(1234567);
        
        assertEquals(req1.hashCode(), req2.hashCode());
    }

    @Test
    public void testHashCodeConsistant() throws FoodNetworkException {
        FoodMessage req1 = new GetFood(1234567);
        
        assertEquals(req1.hashCode(), req1.hashCode());
    }

    @Test
    public void testGetFoodConstructorSuccess() throws FoodNetworkException {
        new GetFood(1234567);
    }


    @Test(expected = FoodNetworkException.class)
    public void testGetFoodConstructorInvalidTimestamp() throws FoodNetworkException {
        new GetFood(-1);
    }

    @Test(expected=FoodNetworkException.class)
    public void testGetFoodSetTimestampInvalid() throws FoodNetworkException {
        FoodMessage req = new GetFood(1234567);

        req.setMessageTimestamp(-1);
    }

    @Test
    public void testGetFoodGetTimestamp() throws FoodNetworkException {
        FoodMessage req = new GetFood(1234567);
        
        assertEquals(1234567L, req.getMessageTimestamp());
    }
    
    @Test
    public void testEncode() throws FoodNetworkException, EOFException {
        
		// Create ByteArrayOutputStream to capture the output
		ByteArrayOutputStream b = new ByteArrayOutputStream();
        FoodMessage req = new GetFood(1234567);
		MessageOutput mo = new MessageOutput(b);
		// Encode & test output
		req.encode(mo);
		byte[] bytes = b.toByteArray();
		String s = new String(bytes);
		assertEquals(s, "FN1.0 1234567 GET \n");
    }

	@Test(expected=FoodNetworkException.class)
	public void testGetFoodEncodeFailure() throws FoodNetworkException, IOException{
		// Create FileOutputStream
		OutputStream os = new FileOutputStream("Bad");
		GetFood a = new GetFood(1234567);
		MessageOutput mo = new MessageOutput(os);
		
		// Close the FileOutputStream before writing to it
		os.close();
		a.encode(mo);
	}
	
	@Test
	public void testGetFoodGetRequest() throws FoodNetworkException {
        FoodMessage req = new GetFood(1234567);
        assertEquals("GET", req.getRequest());
	}
}
