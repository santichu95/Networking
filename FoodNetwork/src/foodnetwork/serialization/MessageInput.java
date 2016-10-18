/************************************************
*
* Author: Santiago Andaluz Ruiz
* Assignment: Program 0: Fatbat
* Class: CSI 4321 Data Comm
*
************************************************/
package foodnetwork.serialization;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Deserialization input source
 * 
 * @author Santiago Andaulz Ruiz
 *
 */
public class MessageInput {

	/**
	 * The inputstream to wrap MessageInput around
	 */
    private InputStream input;
    /**
     * Size of a character
     */
    private final int charSize = 1;
    /**
     * Buffer to store input from the stream
     */
    private byte[] buffer;
    /**
     * Given delimiter for a String
     */
    private final char DELIMITER = ' ';

    /**
     * Constructs a new input source from an InputStream
     * 
     * @param in
     *            byte input source
     */
    public MessageInput(java.io.InputStream in) {
        input = in;
    }

    /**
     * Reads in a Fixed Length String
     * @param is the number of characters to read in
     * 			variable parameter, must be of size 0 or 1
     * 			0: the number of bytes to read from in will come from in
     * 			1: the number of bytes to read from in is the first element in is
     * @return the fixed length string that was read in
     * @throws FoodNetworkException
     *             if expected more bytes than were given
     * @throws IOException some I/O error occurs
     */
    public String readFLString(int...is) throws FoodNetworkException, IOException {
        String name;

        int charCount;
        
        //Getting the length of the string
        if ( is.length == 0 ){
        	charCount = this.readInt();
        } else if (is.length == 1){
        	charCount = is[0];
        } else {
        	throw new FoodNetworkException("Too many parameters passed, expected 0 or 1 recieved " + is.length);
        }


    	if ( charCount < 0 ) {
    		throw new FoodNetworkException("Size of string to be read in can not be negative");
    	}

        int countRead = 0;
        int temp;
        
        buffer = new byte[charCount];

        while ( countRead < charCount && ( temp = input.read(buffer, countRead, charSize )) != -1 ) {
        	countRead += temp;
        }
        
        if(countRead != charCount){
            throw new EOFException("Expected more input");
        }

        name = new String(buffer,"US-ASCII");
 
        return name;
    }

    /**
     * Reads an int from InputStream
     * 
     * @return int that was read from InputStream
     * @throws FoodNetworkException Did not get correct input
     * @throws IOException some I/O error occurs
     * @throws EOFException expected more input
     */
    public int readInt() throws EOFException, FoodNetworkException, IOException {
        String tempInt = readString();
        
        try {
        	return Integer.parseInt(tempInt);
        } catch( NumberFormatException e ) {
            throw new FoodNetworkException("Expected unsinged integer string");
        }
    }

    /**
     * Reads a char from InputStream
     * 
     * @return char char read in
     * @throws FoodNetworkException
     *             if expected more bytes than were given
     * @throws IOException some I/O error occurs
     */
    public char readChar() throws FoodNetworkException, IOException {
    	return readFLString(charSize).charAt(0);
    }

    /**
     * Reads a long from InputStream
     * 
     * @return long long read in from InputStream
     * @throws FoodNetworkException
     *             if expected more bytes than were given
     * @throws IOException some I/O error occurs
     */
    public long readLong() throws FoodNetworkException, IOException {
        String tempLong = readString();
        
        try {
            return Long.parseLong(tempLong);
        } catch ( NumberFormatException e ) {
            throw new FoodNetworkException(e.getMessage());
        }
    }

    /**
     * Reads in a double in the format of a string
     * 
     * @return grams of fat
     * @throws FoodNetworkException
     *             if more bytes were expected than were received.
     * @throws IOException some I/O error occurs
     */
    public String readStringDouble() throws FoodNetworkException, IOException {
        String tempDouble = readString();
        
        try {
            Double.parseDouble(tempDouble);
        } catch ( NumberFormatException e ) {
            throw new FoodNetworkException(e.getMessage());
        }
        
        return tempDouble;
    }
    
    /**
     * Read up to DELIMITER is read
     * @return the string from in until the DELIMITER is read
     * @throws IOException some I/O error occurs
     */
    public String readString() throws IOException {
        buffer = new byte[charSize];
        String tempString = "";
        boolean endString = false;

        while (!endString && charSize == input.read(buffer, 0, charSize)) {
            if ( buffer[0] != DELIMITER ) {
               tempString += ((char) buffer[0]);
            } else {
                endString = true;
            }
        }
        
        if ( !endString ) {
            throw new EOFException("Expected more input");
        }

        return tempString;
    }
}
