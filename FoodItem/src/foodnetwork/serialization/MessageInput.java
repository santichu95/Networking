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
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Deserialization input source
 * 
 * @author Santiago Andaulz Ruiz
 *
 */
public class MessageInput {
<<<<<<< HEAD

    java.io.InputStream input;
    final int charSize = 1;
    byte[] buffer;
    int charCount;

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
     * Reads in the Name of the FoodItem
     * 
     * @return The name of the FoodItem
     * @throws FoodNetworkException
     *             if expected more bytes than were given
     */
    public String readName() throws FoodNetworkException, IOException {
        String name;
        
        //Credit: RealHowTo http://stackoverflow.com/users/25122/realhowto
        CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();

        charCount = this.readInt();
        int countRead;

        buffer = new byte[charCount];

        countRead = input.read(buffer, 0, charCount);
        
        if(countRead != charCount){
            throw new EOFException("Expected more input");
        }

        name = new String(buffer);
        
        //Credit: RealHowTo http://stackoverflow.com/users/25122/realhowto
        if ( !(asciiEncoder.canEncode(name)) ) {
            throw new FoodNetworkException("Non ASCII Character found");
        }

        return name;
    }

    /**
     * Reads an int from InputStream
     * 
     * @return int that was read from InputStream
     * @throws FoodNetworkException Did not get correct input
     */
    public int readInt() throws EOFException, FoodNetworkException, IOException {
        buffer = new byte[charSize];
        String tempInt = "";
        boolean endInt = false;

        while (!endInt && 1 == input.read(buffer, 0, charSize)) {
            if ( buffer[0] != ' ' ) {
                if ( buffer[0] > '9' || buffer[0] < '0' ) {
                    throw new FoodNetworkException("Did not get a digit 0-9");
                }
                tempInt += ((char) buffer[0]);
            } else {
                endInt = true;
            }
        }
        
        if ( !endInt ) {
            throw new EOFException("Expected more input");
        }

        charCount = Integer.parseInt(tempInt);
        return charCount;
    }

    /**
     * Reads the MealType from InputStream
     * 
     * @return MealType MealType read in
     * @throws FoodNetworkException
     *             if expected more bytes than were given
     */
    public MealType readType() throws FoodNetworkException, IOException {
        buffer = new byte[charSize];
        String convert;
        MealType result;

        int counter = input.read(buffer, 0, buffer.length);
        if ( counter < 1 ) {
           throw new EOFException("Expected more input"); 
        }

        convert = new String(buffer);
        result = MealType.getMealType(convert.charAt(0));

        return result;
    }

    /**
     * Reads the calories from InputStream
     * 
     * @return long calories
     * @throws FoodNetworkException
     *             if expected more bytes than were given
     */
    public long readCal() throws FoodNetworkException, IOException {

        long cal;
        buffer = new byte[charSize];
        String tempLong = "";
        boolean endLong = false;

        while (!endLong && 1 == input.read(buffer, 0, charSize)) {
            if (buffer[0] != ' ') {
                if (buffer[0] > '9' || buffer[0] < '0') {
                    throw new FoodNetworkException("Invalid input for calorie");
                }
                tempLong += ((char) buffer[0]);
            } else {
                endLong = true;
            }
        }

        if ( !endLong ) {
            throw new EOFException("Expected more input");
        }

        cal = Long.parseLong(tempLong);
        return cal;
    }

    /**
     * Reads the grams of fat form InputStream
     * 
     * @return grams of fat
     * @throws FoodNetworkException
     *             if more bytes were excpted than were recieved.
     */
    public String readFat() throws FoodNetworkException, IOException {
        buffer = new byte[charSize];
        String fat = "";
        boolean endFat = false;

        while (!endFat && 1 == input.read(buffer, 0, charSize)) {
            if ( buffer[0] != ' ' ) {
                if ( (buffer[0] > '9' && buffer[0] < '0' && buffer[0] != '.') ) {
                    throw new FoodNetworkException("Invalid input for Fat");
                }
                fat += ((char) buffer[0]);
            } else {
                endFat = true;
            }
        }
        
        
        if ( !endFat ) {
            throw new EOFException("Expected more input");
        }
        
        return fat;
    }
=======
	

	java.io.InputStream input;

	/**Constructs a new input source from an InputStream
	 * 
	 * @param in byte input source
	 */
	public MessageInput(java.io.InputStream in) {
		input = in;
	}

	/**
	 * 
	 * @return The name of the FoodItem
	 * @throws FoodNetworkException 
	 */
	public String readName() throws FoodNetworkException {
		int charCount;
		byte[] length = new byte[1];
		String tempInt = "";
		boolean endInt = false;
		String name;

		try{
			charCount = this.readInt();

			length = new byte[charCount];
			
			input.read(length, 0, charCount);
			
			name = new String(length);
		}catch(IOException e) {
			FoodNetworkException err = new FoodNetworkException("Expected to get Character Count");
			throw err;
		}
		
		
		return name;
	}
	
	public int readInt() throws IOException{
		int charCount;
		byte[] length = new byte[1];
		String tempInt = "";
		boolean endInt = false;

		while(!endInt && 1 == input.read(length, 0, 1)) {
			if ( length[0] <= '9' && length[0] >= '0' ) {
				tempInt += ((char) length[0] );
			}else {
				endInt = true;
			}
		}
		
		charCount = Integer.parseInt(tempInt);
		return charCount;
	}

	public MealType readType() {
		// TODO Auto-generated method stub
		return null;
	}

	public long readCal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String readFat() {
		// TODO Auto-generated method stub
		return null;
	}
>>>>>>> master
}
