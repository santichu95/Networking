/************************************************
*
* Author: Santiago Andaluz Ruiz
* Assignment: Program 0: Fatbat
* Class: CSI 4321 Data Comm
*
************************************************/
package foodnetwork.serialization;

import java.io.IOException;

/**
 * Represents FoodItem and provides serialization/deserialization
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
     *            deserialization of input source
     * @throws FoodNetworkException
     *             if deserialization of validation failure
     */
    public FoodItem(MessageInput in) throws FoodNetworkException, IOException {
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

    /**
     * Constructs food item with set values
     * 
     * @param name
     *            name of food item
     * @param mealType
     *            type of meal
     * @param calories
     *            number of calories in food item
     * @param fat
     *            grams of fat in food item
     * @throws FoodNetworkException 
     */
    public FoodItem(String name, MealType mealType, long calories, String fat) throws FoodNetworkException {

        if ( name == null ){
            throw new FoodNetworkException("Expected non-null string for name parameter");
        }
        if ( mealType == null ) {
            throw new FoodNetworkException("Expected non-null string for meal type parameter");
        }
        if ( fat == null ) {
            throw new FoodNetworkException("Expected non-null string for fat parameter");
        }
        
        this.name = name;
        this.type = mealType;
        this.calories = (long)calories;
        this.fat = fat;
    }

    /**
     * Serializes food item
     * 
     * @param out
     *            serialization output destination
     */
    public void encode(MessageOutput out) {
        try {
            out.writeName(name);
            out.writeType(type);
            out.writeCal(calories);
            out.writeFat(fat);
        } catch (IOException e) {
            System.err.println("Incorrect data");
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FoodItem other = (FoodItem) obj;
        if (calories != other.calories)
            return false;
        if (fat == null) {
            if (other.fat != null)
                return false;
        } else if (!fat.equals(other.fat))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    /**
     * calories
     * 
     * @return calories
     */
    public long getCalories() {
        return calories;
    }

    /**
     * Returns grams of fat
     * 
     * @return grams of fat
     */
    public String getFat() {
        return fat;
    }

    /**
     * Returns meal type
     * 
     * @return meal type
     */
    public MealType getMealType() {
        return type;
    }

    /**
     * Returns name
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (calories ^ (calories >>> 32));
        result = prime * result + ((fat == null) ? 0 : fat.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /**
     * Sets fat
     * 
     * @param fat
     *            new grams of fat
     */
    public void setFat(String fat) {
        this.fat = fat;
    }

    /**
     * Sets meal type
     * 
     * @param mealType
     *            new meal type
     */
    public void setMealType(MealType mealType) {
        this.type = mealType;
    }

    /**
     * Sets name
     * 
     * @param name
     *            new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets calories
     * 
     * @param cal
     *            new calories
     */
    public void setCalories(long cal) {
        calories = cal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "Name: " + name + " MealType: " + type.getMealTypeCode() + " Calories: " + calories + " Grams of fat: "
                + fat;
    }
}
