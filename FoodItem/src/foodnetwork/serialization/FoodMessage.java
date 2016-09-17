package foodnetwork.serialization;

public abstract class FoodMessage {
    
    private long messageTimestamp;

    public static FoodMessage decode( MessageInput in ) {
        return null;
    }
    
    public void encode( MessageOutput out ) {
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (messageTimestamp ^ (messageTimestamp >>> 32));
        return result;
    }

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
    
    public long getMessageTimestamp() {
        return messageTimestamp;
    }
        
    public abstract String getRequest();
    
    public int hashCoe() {
        return 0;
    }
    
    public void setMessageTimestamp( long messageTimestamp ) throws FoodNetworkException {
        if ( messageTimestamp < 0 ) {
            throw new FoodNetworkException ("Expected non-negative timestamp");
        }
        this.messageTimestamp = messageTimestamp;
    }
    
    public String toString() {
        return "";
    }
}
