package foodnetwork.serialization;

import java.io.IOException;

public class AddFood extends FoodMessage {
    private FoodItem foodItem;

    public AddFood( long messageTimestamp, FoodItem foodItem ) throws FoodNetworkException{
        setMessageTimestamp(messageTimestamp);
        this.foodItem = foodItem;
    }
    
    @Override
    public void encode( MessageOutput out ) throws FoodNetworkException {
        super.encode(out);
        try {
            out.writeString(getRequest());
            foodItem.encode(out);
            out.writeEndline();
        } catch (IOException e) {
            throw new FoodNetworkException("I/O interrupted");
        }
    }

    @Override
    public String toString() {
        return "AddFood [foodItem=" + foodItem + ", messageTimestamp=" + messageTimestamp + ", getRequest()="
                + getRequest() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((foodItem == null) ? 0 : foodItem.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof AddFood)) {
            return false;
        }
        AddFood other = (AddFood) obj;
        if (foodItem == null && other.foodItem != null ) {
                return false;
        }
        if (!foodItem.equals(other.foodItem)) {
            return false;
        }
        return true;
    }

    @Override
    public String getRequest() {
        return "ADD";
    }
    
    public final FoodItem getFoodItem() {
        return foodItem;
    }
    
    public final void setFoodItem( FoodItem foodItem ) {
        this.foodItem = foodItem;
    }

}
