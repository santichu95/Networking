/************************************************
*
* Author: Santiago Andaluz Ruiz
* Assignment: Program 1
* Class: CSI 4321 Data Comm
*
************************************************/
package foodnetwork.serialization;
/**
 * Represents a Interval and provides serialization/deserialization
 * @author Santiago Andaluz Ruiz
 *
 */
public class Interval extends FoodMessage {

	private int intervalTime;
    private final static String TYPE = "INTERVAL";
    
    /**Gets the type of request
     * @return the type of request
     */
    public static String getRequestType() {
    	return TYPE;
    }
	
	public Interval(long messageTimestamp, int intervalTime) throws FoodNetworkException {
		super.setMessageTimestamp(messageTimestamp);
		setIntervalTime(intervalTime);
	}
	
	public int getIntervalTime() {
		return intervalTime;
	}
	
	public void setIntervalTime(int intervalTime) throws FoodNetworkException{
		if ( intervalTime < 0 ) {
			throw new FoodNetworkException("Expected non-negative integer for interval length");
		}
		this.intervalTime = intervalTime;
	}
	
	@Override
	public String toString() {
		return "Interval [intervalTime=" + intervalTime + ", getRequest()=" + getRequest() + ", getMessageTimestamp()="
				+ getMessageTimestamp() + "]";
	}

	@Override
	public String getRequest() {
		return Interval.getRequestType();
	}
	
	public void encode( MessageOutput out ) throws FoodNetworkException {
		super.encode(out);
		out.writeString(Interval.getRequestType());
		out.writeInt(getIntervalTime());
		out.writeChar(ENDLINECHAR);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + intervalTime;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (!super.equals(obj)){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Interval other = (Interval) obj;
		if (intervalTime != other.intervalTime){
			return false;
		}
		return true;
	}

}
