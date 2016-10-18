/************************************************
*
* Author: Santiago Andaluz Ruiz
* Assignment: Program 1
* Class: CSI 4321 Data Comm
*
************************************************/
package foodnetwork.serialization;

/**Represents a GetFood and provides serialization/deserialization
 * @author Santiago Andaluz Ruiz
 *
 */
public class GetFood extends FoodMessage {

	/**
	 * The label for the request
	 */
    private final static String TYPE = "GET";
    
    /**Gets the type of request
     * @return the type of request
     */
    public static String getRequestType() {
    	return TYPE;
    }
    /**Constructs GetFood using set values
     * @param timestamp message timestamp
     * @throws FoodNetworkException if validation fails
     */
    public GetFood( long timestamp ) throws FoodNetworkException {
        setMessageTimestamp(timestamp);
    }

    /* (non-Javadoc)
     * @see foodnetwork.serialization.FoodMessage#getRequest()
     */
    @Override
    public String getRequest() {
        return GetFood.getRequestType();
    }

    /* (non-Javadoc)
     * @see foodnetwork.serialization.FoodMessage#toString()
     */
    @Override
    public String toString() {
        return "GetFood [messageTimestamp=" + messageTimestamp + ", getRequest()=" + getRequest() + "]";
    }
    
    /* (non-Javadoc)
     * @see foodnetwork.serialization.FoodMessage#encode(foodnetwork.serialization.MessageOutput)
     */
    @Override
    public void encode( MessageOutput out ) throws FoodNetworkException {
        super.encode(out);
        out.writeString(getRequest());
        out.writeChar(FoodMessage.ENDLINECHAR);
        
    }
}
