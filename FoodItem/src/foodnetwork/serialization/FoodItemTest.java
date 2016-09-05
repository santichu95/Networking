package foodnetwork.serialization;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class FoodItemTest {

	MealType meal;
	@Test
	public void testReadInt() throws IOException {
		FileInputStream in;
		try {
			in = new FileInputStream(new File("data"));
			MessageInput test = new MessageInput(in);
			assertEquals(4,test.readInt());
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found");
			e.printStackTrace();
		}
	}

	@Test
	public void testReadName() throws FoodNetworkException{
		try {
			FileInputStream in = new FileInputStream(new File("data"));
			MessageInput test = new MessageInput(in);
			assertEquals("plum",test.readName());
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetMealTypeBreakfast() {
		assertEquals(MealType.Breakfast, MealType.getMealType('B'));
	}	
	
	@Test
	public void testGetMealTypeLunch() {
		assertEquals(MealType.Lunch, MealType.getMealType('L'));
	}	
	
	@Test
	public void testGetMealTypeDinner() {
		assertEquals(MealType.Dinner, MealType.getMealType('D'));
	}	
	
	@Test
	public void testGetMealTypeSnack() {
		assertEquals(MealType.Snack, MealType.getMealType('S'));
	}

	@Test
	public void testGetMealTypeCodeBreakfast() {
		meal = MealType.Breakfast;
		
		assertEquals('B', meal.getMealTypeCode());
	}
	
	@Test
	public void testGetMealTypeCodeLunch() {
		meal = MealType.Lunch;
		
		assertEquals('L', meal.getMealTypeCode());
	}
	
	@Test
	public void testGetMealTypeCodeDinner() {
		meal = MealType.Dinner;
		
		assertEquals('D', meal.getMealTypeCode());
	}
	
	@Test
	public void testGetMealTypeCodeSnack() {
		meal = MealType.Snack;
		
		assertEquals('S', meal.getMealTypeCode());
	}

}
