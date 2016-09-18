/***************************************************
*
* Author:      Jonathan Myers & Santiago Andaluz
* Assignment:  Program 1
* Class:       CSI 4321
*
***************************************************/

package foodnetwork.serialization.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

import foodnetwork.serialization.ErrorMessage;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.MessageOutput;

/**
* ErrorMessageTest class using JUnit4 test cases to test the accuracy of package foodnetwork.serialization
* @author Jonathan Myers & Santiago Andaluz
* @version 1.1 Build 9/17/2016
*/
public class ErrorMessageTest {
	
	/**
	 * Tests ErrorMessage(long messageTimestamp, String errorMessage)
	 * @throws FoodNetworkException if messageTimestamp or errorMessage validation fails
	 */
	@Test(expected=FoodNetworkException.class)
	public void testInvalidTimestamp() throws FoodNetworkException {
		new ErrorMessage(-1, "Cannot add FoodItem");
	}
	
	/**
	 * Tests ErrorMessage(long messageTimestamp, String errorMessage)
	 * @throws FoodNetworkException if messageTimestamp or errorMessage validation fails
	 */
	@Test(expected=FoodNetworkException.class)
	public void testInvalidErrorMessage() throws FoodNetworkException {
		new ErrorMessage(1234567, "");
	}
	
	/**
	 * Tests ErrorMessage(long messageTimestamp, String errorMessage)
	 * @throws FoodNetworkException if messageTimestamp or errorMessage validation fails
	 */
	@Test
	public void testErrorMessageConstructorSuccess() throws FoodNetworkException {
		new ErrorMessage(1234567, "Cannot add FoodItem");
	}
	
	/**
	 * Tests ErrorMessage.encode(MessageOutput out)
	 * @throws FoodNetworkException if serialization output fails
	 */
	@Test(expected=FoodNetworkException.class)
	public void testErrorMessageEncodeFails() throws FoodNetworkException, IOException {
		// Create FileOutputStream
		OutputStream os = new FileOutputStream("Bad");
		ErrorMessage e = new ErrorMessage(1234567, "Cannot add FoodItem");
		MessageOutput mo = new MessageOutput(os);

		// Close the FileOutputStream before writing to it
		os.close();
		e.encode(mo);
	}
	
	/**
	 * Tests ErrorMessage.encode(MessageOutput out)
	 * @throws FoodNetworkException if serialization output fails
	 */
	@Test
	public void testEncodePasses() throws FoodNetworkException, IOException {
		// Create ByteArrayOutputStream to capture the output
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ErrorMessage e = new ErrorMessage(1234567, "Cannot add FoodItem");
		MessageOutput mo = new MessageOutput(b);
		// Encode & test output
		e.encode(mo);
		byte[] bytes = b.toByteArray();
		String s = new String(bytes);
		assertEquals(s, "FN1.0 1234567 ERROR 19 Cannot add FoodItem\n");
	}
	
	/**
	 * Tests ErrorMessage.setErrorMessage(String errorMessage)
	 * @throws FoodNetworkException if messageTimestamp or errorMessage validation fails
	 */
	@Test(expected=FoodNetworkException.class)
	public void testErrorMessageSetNullErrorMessage() throws FoodNetworkException {
		ErrorMessage e = new ErrorMessage(1234567, "Cannot add FoodItem");
		e.setErrorMessage(null);
	}
	
	/**
	 * Tests ErrorMessage.setErrorMessage(String errorMessage)
	 * @throws FoodNetworkException if messageTimestamp or errorMessage validation fails
	 */
	@Test(expected=FoodNetworkException.class)
	public void testErrorMessageSetEmptyErrorMessage() throws FoodNetworkException {
		ErrorMessage e = new ErrorMessage(1234567, "Cannot add FoodItem");
		e.setErrorMessage("");
	}
		
	/**
	 * Tests ErrorMessage.getRequest()
	 * @throws FoodNetworkException if messageTimestamp or errorMessage validation fails
	 */
	@Test
	public void testErrorMessageGetRequestSuccess() throws FoodNetworkException {
		assertEquals(new ErrorMessage(1234567, "ERROR").getRequest(), "ERROR");
	}
	
	/**
	 * Tests ErrorMessage.equals()
	 * @throws FoodNetworkException if messageTimestamp or errorMessage validation fails
	 */
	@Test
	public void testErrorMessageEqualsFailure() throws FoodNetworkException {
		assertNotEquals(new ErrorMessage(1234567, "Cannot add FoodItem"),
				new ErrorMessage(1234566, "Cannot add FoodItem"));
	}
	
	/**
	 * Tests ErrorMessage.equals()
	 * @throws FoodNetworkException if messageTimestamp or errorMessage validation fails
	 */
	@Test
	public void testEqualsSuccess() throws FoodNetworkException {
		assertEquals(new ErrorMessage(1234567, "Cannot add FoodItem"),
				new ErrorMessage(1234567, "Cannot add FoodItem"));
	}
	
	/**
	 * Tests ErrorMessage.getMessageTimestamp
	 * @throws FoodNetworkException if messageTimestamp or errorMessage validation fails
	 */
	@Test
	public void testErrorMessageMessageTimestampGetter() throws FoodNetworkException {
		assertEquals(new ErrorMessage(1234567, "Cannot add FoodItem").getMessageTimestamp(), 1234567);
	}

	/**
	 * Tests ErrorMessage.getErrorMessage()
	 * @throws FoodNetworkException if messageTimestamp or errorMessage validation fails
	 */
	@Test
	public void testErrorMessageErrorMessageGetter() throws FoodNetworkException {
		assertEquals(new ErrorMessage(1234567, "Cannot add FoodItem").getErrorMessage(), "Cannot add FoodItem");
	}
}
