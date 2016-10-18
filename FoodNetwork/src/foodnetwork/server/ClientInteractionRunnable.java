/***************************************************
 *
 * Author:      Santiago Andaluz
 * Assignment:  Program 3
 * Class:       CSI 4321
 *
 ***************************************************/
package foodnetwork.server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

import edu.baylor.googlefit.FoodManager;
import foodnetwork.serialization.AddFood;
import foodnetwork.serialization.ErrorMessage;
import foodnetwork.serialization.FoodItem;
import foodnetwork.serialization.FoodList;
import foodnetwork.serialization.FoodMessage;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.GetFood;
import foodnetwork.serialization.Interval;
import foodnetwork.serialization.MessageInput;
import foodnetwork.serialization.MessageOutput;

public class ClientInteractionRunnable implements Runnable {

	/**
	 * Socket where the client is connected
	 */
	private Socket cSocket;
	/**
	 * FoodManager to handle all the interactions with the database
	 */
	private FoodManager mgr;
	/**
	 * Logger to log any issues that arise
	 */
	private Logger LOG;
	/**
	 * Handle input from client
	 */
	private MessageInput in;
	/**
	 * Handle output from client
	 */
	private MessageOutput out;

	/**Prepare to run the thread
	 * @param cltSocket socket where the client is connected
	 * @param mgr FoodManager to handle all interactions with the database
	 * @param LOG Logger to log any issues to the log file
	 */
	public ClientInteractionRunnable(Socket cltSocket, FoodManager mgr, Logger LOG) {
		this.cSocket = cltSocket;
		this.mgr = mgr;
		this.LOG = LOG;
		try {
			in = new MessageInput(cSocket.getInputStream());
			out = new MessageOutput(cSocket.getOutputStream());
		} catch (IOException e) {
			LOG.severe("Error while creating streams: " + e.getMessage());
			closeClean();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		LOG.info("Handling client " + cSocket.getInetAddress() + "-" + cSocket.getPort() 
			+ " with thread id " + Thread.currentThread().getId() + "\n");
		while ( true ){
			try{
				FoodMessage req = getRequest();
				if ( cSocket.isClosed() ) {
					return;
				}
				processRequest(req);
				if ( cSocket.isClosed() ) {
					return;
				}
			} catch ( FoodNetworkException e ) {
				LOG.fine("Error sending error message to client, terminating connection\n");
				closeClean();
				break;
			}
		}
	}
	
	/**
	 * Close connections to the client and log the event
	 */
	private void closeClean() {
		if ( !cSocket.isClosed() ) {
			LOG.info("Connetion terminated");
			try {
				cSocket.close();
			} catch (IOException e) {
				LOG.severe("Error attempting to close socket");
			}		
		}
	}

	/**Process the request from the client
	 * @param req client's request
	 * @throws FoodNetworkException 
	 */
	private void processRequest(FoodMessage req) throws FoodNetworkException {
		if (req.getRequest() == AddFood.getRequestType()) {
			processAddFood(req);	
		} else if (req.getRequest() == GetFood.getRequestType()) {
			processGetFood();
		} else if ( req.getRequest() == Interval.getRequestType() ) {
			processIntervalFood(req);
		} else {
			sendErrorMessage(req.getMessageTimestamp(), "Unexpected message type: " + req.getRequest());
		}
	}
	/** Sends an error message to the client
	 * @param ts timestamp for the error message to send
	 * @param message error message
	 * @throws FoodNetworkException serialization failure
	 */
	private void sendErrorMessage(long ts, String message) throws FoodNetworkException {
		ErrorMessage temp = new ErrorMessage(ts, message);
		temp.encode(out);
		LOG.info("sent to " + cSocket.getInetAddress() + "-" + cSocket.getPort() + " " + temp.toString() );
	}

	/**Process and interval food request
	 * @param req the actual interval request from the client
	 * @throws FoodNetworkException 
	 */
	private void processIntervalFood(FoodMessage req) throws FoodNetworkException {
		try {
			sendMessage(generateFoodList(((Interval)req).getIntervalTime()));
		} catch (FoodNetworkException e) {
			sendErrorMessage(System.currentTimeMillis(), "Server side error");
			LOG.severe("Server side error" + e.getMessage());
			closeClean();
		}
	}

	/**Process given AddFood item
	 * @param req the Add food request from the client
	 * @throws FoodNetworkException if validation fails
	 */
	private void processAddFood(FoodMessage req ) throws FoodNetworkException {
		FoodItem tempFood = ((AddFood)req).getFoodItem();
		try {
			mgr.addFood(tempFood.getName(), tempFood.getMealType(),
					tempFood.getCalories(), tempFood.getFat());
			sendMessage(generateFoodList());
		} catch (FoodNetworkException e) {
			sendErrorMessage(System.currentTimeMillis(), "Server side error");
			LOG.severe("Server side error" + e.getMessage());
			closeClean();
		}
	}
	
	/**Generate a food list from the list on the database
	 * @param interval interval of time to get the food items
	 * @return a FoodList message containing all the food on the database
	 * @throws FoodNetworkException if validation fails
	 */
	private FoodMessage generateFoodList(int... interval) throws FoodNetworkException{
		if ( interval.length > 1 ) {
			LOG.severe("Recieved too many arguemnts for generateList(), expected 1 or 0");
			throw new FoodNetworkException("Recieved too many arguemnts for generateList(), expected 1 or 0");
		}
		FoodMessage fm = null;
		try {
			fm = new FoodList(System.currentTimeMillis(), mgr.getLastModified());
			List<FoodItem> list = interval.length == 1 ? mgr.getFoodItems(interval[0]) : mgr.getFoodItems();
			for ( FoodItem var : list ) {
				((FoodList)fm).addFoodItem(var);
			}
		} catch (FoodNetworkException e) {
			sendErrorMessage(System.currentTimeMillis(), "Server side error");
			LOG.severe("Server side error" + e.getMessage());
			closeClean();
		}
		return fm;
	}

	/**Process given get food request
	 * @throws FoodNetworkException if validation fails while sending messages
	 */
	private void processGetFood() throws FoodNetworkException {
		try {
			sendMessage(generateFoodList());
		} catch (FoodNetworkException e) {
			sendErrorMessage(System.currentTimeMillis(), "Server side error");
			LOG.severe("Server side error" + e.getMessage());
			closeClean();
		}
		
	}

	/**Send given FoodMessage to client
	 * @param fm the FoodMessage to send the client
	 * @throws FoodNetworkException if serialization fails during encode
	 */
	private void sendMessage(FoodMessage fm) throws FoodNetworkException {
		fm.encode(out);
		LOG.info("sent to " + cSocket.getInetAddress() + "-" + cSocket.getPort() + " " + fm.toString() );
	}
	
	/**Get the request from the client
	 * @return the request FoodMessage from the client	
	 * @throws FoodNetworkException 
	 */
	private FoodMessage getRequest() throws FoodNetworkException {
		try {
			return FoodMessage.decode(in);
		} catch (FoodNetworkException e) {
			sendErrorMessage(System.currentTimeMillis(), e.getMessage());
			closeClean();
			return null;
		} catch (IOException e) {
			closeClean();
			return null;
		}
	}

}
