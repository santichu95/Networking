/***************************************************
 *
 * Author:      Santiago Andaluz
 * Assignment:  Program 3
 * Class:       CSI 4321
 *
 ***************************************************/
package foodnetwork.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.baylor.googlefit.FoodManager;
import edu.baylor.googlefit.GFitFoodManager;

import foodnetwork.serialization.FoodNetworkException;

public class Server {
	
	/**
	 * The project number associated with the fit API
	 */
	private final static String PROJECTNUMBER = "1098262557941";
	/**
	 * Path to the authentication JSON
	 */
	private final static String PATHTOAUTHENTICATION = "./FNAuth.json";
	/**
	 * The number of milliseconds for each client to wait to timeout
	 */
	private final static int TIMEOUTLENGTHMS = 50000;
	/**
	 * Logger to log any information about the server
	 */
	private final static Logger LOG = Logger.getLogger(Server.class.getName());
	
	/**Running the server 
	 * @param args port number to connect to, number of threads to create in thread pool
	 */
	public static void main(String[] args) {

		//Create handlers for the logger
		createHandlers(LOG);
		
		//Check for the correct amount of arguments
		if (args.length != 2) {
			LOG.severe("Wrong number of parameters");
		}

		//Thread pool containing n threads to handle client interactions
		//n is the second value passed in from the command line
		ExecutorService threadPool = Executors.newFixedThreadPool(Integer.parseInt(args[1]));
		
		//The socket for the server on the port that is specified on the command line
		ServerSocket servSock = connectServerSocket(args[0]);
		
		if ( servSock == null ){
			return;
		}
		
		//The food manager that handles all interactions between the server and the
		//list of food items in the database
		FoodManager mgr = createFoodManger(PROJECTNUMBER, PATHTOAUTHENTICATION);
		if ( mgr == null ) {
			LOG.severe("Unable to create FoodManager");
		}

		while (true) {
			Socket cltSocket = null;
			
			try {
				cltSocket = servSock.accept();
				cltSocket.setSoTimeout(TIMEOUTLENGTHMS);
			} catch (IOException e) {
				LOG.severe("I/O error while attempting to connect to client");
				continue;
			}
			
			threadPool.submit(new ClientInteractionRunnable(cltSocket, mgr, LOG));
		}
	}
	
	/**Create handler for the logger 
	 * @param log the Logger that will modify the handlers
	 */
	private static void createHandlers(Logger log) {
		try {
			log.setUseParentHandlers(false);
			log.addHandler(new FileHandler("./connections.log"));
			log.setLevel(Level.ALL);
			log.config("Finished Configuration");
		} catch (IOException e) {
			log.severe("Error configuring Logger");
		}
	}
	
	/**Connect the server to the given port number on this systems IP address
	 * @param portNum the port number to have the server connect to
	 * @return the ServerSocket that the server is using
	 */
	private static ServerSocket	connectServerSocket(String portNum){
		try {
			ServerSocket temp = new ServerSocket(Integer.parseInt(portNum));
			temp.setReuseAddress(true);
			return temp;
		} catch (NumberFormatException e) {
			LOG.severe("Unable to connect to the given port");
		} catch (IOException e) {
			LOG.severe("I/O issue while creating server socket");
		}
		return null;
	}
	
	/**Create a FoodManager that will store all of the FoodItems
	 * @param projNum the number of the google fit project
	 * @param pathToAuth the path to the authentication JSON
	 * @return a FoodManager that handles storing all the FoodItems
	 */
	private static FoodManager createFoodManger(String projNum, String pathToAuth){
		try {
			return new GFitFoodManager("1098262557941", "./FNAuth.json");
		} catch (FoodNetworkException e) { 
			LOG.severe("Unable to initial FoodManager with provided information");
		}
		return null;
	}

}
