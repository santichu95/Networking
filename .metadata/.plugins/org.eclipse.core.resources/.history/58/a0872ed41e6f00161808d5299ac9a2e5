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
		MealType result = null;
		switch(code) {
		case 'B':
			result = Breakfast;
			break;
		case 'L':
			result = Lunch;
			break;
		case 'D':
			result = Dinner;
			break;
		case 'S':
			result = Snack;
		}
		return result;
	}
	
	/** get code for meal type
	 * 	
	 * @return meal type code
	 */
	public char getMealTypeCode(){
		char result = ' ';
		if( this.equals(Breakfast) ){
			result = 'B';
		} else if( this.equals(Lunch) ){
			result = 'L';
		} else if( this.equals(Dinner) ){
			result = 'D';
		} else if( this.equals(Snack) ){
			result = 'S';
		}
		return result;
	}
}
