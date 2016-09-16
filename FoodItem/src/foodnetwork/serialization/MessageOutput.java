/************************************************
*
* Author: Santiago Andaluz Ruiz
* Assignment: Program 0: Fatbat
* Class: CSI 4321 Data Comm
*
************************************************/
package foodnetwork.serialization;

import java.io.IOException;

/**
 * Serialization output source
 * 
 * @author Santiago Andaulz Ruiz
 *
 */
public class MessageOutput {

    java.io.OutputStream out;

    /**
     * Constructs a new output source from an OutputStream
     * 
     * @param out
     *            byte output source
     */
    public MessageOutput(java.io.OutputStream out) {
        this.out = out;
    }
    
    public void writeInt(String name) throws IOException {
        out.write((((Integer) name.length()).toString() + " ").getBytes());
    }

    /**
     * Writes name to OutputStream
     * 
     * @param name
     *            name
     * @throws FoodNetworkException
     */
    public void writeName(String name) throws FoodNetworkException {
        try {
            writeInt(name);
            out.write((name).getBytes());
        } catch (IOException e) {
           throw new FoodNetworkException(e.getMessage());
        }
    }

    /**
     * Writes calories to OutputStream
     * 
     * @param calories
     *            calories
     * @throws FoodNetworkException
     */
    public void writeCal(long calories) throws FoodNetworkException {
        try {
            out.write((((Long) calories).toString() + " ").getBytes());
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
    public void writeType(MealType type) throws FoodNetworkException {
        try {
            out.write(((Character) type.getMealTypeCode()).toString().getBytes());
        } catch (IOException e) {
            throw new FoodNetworkException(e.getMessage());
        }
    }

    /**
     * Writes Fat to OutputStream
     * 
     * @param fat
     *            grams of fat
     * @throws FoodNetworkException
     */
    public void writeFat(String fat) throws FoodNetworkException {
        try {
            out.write((fat + " ").getBytes());
        } catch (IOException e) {
            throw new FoodNetworkException(e.getMessage());
        }
    }
}
