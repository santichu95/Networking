package foodnetwork.serialization;

import java.io.IOException;

public class ErrorMessage extends FoodMessage {

    String message;

    public ErrorMessage( long timestamp, String message ) throws FoodNetworkException {
        setMessageTimestamp( timestamp );
        this.message = message;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ErrorMessage [message=" + message + ", messageTimestamp=" + messageTimestamp + ", getRequest()="
                + getRequest() + "]";
    }

    @Override
    public String getRequest() {
        return "ERROR";
    }
    
    public final String getErrorMessage() {
        return message;
    }
    
    public final void setErrorMessage( String message ) {
        this.message = message;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
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
        if (message == null && other.message != null) {
                return false;
        }
        if (!message.equals(other.message)) {
            return false;
        }
        return true;
    }
    
    @Override
    public void encode( MessageOutput out ) throws FoodNetworkException {
        super.encode(out);
        try {
            out.writeString(getRequest());
            out.writeString(getErrorMessage());
            out.writeEndline();
        } catch (IOException e) {
            throw new FoodNetworkException(e.getMessage());
        }
    }
}
