package foodnetwork.serialization;

/** Represents FoodItem and provides serialization/deserialization
 * 
 * @author Santiago Andaluz Ruiz
 *
 */
public class FoodItem {
	/**
	 * @param in
	 */
	public FoodItem(MessageInput in) {
		
	}
	
	/**
	 * @param name
	 * @param mealType
	 * @param calories
	 * @param fat
	 */
	public FoodItem(String name, MealType mealType, long calories, String fat){
		
	}
	
	/**
	 * @param out
	 */
	public void encode(MessageOutput out){
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj){
		return false;
	}
	
	/**
	 * @return
	 */
	public long getCalories(){
		return 0;
	}
	
	/**
	 * @return
	 */
	public String getFat(){
		return "";
	}
	
	/**
	 * @return
	 */
	public MealType getMealType(){
		return null;
	}
	
	/**
	 * @return
	 */
	public String getName(){
		return "";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode(){
		return 0;
	}
	
	/**
	 * @param fat
	 */
	public void setFat(String fat){
		
	}
	
	/**
	 * @param mealType
	 */
	public void setMealType(MealType mealType){
		
	}
	
	/**
	 * @param name
	 */
	public void setName(String name){
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "";
	}
	
	
	
}
