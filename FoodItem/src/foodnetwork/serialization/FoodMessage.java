package foodnetwork.serialization;

import java.io.EOFException;
import java.io.IOException;

public abstract class FoodMessage {
    
    protected long messageTimestamp;
    private final static String versionNum = "FN1.0";

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
            reqMessage = new FoodList( timestamp, System.currentTimeMillis() );
        } else if ( request.equals("ERROR")) {
            reqMessage = new ErrorMessage( timestamp, in.readFLString() );
        } else {
            throw new FoodNetworkException("Unexpected request");
        }

        return reqMessage;
    }
    
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
        if (this == obj) {
            return true;
        }
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

    public final long getMessageTimestamp() {
        return messageTimestamp;
    }
        
    public abstract String getRequest();
    
    public final void setMessageTimestamp( long messageTimestamp ) throws FoodNetworkException {
        if ( messageTimestamp < 0 ) {
            throw new FoodNetworkException ("Expected non-negative timestamp");
        }
        this.messageTimestamp = messageTimestamp;
    }
    
    @Override
    public String toString() {
        return "FoodMessage [messageTimestamp=" + messageTimestamp + "]" + "[Request Type=" + getRequest() + "]";
    }
}
