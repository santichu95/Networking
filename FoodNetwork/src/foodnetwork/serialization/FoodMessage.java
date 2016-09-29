/************************************************
*
* Author: Santiago Andaluz Ruiz
* Assignment: Program 1
* Class: CSI 4321 Data Comm
*
************************************************/
package foodnetwork.serialization;

import java.io.EOFException;
import java.io.IOException;

/**Represents generic portion of Food message
 * @author Santiago Andaluz Ruiz
 *
 */
public abstract class FoodMessage {
    
    protected long messageTimestamp;
    private final static String versionNum = "FN1.0";
    private final static char ENDLINECHAR = '\n';

    /**Deserializes message from byte source
     * @param in deserialization input source
     * @return a specific FoodMessage resulting from deserialization
     * @throws FoodNetworkException if deserialization or validation failure
     * @throws IOException if premature end of stream
     */
    public static FoodMessage decode( MessageInput in ) throws FoodNetworkException, IOException {
        String version;
        try {
            version = in.readString();
        } catch ( IOException e ) {
            throw new EOFException("Premature end of stream");
        }

        if ( !versionNum.equals(version) ) {
            throw new FoodNetworkException("Version number did not match");
        }
        
        long timestamp = in.readLong();
        
        String request = in.readString();
        
        FoodMessage reqMessage = null;
        if ( request.equals("ADD") ) {
            reqMessage = new AddFood( timestamp, new FoodItem(in) );
        } else if ( request.equals("GET")) {
            reqMessage = new GetFood( timestamp );
        } else if ( request.equals("LIST") ) {
            reqMessage = new FoodList( timestamp, in.readLong() );
            
            int sizeList = in.readInt();
            
            for ( int i = 0; i < sizeList; i++ ) {
                ((FoodList)reqMessage).addFoodItem(new FoodItem(in));
            }
            
        } else if ( request.equals("ERROR")) {
            reqMessage = new ErrorMessage( timestamp, in.readFLString() );
        } else {
            throw new FoodNetworkException("Unexpected request");
        }
        
        char endline = in.readChar();
        
        if ( endline != ENDLINECHAR ) {
            throw new FoodNetworkException("Expected ENDLINECHAR:" + ENDLINECHAR + " recieved: " + endline );
        }

        return reqMessage;
    }
    
    /**Serializes message to MessageOutput
     * @param out serialization output destination
     * @throws FoodNetworkException if serialization fails
     */
    public void encode( MessageOutput out ) throws FoodNetworkException {
        try {
            out.writeString(versionNum);
            out.writeLong(getMessageTimestamp());
        } catch (IOException e) {
            throw new FoodNetworkException("I/O interrupted");
        }
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (messageTimestamp ^ (messageTimestamp >>> 32));
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FoodMessage)) {
            return false;
        }
        FoodMessage other = (FoodMessage) obj;
        if (messageTimestamp != other.messageTimestamp) {
            return false;
        }
        return true;
    }

    /**Returns message timestamp
     * @return message timestamp
     */
    public final long getMessageTimestamp() {
        return messageTimestamp;
    }
        
    /**Returns message request (e.g., ADD)
     * @return message request
     */
    public abstract String getRequest();
    
    /**Sets message timestamp
     * @param messageTimestamp new message timestamp
     * @throws FoodNetworkException if validation fails
     */
    public final void setMessageTimestamp( long messageTimestamp ) throws FoodNetworkException {
        if ( messageTimestamp < 0 ) {
            throw new FoodNetworkException ("Expected non-negative timestamp");
        }
        this.messageTimestamp = messageTimestamp;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "FoodMessage [messageTimestamp=" + messageTimestamp + "]" + "[Request Type=" + getRequest() + "]";
    }
}
