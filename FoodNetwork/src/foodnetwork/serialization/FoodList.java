/************************************************
*
* Author: Santiago Andaluz Ruiz
* Assignment: Program 1
* Class: CSI 4321 Data Comm
*
************************************************/
package foodnetwork.serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**Represents a FoodList message and provides serialization/deserialization
 * @author Santiago Andaluz Ruiz
 *
 */
public class FoodList extends FoodMessage {
    
    private long modTimestamp;
    private List<FoodItem> foods = new ArrayList<FoodItem>();
    private final static String TYPE = "LIST";
    
    public static String getRequestType() {
    	return TYPE;
    };

    /**Constructs a FoodList item using set values
     * @param timestamp message timestamp
     * @param modTimestamp timestamp of last modification
     * @throws FoodNetworkException if validation fails
     */
    public FoodList( long timestamp, long modTimestamp ) throws FoodNetworkException {
        setMessageTimestamp( timestamp );
        setModifiedTimestamp( modTimestamp );
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodList other = (FoodList) obj;
		if (foods == null) {
			if (other.foods != null)
				return false;
		} else if (!foods.equals(other.foods))
			return false;
		if (modTimestamp != other.modTimestamp)
			return false;
		return true;
	}

	/**Returns modified timestamp
     * @return modified timestamp
     */
    public long getModifiedTimestamp() {
        return modTimestamp;
    }
    
    /**Sets modified timestamp
     * @param timestamp new modified timestamp
     * @throws FoodNetworkException if validation fails
     */
    public final void setModifiedTimestamp( long timestamp ) throws FoodNetworkException {
       if ( timestamp < 0 ) {
           throw new FoodNetworkException("Invalid modified timestamp");
       }
       modTimestamp = timestamp;
    }
    
    /**Returns list of food items
     * @return food items
     */
    public List<FoodItem> getFoodItemList() {
        return new ArrayList<FoodItem>(foods);
    }
        
    /**Adds food item
     * @param foodItem new food item to add
     * @throws FoodNetworkException if validation fails
     */
    public void addFoodItem( FoodItem foodItem ) throws FoodNetworkException {
        if ( foodItem == null ) {
            throw new FoodNetworkException("Expected non-null FoodItem");
        }
        foods.add(foodItem);
    }

    /* (non-Javadoc)
     * @see foodnetwork.serialization.FoodMessage#getRequest()
     */
    @Override
    public String getRequest() {
        return TYPE;
    }
    
    /* (non-Javadoc)
     * @see foodnetwork.serialization.FoodMessage#encode(foodnetwork.serialization.MessageOutput)
     */
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
            out.writeChar('\n');
        } catch (IOException e) {
            throw new FoodNetworkException(e.getMessage());
        }
    }

	@Override
	public String toString() {
		String ret =  "FoodList Modified TS=" + modTimestamp + "\n" + "Msg TS" + getMessageTimestamp();
		for ( FoodItem var : foods ) {
			ret += var.toString() + "\n";
		}
		return ret;
	}
}
