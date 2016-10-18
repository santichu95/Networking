/***************************************************
 *
 * Author:      Santiago Andaluz
 * Assignment:  Program 3
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

import foodnetwork.serialization.Interval;
import foodnetwork.serialization.FoodMessage;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.MessageOutput;

public class IntervalTest {

    @Test
    public void testToStringEqualObjects() throws FoodNetworkException {
        FoodMessage req1 = new Interval(1234567, 100);
        FoodMessage req2 = new Interval(1234567, 100);
        
        assertEquals(req1.toString(), req2.toString());
    }

    @Test
    public void testHashCodeEqualObjectsSameCode() throws FoodNetworkException {
        FoodMessage req1 = new Interval(1234567, 100);
        FoodMessage req2 = new Interval(1234567, 100);
        
        assertEquals(req1.hashCode(), req2.hashCode());
    }

    @Test
    public void testHashCodeConsistant() throws FoodNetworkException {
        FoodMessage req1 = new Interval(1234567, 100);
        
        assertEquals(req1.hashCode(), req1.hashCode());
    }

    @Test
    public void testIntervalConstructorSuccess() throws FoodNetworkException {
        new Interval(1234567, 100);
    }


    @Test(expected = FoodNetworkException.class)
    public void testIntervalConstructorInvalidTimestamp() throws FoodNetworkException {
        new Interval(-1, 100);
    }

    @Test(expected=FoodNetworkException.class)
    public void testIntervalSetTimestampInvalid() throws FoodNetworkException {
        FoodMessage req = new Interval(1234567, 100);

        req.setMessageTimestamp(-1);
    }

    @Test
    public void testIntervalGetTimestamp() throws FoodNetworkException {
        FoodMessage req = new Interval(1234567, 100);
        
        assertEquals(1234567L, req.getMessageTimestamp());
    }
    
    @Test
    public void testEncode() throws FoodNetworkException, EOFException {
        
		// Create ByteArrayOutputStream to capture the output
		ByteArrayOutputStream b = new ByteArrayOutputStream();
        FoodMessage req = new Interval(1234567, 100);
		MessageOutput mo = new MessageOutput(b);
		// Encode & test output
		req.encode(mo);
		byte[] bytes = b.toByteArray();
		String s = new String(bytes);
		assertEquals(s, "FN1.0 1234567 INTERVAL 100 \n");
    }

	@Test(expected=FoodNetworkException.class)
	public void testIntervalEncodeFailure() throws FoodNetworkException, IOException{
		// Create FileOutputStream
		OutputStream os = new FileOutputStream("Bad");
		Interval a = new Interval(1234567,100);
		MessageOutput mo = new MessageOutput(os);
		
		// Close the FileOutputStream before writing to it
		os.close();
		a.encode(mo);
	}
	
	@Test
	public void testIntervalGetRequest() throws FoodNetworkException {
        FoodMessage req = new Interval(1234567,100);
        assertEquals("INTERVAL", req.getRequest());
	}
	
	/**
	 * Tests Interval.equals()
	 * @throws FoodNetworkException if foodItem validation fails
	 */
	@Test
	public void testIntervalEqualsFailure() throws FoodNetworkException {
		assertNotEquals(new Interval(1234567,100),
				new Interval(1234567,102));
	}
	
	/**
	 * Tests Interval.equals()
	 * @throws FoodNetworkException if foodItem validation fails
	 */
	@Test
	public void testIntervalEqualsSuccess() throws FoodNetworkException {
		assertEquals(new Interval(1234567,100),
				new Interval(1234567,100));
	}
	
	@Test(expected=FoodNetworkException.class)
	public void testIntervalSetIntervalTime() throws FoodNetworkException {
		Interval req = new Interval(1234567,100);
		req.setIntervalTime(-1);
	}
	
	
	@Test
	public void testIntervalSetIntervalTime2() throws FoodNetworkException {
		Interval req = new Interval(1234567,100);
		req.setIntervalTime(100000);
	}
}
