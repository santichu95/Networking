/************************************************
*
* Author: Santiago Andaluz Ruiz
* Assignment: Program 0: Fatbat
* Class: CSI 4321 Data Comm
*
************************************************/
package foodnetwork.serialization;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Serialization output source
 * 
 * @author Santiago Andaulz Ruiz
 *
 */
public class MessageOutput {

	/**
	 * The output stream to wrap MessageOutput around
	 */
    OutputStream out;

    /**
     * Constructs a new output source from an OutputStream
     * 
     * @param out
     *            byte output source
     */
    public MessageOutput(java.io.OutputStream out) {
        this.out = out;
    }
    
    public void write(String string) throws FoodNetworkException {
    	try {
			out.write(string.getBytes("US-ASCII"));
		} catch (IOException e) {
			throw new FoodNetworkException(e.getMessage());
		}
    }
    
    public void writeInt(int number) throws FoodNetworkException {
    	write(((Integer)number).toString() + " ");
    }
    
    public void writeString( String string ) throws FoodNetworkException {
    	write(string + " ");
    }

    /**
     * Writes name to OutputStream
     * 
     * @param name
     *            name
     * @throws FoodNetworkException premature end of stream
     */
    public void writeFLString(String name) throws FoodNetworkException {
            writeInt(name.length());
            write(name);
    }

    /**
     * Writes the given long to OutputStream
     * 
     * @param number long to be written
     * @throws FoodNetworkException premature end of stream
     */
    public void writeLong(long number) throws FoodNetworkException {
            write(((Long) number).toString() + " ");
    }

    /**
     * Writes char to OutputStream
     * 
     * @param character char to write
     *            
     * @throws FoodNetworkException premature end of stream
     */
    public void writeChar(char character) throws FoodNetworkException {
    	write(((Character)character).toString());
    }
    
    /**
     * Writes string double to OutputStream
     * 
     * @param number
     *            double to be written to OutputStream
     * @throws FoodNetworkException if I/O error occurs
     */
    public void writeStringDouble(String number) throws FoodNetworkException {
    	writeString(number);
    }

}
