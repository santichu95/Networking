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
}