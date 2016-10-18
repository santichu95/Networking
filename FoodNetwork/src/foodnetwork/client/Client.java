/***************************************************
 *
 * Author:      Santiago Andaluz
 * Assignment:  Program 2
 * Class:       CSI 4321
 *
 ***************************************************/
package foodnetwork.client;

import java.net.Socket;
import java.util.Scanner;

import foodnetwork.serialization.AddFood;
import foodnetwork.serialization.ErrorMessage;
import foodnetwork.serialization.FoodItem;
import foodnetwork.serialization.FoodList;
import foodnetwork.serialization.FoodMessage;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.GetFood;
import foodnetwork.serialization.Interval;
import foodnetwork.serialization.MealType;
import foodnetwork.serialization.MessageInput;
import foodnetwork.serialization.MessageOutput;

import java.io.Closeable;
import java.io.IOException;

/**Food Network client that interacts with a user and a given server.
 * 
 * @author Santiago Andaluz Ruiz
 *
 */
public class Client {
	private static boolean startOver = false;

	/**
	 * @param args the server name and the socket to use
	 * @throws IOException if I/O error closing socket
	 */
	public static void main(String[] args) throws IOException  {

		//Creating input and output streams
		MessageInput in = null;
		MessageOutput out = null;
		Scanner kb = null;
		Socket socket = null;
		try {
			socket = estConnection(args);
			in = new MessageInput(socket.getInputStream());
			out = new MessageOutput(socket.getOutputStream());
		
			kb = new Scanner(System.in);
			
			//While the user wants to send requests
			do {
				startOver = false;
				if ( !requestRoutine(kb,out) ) {
					closeClean(kb, socket);
					return;
				}
				if ( startOver ) {
					continue;
				}
				if ( !responseRoutine(kb, in) ) {
					closeClean(kb, socket);
					return;
				}
			}while ( askCont(kb) );
		} catch (IOException e1) {
			System.err.println("Unable to communicate: " + e1.getMessage());
			closeClean(socket);
			return;
		} 
		
		closeClean(kb, socket);
	}
	
	
	/**Asks the user if they still want to continue
	 * @param sc Scanner that holds the user input
	 * @return a valid answer that the user gave
	 */
	private static boolean askCont(Scanner sc){//Checks if the user wants to continue
		String conti;
		do {
			System.out.print("Continue (y/n)");
			conti = sc.next();
			sc.nextLine();
			//Checks for valid input
			if ( !conti.equals("y") && !conti.equals("n") ) {
				System.err.println("Invalid user input: Expected y or n, recieved: " + conti);
			}
		}while ( !conti.equals("y") && !conti.equals("n") );
		
		return conti.equals("y");
	}
	
	private static Socket estConnection(String[] args){
		//Check for valid parameters
		if ( (args.length != 2) ) { // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Server> [<Port>]");
		}
		
		String server = args[0];       // Server name or IP address
		// Convert argument String to bytes using the default character encoding
		int servPort = Integer.parseInt(args[1]);
		Socket socket = null;
		//Attempt to connect to socket
		try {
			socket = new Socket(server, servPort);
		} catch (IOException e1) {
			System.err.println("Unable to communicate: " + e1.getMessage());
			return null;
		}
		
		return socket;
	}
	
	private static FoodMessage getUserRequest(Scanner kb) throws FoodNetworkException{
		//Prompt for request
		System.out.print("Request ( ADD | GET | INTERVAL )>");
		String userReq = kb.nextLine();
		
		if ( !userReq.equals(AddFood.getRequestType()) && !userReq.equals(GetFood.getRequestType()) 
				&& !userReq.equals(Interval.getRequestType())) {
			System.err.println("Invalid user input: Expected ADD or GET or INTERVAL, recieved: " + userReq);
			return null;	
		}
		
		FoodMessage req = null;
		
		//the request is ADD
		if ( userReq.equals(AddFood.getRequestType()) ){
			//Generate AddFood request
			req = new AddFood(System.currentTimeMillis(), getUserFood(kb));
		} else if (userReq.equals(Interval.getRequestType())) {
			//generate interval request
			req = getInterval(kb);
		} else { //the request is GET
			//Generate GetFood request
			req = new GetFood(System.currentTimeMillis());
		}
		return req;
	}
	
	private static FoodMessage getInterval(Scanner kb) {
		System.out.print("Interval Length>");
		try {
			int interval = kb.nextInt();
			if ( interval < 0 ) {
				//error
			}
			return new Interval(System.currentTimeMillis(), interval);
		}catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	private static void closeClean(Closeable... obj) throws IOException {
		for ( Closeable var : obj ) {
			var.close();
		}
	}
	
	private static FoodItem getUserFood(Scanner kb){
		//Attributes for food item
		String foodName, 
				fat,
				calories,
				mealtype;
		
		//dummy food to validate input
		FoodItem food = new FoodItem();

		try {
			//Prompt for food item attributes
			System.out.print("Name>");
			foodName = kb.nextLine();
			food.setName(foodName);
			
			System.out.print("Meal type(B,L,D,S)>");
			mealtype = kb.nextLine();
			food.setMealType(MealType.getMealType(mealtype.charAt(0)));
			
			try {
				System.out.print("Calories>");
				calories = kb.nextLine();
				food.setCalories(Long.parseLong(calories));
			} catch (NumberFormatException e2 ) {
				System.err.println("Invalid user input: " + e2);
				return null;
			}
			
			System.out.print("Fat>");
			fat = kb.nextLine();
			food.setFat(fat);
		} catch (FoodNetworkException e) {
			return null;
		}
		return food;
	}
	
	private static boolean requestRoutine(Scanner kb, MessageOutput out){
		//Request
		FoodMessage req = null;
		try {
			req = getUserRequest(kb);
			if ( req == null ) {
				startOver = true;
				return true;
			}
		} catch (FoodNetworkException e) {
			System.err.println("Invalid user input: " + e.getMessage());
			startOver = true;
			return true;
		} 

		//Send request to the server
		try {
			req.encode(out);
		} catch (FoodNetworkException e) {
			System.err.println("Unable to communicate: " + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	private static boolean responseRoutine(Scanner kb, MessageInput in) throws IOException {

		FoodMessage response = null;
		//Read in the message from the server
		try {
			response = FoodMessage.decode(in);
		} catch (FoodNetworkException e) {
			System.err.println("Invalid message: " + e.getMessage());
			return false;
		}
		
		if ( response.getRequest().equals(ErrorMessage.getRequestType()) ) {
			System.err.println("Error: " + (((ErrorMessage) response).getErrorMessage()) );
			return true;
		}

		//For add and get messages expect to get a list message back
		if ( !response.getRequest().equals(FoodList.getRequestType())){
			System.err.println("Unexpected message: " + response);
			return true;
		}

		System.out.println(response);
		return true;
	}
}

	

