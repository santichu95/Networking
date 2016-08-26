package foodnetwork.serialization;

/**	Meal Type
 * 
 * @author Santiago Andaluz
 *
 */
public enum MealType {
	/**
	 * Breakfast
	 */
	Breakfast, /**
	 * Lunch
	 */
	Lunch, /**
	 * Dinner
	 */
	Dinner, /**
	 * Snack
	 */
	Snack;	
	
	/**Get meal type for given code
	 * 
	 * @param code : code of meal type
	 * @return meal type corresponding to code
	 * @throws FoodNetworkException if bad code value
	 */
	public static MealType getMealType(char code){
		return null;
	}
	
	/** get code for meal type
	 * 	
	 * @return meal type code
	 */
	public char getMealTypeCode(){
		return 0;
	}
}
