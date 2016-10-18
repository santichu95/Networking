/************************************************
*
* Author: Santiago Andaluz Ruiz
* Assignment: Program 0: Fatbat
* Class: CSI 4321 Data Comm
*
************************************************/
package foodnetwork.serialization;

/**
 * Exception for food network handling
 * 
 * @author Santiago Andaluz Ruiz
 *
 */
public class FoodNetworkException extends java.lang.Exception {

	//Version ID of FoodNetwork Exception for FN1.0
    private static final long serialVersionUID = 1810537222285527822L;
    
    /**
     * Constructs food network exception with null cause
     * 
     * @param message
     *            exception message
     */
    public FoodNetworkException(String message) {
        super(message);
    }

    /**
     * Constructs food network exception
     * 
     * @param message
     *            exception message
     * @param cause
     *            exception cause
     */
    public FoodNetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
