package foodnetwork.serialization;

import java.io.IOException;

/**
 * Deserialization input source
 * @author Santiago Andaulz Ruiz
 *
 */
public class MessageInput {
	

	java.io.InputStream input;

	/**Constructs a new input source from an InputStream
	 * 
	 * @param in byte input source
	 */
	public MessageInput(java.io.InputStream in) {
		input = in;
	}

	/**Reads in the Name of the FoodItem
	 * 
	 * @return The name of the FoodItem
	 * @throws FoodNetworkException if expected more bytes than were given
	 */
	public String readName() throws FoodNetworkException {
		int charCount;
		byte[] length;
		String name;

		try{
			charCount = this.readInt();

			length = new byte[charCount];
			
			input.read(length, 0, charCount);
			
			name = new String(length);
		}catch(IOException e) {
			FoodNetworkException err = new FoodNetworkException("Expected to get name of food");
			throw err;
		}
		
		return name;
	}
	
	/**
	 * 
	 * @return int that was read from InputStream
	 * @throws FoodNetworkException if expected more bytes than were given
	 */
	public int readInt() throws FoodNetworkException {
		int charCount;
		byte[] length = new byte[1];
		String tempInt = "";
		boolean endInt = false;
		
		
		try {
			while(!endInt && 1 == input.read(length, 0, 1)) {
				if ( length[0] <= '9' && length[0] >= '0' ) {
					tempInt += ((char) length[0] );
				}else {
					endInt = true;
				}
			}
		} catch (IOException e) {
			FoodNetworkException err = new FoodNetworkException("Expected to get Character Count");
			throw err;
		}
		
		charCount = Integer.parseInt(tempInt);
		return charCount;
	}

	/**Reads the MealType from InputStream
	 * 
     * @return MealType MealType read in
	 * @throws FoodNetworkException if expected more bytes than were given
	 */
	public MealType readType() throws FoodNetworkException {
		byte[] temp = new byte[1];
		String convert;
		MealType result;
		
		try {
			input.read(temp, 0, temp.length);
			convert = new String(temp);
			result = MealType.getMealType(convert.charAt(0));
		} catch (IOException e) {
			FoodNetworkException err = new FoodNetworkException("Expected to get MealType Code");
			throw err;
		}
		
		return result;
	}

	/**Reads the calories from InputStream
	 * 
     * @return long calories
	 * @throws FoodNetworkException if expected more bytes than were given
	 */
	public long readCal() throws FoodNetworkException {
		
		long cal;
		byte[] length = new byte[1];
		String tempLong = "";
		boolean endLong = false;
		
		try {
			while(!endLong && 1 == input.read(length, 0, 1)) {
				if ( length[0] <= '9' && length[0] >= '0' ) {
					tempLong += ((char) length[0] );
				}else {
					endLong = true;
				}
			}
		} catch (IOException e) {
			FoodNetworkException err = new FoodNetworkException("Expected to get Character Count");
			throw err;
		}
		
		cal = Long.parseLong(tempLong);
		return cal;
	}
	
    /**Reads the grams of fat form InputStream
	 * 
	 * @return grams of fat
	 * @throws FoodNetworkException if more bytes were excpted than were recieved.
	 */
	public String readFat() throws FoodNetworkException {
		int charCount;
		byte[] length = new byte[1];
		String fat = "";
		boolean endInt = false;
		
		try {
			while(!endInt && 1 == input.read(length, 0, 1)) {
				if ( ( length[0] <= 'z' && length[0] >= 'a' ) || ( length[0] <= 'Z' && length[0] >= 'A' ) ) {
					fat += ((char) length[0] );
				}else {
					endInt = true;
				}
			}
		} catch (IOException e) {
			FoodNetworkException err = new FoodNetworkException("Expected to get Character Count");
			throw err;
		}
		return fat;
	}
}
