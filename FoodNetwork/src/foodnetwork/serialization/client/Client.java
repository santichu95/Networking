package foodnetwork.serialization.client;

import java.net.Socket;
import java.util.Scanner;

import foodnetwork.serialization.AddFood;
import foodnetwork.serialization.ErrorMessage;
import foodnetwork.serialization.FoodItem;
import foodnetwork.serialization.FoodList;
import foodnetwork.serialization.FoodMessage;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.GetFood;
import foodnetwork.serialization.MealType;
import foodnetwork.serialization.MessageInput;
import foodnetwork.serialization.MessageOutput;

import java.io.IOException;

/**Food Network client that interacts with a user and a given server.
 * 
 * @author Santiago Andaluz Ruiz
 *
 */
public class Client {

	/**
	 * @param args the server name and the socket to use
	 * @throws IOException if I/O error closing socket
	 */
	public static void main(String[] args) throws IOException  {
		//Check for valid parameters
		if ( (args.length != 2) ) { // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Server> [<Port>]");
		}
		
		String server = args[0];       // Server name or IP address
		// Convert argument String to bytes using the default character encoding
		int servPort = Integer.parseInt(args[1]);
		
		// Create socket that is connected to server on specified port
		Socket socket = null;

		//Attempt to connect to socket
		try {
			socket = new Socket(server, servPort);
		} catch (IOException e1) {
			System.err.println("Unable to communicate: " + e1.getMessage());
			return;
		}

		System.out.println("Connected to " + server + " on port: " + servPort);

		//Creating input and output streams
		MessageInput in = null;
		MessageOutput out = null;

		try {
			in = new MessageInput(socket.getInputStream());
			out = new MessageOutput(socket.getOutputStream());
		} catch (IOException e1) {
			System.err.println("Unable to communicate: " + e1.getMessage());
			socket.close();
			return;
		}
		
		Scanner kb = new Scanner(System.in);
		String cont = "y";

		//While the user wants to send requests
		while ( cont.equals("y") ) {
			String userReq;
			long timestamp;
			FoodMessage req = null, response = null;
			
			//Prompt for request
			System.out.print("Request ( ADD | GET )>");
			userReq = kb.nextLine();
			if ( !userReq.equals(AddFood.getRequestType()) && !userReq.equals(GetFood.getRequestType()) ) {
				System.err.println("Invalid user input: Expected ADD or GET, recieved: " + userReq);
				continue;
			}

			//Get the current time of request
			timestamp = System.currentTimeMillis();

			try {
				//the request is ADD
				if ( userReq.equals(AddFood.getRequestType()) ){
	
					//Attributes for food item
					String foodName, 
							fat,
							calories,
							mealtype;
					
					//dummy food to validate input
					FoodItem food = new FoodItem();
	
					//Prompt for food item attributes
					System.out.print("Name>");
					foodName = kb.nextLine();
					food.setName(foodName);
					
					System.out.print("Meal type(B,L,D,S)>");
					mealtype = kb.nextLine();
					food.setMealType(MealType.getMealType(mealtype.charAt(0)));
					
					System.out.print("Calories>");
					calories = kb.nextLine();
					food.setCalories(Long.parseLong(calories));
					
					System.out.print("Fat>");
					fat = kb.nextLine();
					food.setFat(fat);
				
					req = new AddFood(timestamp, food);
					
				} else { //the request is GET
					//Generate GetFood request
					req = new GetFood(timestamp);
				}
			} catch (FoodNetworkException e) {
				System.err.println("Invalid user input: " + e.getMessage());
				continue;
			} catch (NumberFormatException e2 ) {
				System.err.println("Invalid user input: " + e2);
				continue;
			}

			
			//Send request to the server
			try {
				req.encode(out);
			} catch (FoodNetworkException e) {
				System.err.println("Unable to communicate: " + e.getMessage());
				socket.close();
				kb.close();
				return;
			}
		
			//Read in the message from the server
			try {
				response = FoodMessage.decode(in);
			} catch (IOException e1) {
				System.err.println("Unable to communicate: " + e1.getMessage());
				socket.close();
				kb.close();
				return;
			} catch (FoodNetworkException e) {
				System.err.println("Invalid message: " + e.getMessage());
				socket.close();
				kb.close();
				return;
			}
			
			if ( response.getRequest().equals(ErrorMessage.getRequestType()) ) {
				System.err.println("Error: " + (((ErrorMessage) response).getErrorMessage()) );
				continue;
			}

			//For add and get messages expect to get a list message back
			if ( userReq.equals(AddFood.getRequestType()) || userReq.equals(GetFood.getRequestType()) ) {
				if ( !response.getRequest().equals(FoodList.getRequestType())){
					System.err.println("Unexpected message: " + response);
					continue;
				}
			}

			System.out.println(response);
			
			//Checks if the user wants to continue
			do {
				System.out.print("Continue (y/n)");
				cont = kb.next();
				kb.nextLine();
				//Checks for valid input
				if ( !cont.equals("y") && !cont.equals("n") ) {
					System.err.println("Invalid user input: Expected y or n, recieved: " + cont);
				}
			}while ( !cont.equals("y") && !cont.equals("n") );
		}
		
		kb.close();
		socket.close();
	}
		
}