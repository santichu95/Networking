/************************************************
*
* Author: Santiago Andaluz Ruiz
* Assignment: Program 1
* Class: CSI 4321 Data Comm
*
************************************************/
package foodnetwork.serialization;

import java.io.IOException;

/**Represents an error message and provides serialization/deserialization
 * @author Santiago Andaluz Ruiz
 *
 */
public class ErrorMessage extends FoodMessage {

    private String message;
    private final char ENDLINE = '\n';
    private final static String TYPE = "ERROR";
    
    public static String getRequestType() {
    	return TYPE;
    };

    /**Constructs error message using set values
     * @param timestamp message timestamp
     * @param message error message
     * @throws FoodNetworkException if validation fails
     */
    public ErrorMessage( long timestamp, String message ) throws FoodNetworkException {
        setMessageTimestamp( timestamp );
        setErrorMessage( message );
    }

    /* (non-Javadoc)
     * @see foodnetwork.serialization.FoodMessage#toString()
     */
    @Override
    public String toString() {
        return "ErrorMessage [message=" + message + ", messageTimestamp=" + messageTimestamp + ", getRequest()="
                + getRequest() + "]";
    }

    /* (non-Javadoc)
     * @see foodnetwork.serialization.FoodMessage#getRequest()
     */
    @Override
    public String getRequest() {
        return TYPE;
    }
    
    /**Returns error message
     * @return error message
     */
    public final String getErrorMessage() {
        return message;
    }
    
    /**Sets error message
     * @param message new error message
     * @throws FoodNetworkException if validation fails
     */
    public final void setErrorMessage( String message ) throws FoodNetworkException {
        if ( message == null ) {
            throw new FoodNetworkException("Expected non-null error message");
        }
        if ( message.length() < 1 ) {
            throw new FoodNetworkException("Expected non-empty String for error message");
        }
        this.message = message;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    /* (non-Javadoc)
     * @see foodnetwork.serialization.FoodMessage#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result +  message.hashCode();
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    /* (non-Javadoc)
     * @see foodnetwork.serialization.FoodMessage#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof ErrorMessage)) {
            return false;
        }
        ErrorMessage other = (ErrorMessage) obj;
        if (!message.equals(other.message)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see foodnetwork.serialization.FoodMessage#encode(foodnetwork.serialization.MessageOutput)
     */
    @Override
    public void encode( MessageOutput out ) throws FoodNetworkException {
        super.encode(out);
        try {
            out.writeString(getRequest());
            out.writeFLString(getErrorMessage());
            out.writeChar(ENDLINE);
        } catch (IOException e) {
            throw new FoodNetworkException(e.getMessage());
        }
    }
}
