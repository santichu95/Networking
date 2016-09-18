package foodnetwork.serialization;

import java.io.IOException;

public class GetFood extends FoodMessage {

    public GetFood( long timestamp ) throws FoodNetworkException {
        setMessageTimestamp(timestamp);
    }

    @Override
    public String getRequest() {
        return "GET";
    }

    @Override
    public String toString() {
        return "GetFood [messageTimestamp=" + messageTimestamp + ", getRequest()=" + getRequest() + "]";
    }
    
    @Override
    public void encode( MessageOutput out ) throws FoodNetworkException {
        super.encode(out);

        try {
            out.writeString(getRequest());
            out.writeEndline();
        } catch (IOException e) {
            throw new FoodNetworkException(e.getMessage());
        }
    }
}
