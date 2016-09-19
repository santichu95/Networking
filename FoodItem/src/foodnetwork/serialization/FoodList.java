package foodnetwork.serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FoodList extends FoodMessage {
    
    private long modTimestamp;
    private static List<FoodItem> foods = new ArrayList<FoodItem>();

    public FoodList( long timestamp, long modTimestamp ) throws FoodNetworkException {
        setMessageTimestamp( timestamp );
        this.modTimestamp = modTimestamp;
    }

    public long getModifiedTimestamp() {
        return modTimestamp;
    }
    
    public final void setModifiedTimestamp( long timestamp ) throws FoodNetworkException {
       if ( timestamp < getMessageTimestamp() || timestamp <= 0 ) {
           throw new FoodNetworkException("Invalid modified timestamp");
       }
       modTimestamp = timestamp;
    }
    
    public List<FoodItem> getFoodItemList() {
        return new ArrayList<FoodItem>(foods);
    }
        
    public void addFoodItem( FoodItem foodItem ) {
        foods.add(foodItem);
    }

    @Override
    public String getRequest() {
        return "LIST";
    }
    
    @Override
    public void encode( MessageOutput out ) throws FoodNetworkException{
       super.encode(out);
        try {
            out.writeString(getRequest());
            out.writeLong(getModifiedTimestamp());
            out.writeInt(foods.size());
            for ( FoodItem var : foods ) {
                var.encode(out);
            }
        } catch (IOException e) {
            throw new FoodNetworkException(e.getMessage());
        }
    }
}
