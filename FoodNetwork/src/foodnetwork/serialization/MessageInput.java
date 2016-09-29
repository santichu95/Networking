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

    private InputStream input;
    private final int charSize = 1;
    private byte[] buffer;
    private int charCount;
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
     * 
     * @return the fixed length string that was read in
     * @throws FoodNetworkException
     *             if expected more bytes than were given
     * @throws IOException some I/O error occurs
     */
    public String readFLString() throws FoodNetworkException, IOException {
        String name;

        //Getting the length of the string
        charCount = this.readInt();
        int countRead;

        buffer = new byte[charCount];

        countRead = input.read(buffer, 0, charCount);
        
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
        buffer = new byte[charSize];
        String tempInt = "";
        boolean endInt = false;

        while (!endInt && charSize == input.read(buffer, 0, charSize)) {
            if ( buffer[0] != DELIMITER ) {
               tempInt += ((char) buffer[0]);
            } else {
                endInt = true;
            }
        }
        
        if ( !endInt ) {
            throw new EOFException("Expected more input");
        }

        try {
            charCount = Integer.parseInt(tempInt);
        } catch( NumberFormatException e ) {
        	//Just throw NFE and validate higher
            throw new FoodNetworkException("Expected unsinged integer string");
        }

        return charCount;
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
        buffer = new byte[charSize];

        int counter = input.read(buffer, 0, buffer.length);
        if ( counter < charSize ) {
           throw new EOFException("Expected more input"); 
        }

        return (char) buffer[0];
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

        long number;
        buffer = new byte[charSize];
        String tempLong = "";
        boolean endLong = false;

        while (!endLong && 1 == input.read(buffer, 0, charSize)) {
            if (buffer[0] != DELIMITER ) {
                tempLong += ((char) buffer[0]);
            } else {
                endLong = true;
            }
        }

        if ( !endLong ) {
            throw new EOFException("Expected more input");
        }

        try {
            number = Long.parseLong(tempLong);
        } catch ( NumberFormatException e ) {
            throw new FoodNetworkException(e.getMessage());
        }

        return number;
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
        buffer = new byte[charSize];
        String number = "";
        boolean endFat = false;

        while (!endFat && 1 == input.read(buffer, 0, charSize)) {
            if ( buffer[0] != DELIMITER ) {
                number += ((char) buffer[0]);
            } else {
                endFat = true;
            }
        }
        
        
        if ( !endFat ) {
            throw new EOFException("Expected more input");
        }
        if ( number.charAt(0) == '-' ) {
            throw new FoodNetworkException("Expected non-negative fat");
        }
        
        try {
            Double.parseDouble(number);
        } catch ( NumberFormatException e ) {
            throw new FoodNetworkException(e.getMessage());
        }
        
        return number;
    }
    
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
