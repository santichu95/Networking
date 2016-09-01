/************************************************
*
* Author: Santiago Andaluz Ruiz
* Assignment: Program 0: Fatbat
* Class: CSI 4321 Data Comm
*
************************************************/
package foodnetwork.serialization;

import java.io.IOException;

/** Represents FoodItem and provides serialization/deserialization
 * 
 * @author Santiago Andaluz Ruiz
 *
 */
public class FoodItem {
	
	String name;
	MealType type;
	long calories;
	String fat;
	
	/**
	 * @param in deserialization of input source
	 * @throws FoodNetworkException if deserialization of validation failure
	 */
	public FoodItem(MessageInput in) throws FoodNetworkException {
		try {
			name = in.readName();
			type = in.readType();
			calories = in.readCal();
			fat = in.readFat();
		} catch (FoodNetworkException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**Constructs food item with set values
	 * @param name name of food item
	 * @param mealType type of meal
	 * @param calories number of calories in food item
	 * @param fat grams of fat in food item
	 */
	public FoodItem(String name, MealType mealType, long calories, String fat){
		
		this.name = name;
		this.type = mealType;
		this.calories = calories;
		this.fat = fat;
	}
	
	/** Serializes food item
	 * @param out serialization output destination
	 */
	public void encode(MessageOutput out){
		try {
			out.writeName(name);
			out.writeType(type);
			out.writeCal(calories);
			out.writeFat(fat);} 
		catch (IOException e) {
			System.err.println("Incorrect data");
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj){
		Boolean ans = false;
		
		if ( this.name.equals(((FoodItem) obj).getName()) ) {
			if ( this.fat.equals(((FoodItem) obj).getFat()) ) {
				if ( this.calories == ((FoodItem) obj).getCalories() ) {
					if ( this.type == ((FoodItem) obj).getMealType() ) {
						ans = true;
					}
				}
			}
		}
		
		return ans;
	}
	
	/** calories
	 * @return calories
	 */
	public long getCalories(){
		return calories;
	}
	
	/** Returns grams of fat
	 * @return grams of fat
	 */
	public String getFat(){
		return fat;
	}
	
	/**Returns meal type
	 * @return meal type
	 */
	public MealType getMealType(){
		return type;
	}
	
	/**Returns name
	 * @return name
	 */
	public String getName(){
		return name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode(){
		return super.hashCode();
	}
	
	/**Sets fat
	 * @param fat new grams of fat
	 */
	public void setFat(String fat){
		this.fat = fat;
	}
	
	/**Sets meal type
	 * @param mealType new meal type
	 */
	public void setMealType(MealType mealType){
		this.type = mealType;
	}
	
	/**Sets name
	 * @param name new name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return name + "Meal: " + type.getMealTypeCode() + " Calories: " + calories +
				" Grams of fat: " + fat;
	}
}
