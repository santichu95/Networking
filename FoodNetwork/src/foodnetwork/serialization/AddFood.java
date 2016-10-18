/************************************************
*
* Author: Santiago Andaluz Ruiz
* Assignment: Program 1
* Class: CSI 4321 Data Comm
*
************************************************/
package foodnetwork.serialization;

/**Represents an AddFood and provides serialization/deserialization
 * @author Santiago Andaluz Ruiz
 *
 */
public class AddFood extends FoodMessage {
	
	/**
	 * FoodItem that is going to be added
	 */
    private FoodItem foodItem;  
    /**
     * The label of the request
     */
    private final static String TYPE = "ADD";
    
    /**Gets the type of request
     * @return the type of request
     */
    public static String getRequestType() {
    	return TYPE;
    }

    /**Constructs new AddFood using set values
     * @param messageTimestamp message timestamp
     * @param foodItem new foodItem
     * @throws FoodNetworkException if validation fails
     */
    public AddFood( long messageTimestamp, FoodItem foodItem ) throws FoodNetworkException{
        setMessageTimestamp(messageTimestamp);
        setFoodItem(foodItem);
    }
    
    /* (non-Javadoc)
     * @see foodnetwork.serialization.FoodMessage#encode(foodnetwork.serialization.MessageOutput)
     */
    @Override
    public void encode( MessageOutput out ) throws FoodNetworkException {
        super.encode(out);
        out.writeString(getRequest());
        foodItem.encode(out);
        out.writeChar(FoodMessage.ENDLINECHAR);
    }

    /* (non-Javadoc)
     * @see foodnetwork.serialization.FoodMessage#toString()
     */
    @Override
    public String toString() {
        return "AddFood [foodItem=" + foodItem + ", messageTimestamp=" + messageTimestamp + ", getRequest()="
                + getRequest() + "]";
    }

    /* (non-Javadoc)
     * @see foodnetwork.serialization.FoodMessage#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result +  foodItem.hashCode();
        return result;
    }

    /* (non-Javadoc)
     * @see foodnetwork.serialization.FoodMessage#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( this == obj ) {
            return true;
        }
        if ( !super.equals(obj) ) {
            return false;
        }
        if ( !(obj instanceof AddFood) ) {
            return false;
        }
        AddFood other = (AddFood) obj;
        if ( !foodItem.equals(other.foodItem) ) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see foodnetwork.serialization.FoodMessage#getRequest()
     */
    @Override
    public String getRequest() {
        return AddFood.getRequestType();
    }
    
    /**Returns food item
     * @return food item
     */
    public final FoodItem getFoodItem() {
        return foodItem;
    }
    
    /**Sets food item
     * @param foodItem new foodItem
     * @throws FoodNetworkException if validation fails
     */
    public final void setFoodItem( FoodItem foodItem ) throws FoodNetworkException {
        if ( foodItem == null ) {
            throw new FoodNetworkException("Expected non-null FoodItem");
        }
        this.foodItem = foodItem;
    }

}
