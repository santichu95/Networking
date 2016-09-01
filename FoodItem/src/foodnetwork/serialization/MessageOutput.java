/************************************************
*
* Author: Santiago Andaluz Ruiz
* Assignment: Program 0: Fatbat
* Class: CSI 4321 Data Comm
*
************************************************/
package foodnetwork.serialization;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Serialization output source
 * @author Santiago Andaulz Ruiz
 *
 */
public class MessageOutput {
	
	java.io.OutputStream out;
	
	/**Constructs a new output source from an OutputStream
	 * 
	 * @param out byte output source
	 */
	public MessageOutput(java.io.OutputStream out){
		this.out = out;
	}

	/**Writes name to OutputStream
	 * 
	 * @param name name 
	 * @throws IOException
	 */
	public void writeName(String name) throws IOException {
		out.write(name.getBytes());		
	}

	/**Writes calories to OutputStream
	 * @param calories calories
	 * @throws IOException
	 */
	public void writeCal(long calories) throws IOException {
	    ByteBuffer buf = ByteBuffer.allocate(Long.BYTES);
		buf.putLong(calories);
		out.write(buf.array());
		
	}

	/**Writes meal type to OutputStream
	 * @param type meal type
	 * @throws IOException
	 */
	public void writeType(MealType type) throws IOException {
		out.write(((Character) type.getMealTypeCode()).toString().getBytes());
	}

	/**Writes Fat to OutputStream
	 * @param fat grams of fat
	 * @throws IOException
	 */
	public void writeFat(String fat) throws IOException {
		out.write(fat.getBytes());	
	}
}
