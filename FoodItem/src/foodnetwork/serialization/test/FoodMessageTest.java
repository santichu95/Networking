/***************************************************
*
* Author:      Jonathan Myers & Santiago Andaluz
* Assignment:  Program 1
* Class:       CSI 4321
*
***************************************************/
package foodnetwork.serialization.test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.junit.Test;

import foodnetwork.serialization.AddFood;
import foodnetwork.serialization.ErrorMessage;
import foodnetwork.serialization.FoodItem;
import foodnetwork.serialization.FoodList;
import foodnetwork.serialization.FoodMessage;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.GetFood;
import foodnetwork.serialization.MealType;
import foodnetwork.serialization.MessageInput;

/**
 * FoodMessageTest class using JUnit4 test cases to test the accuracy of package foodnetwork.serialization
 * @author Jonathan Myers & Santiago Andaluz
 * @version 1.1 Build 9/20/2016
 */
public class FoodMessageTest {
    @Test
    public void testEqualsDiffObjects() throws IOException, FoodNetworkException {
        PipedInputStream inPipe = new PipedInputStream();
        PipedOutputStream outPipe = new PipedOutputStream(inPipe);
        
        outPipe.write("FN1.0 1234567 GET \n".getBytes());
        MessageInput in = new MessageInput(inPipe);
        
        FoodMessage req1 = FoodMessage.decode(in);
        Integer number = 4321;
        
        assertNotEquals(req1,number);
        
        outPipe.close();
        
    }

    @Test(expected=FoodNetworkException.class)
    public void testDecodeWrongEndline() throws IOException, FoodNetworkException {
        PipedInputStream inPipe = new PipedInputStream();
        PipedOutputStream outPipe = new PipedOutputStream(inPipe);
        
        outPipe.write("FN1.0 1234567 GET \t".getBytes());
        MessageInput in = new MessageInput(inPipe);
        
        FoodMessage.decode(in);
        
        outPipe.close();
        inPipe.close();
    }

    @Test(expected=FoodNetworkException.class)
    public void testWrongVersionNumber() throws IOException, FoodNetworkException {
        PipedInputStream inPipe = new PipedInputStream();
        PipedOutputStream outPipe = new PipedOutputStream(inPipe);
        
        outPipe.write("FN1.2 1234567 GET \n".getBytes());
        MessageInput in = new MessageInput(inPipe);
        
        FoodMessage.decode(in);
        
        outPipe.close();
        inPipe.close();
    }

    @Test(expected=FoodNetworkException.class)
    public void testDecodeWrongRequest() throws IOException, FoodNetworkException {
        PipedInputStream inPipe = new PipedInputStream();
        PipedOutputStream outPipe = new PipedOutputStream(inPipe);
        
        outPipe.write("FN1.0 1234567 REMOVE 1 \n".getBytes());
        MessageInput in = new MessageInput(inPipe);
        
        FoodMessage.decode(in);
        
        outPipe.close();
        inPipe.close();
    }

    @Test
    public void testDecodeErrorSuccess() throws IOException, FoodNetworkException {
        PipedInputStream inPipe = new PipedInputStream();
        PipedOutputStream outPipe = new PipedOutputStream(inPipe);
        
        outPipe.write("FN1.0 1234567 ERROR 16 Invalid FoodItem\n".getBytes());
        MessageInput in = new MessageInput(inPipe);
        
        FoodMessage req = FoodMessage.decode(in);
        
        assertEquals(ErrorMessage.class, req.getClass());
       
        inPipe.close();
        outPipe.close();
    }

    @Test
    public void testDecodeListSuccess() throws IOException, FoodNetworkException {
        PipedInputStream inPipe = new PipedInputStream();
        PipedOutputStream outPipe = new PipedOutputStream(inPipe);
        
        outPipe.write("FN1.0 1234567 LIST 12345678 2 5 PeachS50 1.2 4 PlumB75 3.8 \n".getBytes());
        MessageInput in = new MessageInput(inPipe);
        
        FoodMessage req = FoodMessage.decode(in);
        
        assertEquals(FoodList.class, req.getClass());
       
        inPipe.close();
        outPipe.close();
    }

    @Test
    public void testDecodeGetSuccess() throws IOException, FoodNetworkException {
        PipedInputStream inPipe = new PipedInputStream();
        PipedOutputStream outPipe = new PipedOutputStream(inPipe);
        
        outPipe.write("FN1.0 1234567 GET \n".getBytes());
        MessageInput in = new MessageInput(inPipe);
        
        FoodMessage req = FoodMessage.decode(in);
        
        assertEquals(GetFood.class, req.getClass());
       
        inPipe.close();
        outPipe.close();
    }
    
    @Test
    public void testDecodeAddSuccess() throws IOException, FoodNetworkException {
        PipedInputStream inPipe = new PipedInputStream();
        PipedOutputStream outPipe = new PipedOutputStream(inPipe);
        
        outPipe.write("FN1.0 1234567 ADD 5 PeachS50 1.2 \n".getBytes());
        MessageInput in = new MessageInput(inPipe);
        
        FoodMessage req = FoodMessage.decode(in);
        
        assertEquals(AddFood.class, req.getClass());
       
        inPipe.close();
        outPipe.close();
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
    public void testSetMessageTimestamp() throws FoodNetworkException {
        FoodMessage req1 = new GetFood(1234567);
        req1.setMessageTimestamp(123);
    }

    @Test(expected=FoodNetworkException.class)
    public void testSetMessageTimestampInvalid() throws FoodNetworkException {
        FoodMessage req1 = new GetFood(1234567);
        req1.setMessageTimestamp(-1);
    }

    @Test
    public void testGetMessageTimestamp() throws FoodNetworkException{
        FoodMessage req1 = new GetFood(1234567);
        assertEquals(1234567L, req1.getMessageTimestamp());
    }
    
    @Test
    public void testEqualsSymmertric() throws FoodNetworkException {
        FoodMessage test1 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        FoodMessage test2 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        
        assertEquals(test1,test2);
        assertEquals(test2,test1);
    }

    @Test
    public void testEqualsReflexsive() throws FoodNetworkException {
        FoodMessage test1 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        
        assertEquals(test1,test1);
        assertEquals(test1,test1);
    }

    @Test
    public void testEqualsNull() throws FoodNetworkException {
        FoodMessage test1 = new AddFood(1234567, new FoodItem("Peach", MealType.Snack, 50L, "1.2"));
        
        assertFalse(test1.equals(null));
    }
    
}