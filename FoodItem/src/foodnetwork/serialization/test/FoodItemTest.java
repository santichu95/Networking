/************************************************
*
* Author: Santiago Andaluz Ruiz
* Assignment: Program 0: Fatbat
* Class: CSI 4321 Data Comm
*
************************************************/
package foodnetwork.serialization.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.MealType;
import foodnetwork.serialization.MessageInput;

public class FoodItemTest {

	MealType meal;
	
	@Test
	public void testReadFat() throws FoodNetworkException {
		
	}
	
	
	@Test
	public void testReadMealType() throws FoodNetworkException {
		FileInputStream in;
		
		try {
			in = new FileInputStream(new File("breakfast"));
			MessageInput test = new MessageInput(in);
			test.readName();
			assertEquals(MealType.Breakfast, test.readType());
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadInt() throws FoodNetworkException {
		FileInputStream in;
		try {
			in = new FileInputStream(new File("breakfast"));
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
			FileInputStream in = new FileInputStream(new File("breakfast"));
			MessageInput test = new MessageInput(in);
			assertEquals("plum",test.readName());
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found");
			e.printStackTrace();
		}
	}
	
	@Test(expected=FoodNetworkException.class)
	public void testGetMealTypeException() throws FoodNetworkException {
		MealType.getMealType('A');
	}

	@Test
	public void testGetMealTypeBreakfast() throws FoodNetworkException {
		assertEquals(MealType.Breakfast, MealType.getMealType('B'));
	}	
	
	@Test
	public void testGetMealTypeLunch() throws FoodNetworkException {
		assertEquals(MealType.Lunch, MealType.getMealType('L'));
	}	
	
	@Test
	public void testGetMealTypeDinner() throws FoodNetworkException {
		assertEquals(MealType.Dinner, MealType.getMealType('D'));
	}	
	
	@Test
	public void testGetMealTypeSnack() throws FoodNetworkException {
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
