/************************************************
*
* Author: Santiago Andaluz Ruiz
* Assignment: Program 0: Fatbat
* Class: CSI 4321 Data Comm
*
************************************************/
package foodnetwork.serialization.test;

import static org.junit.Assert.*;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.junit.Test;

import foodnetwork.serialization.FoodItem;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.MealType;
import foodnetwork.serialization.MessageInput;
import foodnetwork.serialization.MessageOutput;

public class FoodItemTest {

    MealType meal;
    MessageInput testIn;
    MessageOutput testOut;
    FileInputStream in;
    FoodItem foodOne, foodTwo;

    PipedInputStream inPipe;
    PipedOutputStream outPipe;
    
    
    @Test(expected = FoodNetworkException.class)
    public void testReadCalException2() throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        outPipe.write( "2.8".toString().getBytes() );
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readCal();
        
        inPipe.close();
    }
    
    
    @Test(expected = EOFException.class)
    public void testReadCalException1() throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        outPipe.write( (((Long)1920L).toString()).getBytes() );
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        long result = testIn.readCal();
        
        assertEquals(1920L, result);
        
        inPipe.close();
    }
    
    @Test
    public void testReadType5() throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        outPipe.write("S".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        meal = testIn.readType();
        
        assertEquals(MealType.Snack, meal);
        
        inPipe.close();
    }
    
    @Test
    public void testReadType4() throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        outPipe.write("D".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        meal = testIn.readType();
        
        assertEquals(MealType.Dinner, meal);
        inPipe.close();
    }
    
    @Test
    public void testReadType3() throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        outPipe.write("L".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        meal = testIn.readType();
        
        assertEquals(MealType.Lunch, meal);
        inPipe.close();
    }
    
    @Test
    public void testReadType2() throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        outPipe.write("B".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        meal = testIn.readType();
        
        assertEquals(MealType.Breakfast, meal);
        inPipe.close();
    }

    @Test(expected = FoodNetworkException.class)
    public void testReadTypeException6 () throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        outPipe.write("A".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readType();
        inPipe.close();
    }


    @Test(expected = FoodNetworkException.class)
    public void testReadTypeException5 () throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        outPipe.write("s".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readType();
        inPipe.close();
    }

    @Test(expected = FoodNetworkException.class)
    public void testReadTypeException4 () throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        outPipe.write("d".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readType();
        inPipe.close();
    }

    @Test(expected = FoodNetworkException.class)
    public void testReadTypeException3 () throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        outPipe.write("l".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readType();
        inPipe.close();
    }

    @Test(expected = FoodNetworkException.class)
    public void testReadTypeException2 () throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        outPipe.write("b".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readType();
        inPipe.close();
    }
    

    @Test(expected = EOFException.class)
    public void testReadTypeException1 () throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        outPipe.write("".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readType();
        inPipe.close();
    }
    

    @Test(expected = FoodNetworkException.class)
    public void testReadIntException2 () throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        outPipe.write("0123456A789 ".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readInt();
        inPipe.close();
    }
    

    @Test(expected = EOFException.class)
    public void testReadIntException1 () throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        outPipe.write("0123456789".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readInt();
        inPipe.close();
    }
    
    @Test(expected = FoodNetworkException.class)
    public void testReadNameException7() throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);
        
        outPipe.write("5 Plum�".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readName();
        inPipe.close();
    }
    
    
    @Test(expected = EOFException.class)
    public void testReadNameException6() throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);
        
        outPipe.write("".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readName();
        inPipe.close();
    }
    
    @Test(expected = EOFException.class)
    public void testReadNameException5() throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);
        
        outPipe.write("4".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readName();
        inPipe.close();
    }

    @Test(expected = FoodNetworkException.class)
    public void testReadNameException4() throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);
        
        outPipe.write("4a Plum".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readName();
        inPipe.close();
    }

    @Test(expected = FoodNetworkException.class)
    public void testReadNameException3() throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);
        
        outPipe.write("4Plum".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readName();
        inPipe.close();
    }

    @Test(expected = FoodNetworkException.class)
    public void testReadNameException2() throws IOException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);
        
        outPipe.write("A Plum".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readName();
        inPipe.close();
    }

    @Test(expected = EOFException.class)
    public void testReadNameException1() throws IOException, EOFException, FoodNetworkException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);
        
        outPipe.write("5 Plum".getBytes());
        
        outPipe.close();
        
        testIn = new MessageInput(inPipe);
        
        testIn.readName();
        inPipe.close();
        
    }

    @Test
    public void testWriteTypeBreakfast() throws IOException {
        byte[] buffer = new byte[1];
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);
        
        testOut = new MessageOutput(outPipe);
        
        testOut.writeType(MealType.Breakfast);
        
        outPipe.close();
        
        inPipe.read(buffer, 0, 1);

        assertEquals( 'B', ((char) buffer[0] ));

        inPipe.close();
    }

    @Test
    public void testWriteFat() throws IOException {
        
        byte[] buffer;
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);
        
        testOut = new MessageOutput(outPipe);
        
        testOut.writeFat("3.8");
        
        outPipe.close();

        //Size of "3.8"
        buffer = new byte[3];
        
        inPipe.read(buffer, 0, 3);
        
        assertEquals("3.8", new String(buffer) );
        
        inPipe.close();

    }

    @Test
    public void testEquals() {
        foodOne = new FoodItem("Plum", MealType.Breakfast, 50L, "3.8");
        foodTwo = new FoodItem("Plum", MealType.Breakfast, 50L, "3.8");

        assertTrue(foodOne.equals(foodTwo));

        foodTwo.setName("Apple");

        assertFalse(foodOne.equals(foodTwo));
    }

    @Test
    public void testGetCalories() {
        foodOne = new FoodItem("Plum", MealType.Breakfast, 50L, "3.8");
        assertEquals(50L, foodOne.getCalories());
    }

    @Test
    public void testGetFat() {
        foodOne = new FoodItem("Plum", MealType.Breakfast, 50L, "3.8");
        assertEquals("3.8", foodOne.getFat());
    }

    @Test
    public void testGetMealType() {
        foodOne = new FoodItem("Plum", MealType.Breakfast, 50L, "3.8");
        assertEquals(MealType.Breakfast, foodOne.getMealType());
    }

    @Test
    public void testGetName() {
        foodOne = new FoodItem("Plum", MealType.Breakfast, 50L, "3.8");
        assertEquals("Plum", foodOne.getName());
    }

    @Test
    public void testSetCalories() {
        foodOne = new FoodItem("Plum", MealType.Breakfast, 50L, "3.8");
        foodOne.setCalories(100L);
        assertEquals(100L, foodOne.getCalories());
    }

    @Test
    public void testSetFat() {
        foodOne = new FoodItem("Plum", MealType.Breakfast, 50L, "3.8");
        foodOne.setFat("10.99");
        assertEquals("10.99", foodOne.getFat());
    }

    @Test
    public void testSetMealType() {
        foodOne = new FoodItem("Plum", MealType.Breakfast, 50L, "3.8");
        foodOne.setMealType(MealType.Snack);
        assertEquals(MealType.Snack, foodOne.getMealType());
    }

    @Test
    public void testSetName() {
        foodOne = new FoodItem("Plum", MealType.Breakfast, 50L, "3.8");
        foodOne.setName("Apple");
        assertEquals("Apple", foodOne.getName());
    }

    @Test
    public void testToString() {
        foodOne = new FoodItem("Plum", MealType.Breakfast, 50L, "3.8");
        assertEquals("Name: Plum MealType: B Calories: 50 Grams of fat: 3.8", foodOne.toString());

    }

    @Test
    public void testWriteName() throws IOException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        testOut = new MessageOutput(outPipe);

        testOut.writeName("plum");

        outPipe.close();

        byte[] buffer = new byte[1];
        String temp = "";
        boolean endLoop = false;

        while (!endLoop && 1 == inPipe.read(buffer, 0, 1)) {
            if (((buffer[0] <= 'z' && buffer[0] >= 'a') || (buffer[0] <= 'Z' && buffer[0] >= 'A'))) {
                temp += ((char) buffer[0]);
            } else {
                endLoop = true;
            }
        }

        assertEquals("plum", temp);

        inPipe.close();

    }

    @Test
    public void testWriteCal() throws IOException {
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        testOut = new MessageOutput(outPipe);

        testOut.writeCal(50L);

        outPipe.close();

        long cal;
        byte[] buffer = new byte[1];
        String tempLong = "";
        boolean endLong = false;

        while (!endLong && 1 == inPipe.read(buffer, 0, 1)) {
            if (buffer[0] <= '9' && buffer[0] >= '0') {
                tempLong += ((char) buffer[0]);
            } else {
                endLong = true;
            }
        }

        cal = Long.parseLong(tempLong);

        assertEquals(50L, cal);

        inPipe.close();
    }

    @Test
    public void testReadEntireFoodItem() throws FoodNetworkException, IOException {
        in = new FileInputStream(new File("breakfast"));
        testIn = new MessageInput(in);

        foodOne = new FoodItem(testIn);

        /* Version Two */
        assertEquals("plum", foodOne.getName());
        assertEquals(MealType.Breakfast, foodOne.getMealType());
        assertEquals(50L, foodOne.getCalories());
        assertEquals("3.8", foodOne.getFat());
    }

    @Test
    public void testReadFat() throws FoodNetworkException, IOException {
        in = new FileInputStream(new File("fat"));
        testIn = new MessageInput(in);
        assertEquals("3.8", testIn.readFat());
    }

    @Test
    public void testReadMealType() throws FoodNetworkException, IOException {
        in = new FileInputStream(new File("type"));
        testIn = new MessageInput(in);
        assertEquals(MealType.Breakfast, testIn.readType());
    }

    @Test
    public void testReadInt() throws FoodNetworkException, IOException {
        in = new FileInputStream(new File("breakfast"));
        testIn = new MessageInput(in);
        assertEquals(4, testIn.readInt());
    }

    @Test
    public void testReadName() throws FoodNetworkException, IOException {
        in = new FileInputStream(new File("breakfast"));
        testIn = new MessageInput(in);
        assertEquals("plum", testIn.readName());
    }

    @Test(expected = FoodNetworkException.class)
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
