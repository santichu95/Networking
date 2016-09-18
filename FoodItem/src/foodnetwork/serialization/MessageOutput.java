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
    
    public void writeInt(int number) throws IOException {
        out.write((((Integer) number).toString() + " ").getBytes());
    }
    
    public void writeString( String string ) throws IOException {
        out.write((string + " ").getBytes());
    }

    /**
     * Writes name to OutputStream
     * 
     * @param name
     *            name
     * @throws FoodNetworkException
     */
    public void writeFLString(String name) throws FoodNetworkException {
        try {
            writeInt(name.length());
            out.write((name).getBytes());
        } catch (IOException e) {
           throw new FoodNetworkException(e.getMessage());
        }
    }

    /**
     * Writes the given long to OutputStream
     * 
     * @param number
     *            long to be written
     * @throws FoodNetworkException
     */
    public void writeLong(long number) throws FoodNetworkException {
        try {
            out.write((((Long) number).toString() + " ").getBytes());
        } catch (IOException e) {
            throw new FoodNetworkException(e.getMessage());
        }
    }

    /**
     * Writes meal type to OutputStream
     * 
     * @param type
     *            meal type
     * @throws FoodNetworkException
     */
    public void writeChar(char character) throws FoodNetworkException {
        try {
            out.write((((Character) character).toString()).getBytes());
        } catch (IOException e) {
            throw new FoodNetworkException(e.getMessage());
        }
    }
    
    public void writeEndline() throws FoodNetworkException{
        try {
            out.write('\n');
        } catch (IOException e) {
            throw new FoodNetworkException(e.getMessage());
        }
    }

    /**
     * Writes string double to OutputStream
     * 
     * @param number
     *            double to be written to OutputStream
     * @throws FoodNetworkException if I/O error occurs
     */
    public void writeStringDouble(String number) throws FoodNetworkException {
        try {
            out.write((number + " ").getBytes());
        } catch (IOException e) {
            throw new FoodNetworkException(e.getMessage());
        }
    }

}
