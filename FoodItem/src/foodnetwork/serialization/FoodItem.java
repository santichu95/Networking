package foodnetwork.serialization;

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
	 * @param in
	 */
	public FoodItem(MessageInput in) {
		try {
			name = in.readName();
			type = in.readType();
			calories = in.readCal();
			fat = in.readFat();
		} catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param name
	 * @param mealType
	 * @param calories
	 * @param fat
	 */
	public FoodItem(String name, MealType mealType, long calories, String fat){
		
		this.name = name;
		this.type = mealType;
		this.calories = calories;
		this.fat = fat;
	}
	
	/**
	 * @param out
	 */
	public void encode(MessageOutput out){
		out.writeName(name);
		out.writeType(type);
		out.writeCal(calories);
		out.writeFat(fat);
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
		/*
		 * 
		if ( this.name.equals(((FoodItem) obj).getName()) && this.fat.equals(((FoodItem) obj).getFat()) )
				&& this.calories == ((FoodItem) obj).getCalories() && this.type == ((FoodItem) obj).getMealType() ) {
						ans = true;
					}
				}
			}
		}
		 */
		
		return ans;
	}
	
	/**
	 * @return
	 */
	public long getCalories(){
		return calories;
	}
	
	/**
	 * @return
	 */
	public String getFat(){
		return fat;
	}
	
	/**
	 * @return
	 */
	public MealType getMealType(){
		return type;
	}
	
	/**
	 * @return
	 */
	public String getName(){
		return name;
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
		this.fat = fat;
	}
	
	/**
	 * @param mealType
	 */
	public void setMealType(MealType mealType){
		this.type = mealType;
	}
	
	/**
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "";
	}
}
