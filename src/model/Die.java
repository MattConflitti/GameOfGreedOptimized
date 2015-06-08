package model;

/**
 * Represents one die (singular of dice) with faces showing values between 1 and 6.
 * 
 * @author Lewis/Loftus
 */
public class Die {
	
	/** maximum face value */
	private static final int MAX = 6;

	/** current value showing on the die */
	private int faceValue;
	
	/** value if die is available */
	private boolean isAvailable;

	/**
	 * Constructs a Die instance with a face value of 1. avail = true
	 */
	public Die() {
		faceValue = 1;
		isAvailable = true;
	}

	/**
	 * Computes a new face value for this die and returns the result.
	 * 
	 * @return face value of die
	 */
	public int roll() {
		faceValue = (int) (Math.random() * MAX) + 1;
		return faceValue;
	}

	/**
	 * Sets the face value of the die.
	 * 
	 * @param value an int indicating the face value of the die
	 */
	public void setFaceValue(int value) {
		if (value > 0 && value <= MAX) {
			faceValue = value;
		}
	}

	/**
	 * Returns the face value of the die.
	 * 
	 * @return the face value
	 */
	public int getFaceValue() {
		return faceValue;
	}

	/**
	 * Sets die availability
	 * 
	 * @param true or false
	 */
	public void setIsAvailable(boolean b) {
		isAvailable = b;
	}
	
	/**
	 * Returns the status of availability
	 * 
	 * @return isAvailable
	 */
	public boolean getIsAvailable() {
		return isAvailable;
	}
	
	/**
	 * Returns a string representation of this die.
	 * 
	 * @return string form of this object
	 */
	@Override
	public String toString() {
		return Integer.toString(faceValue);
	}
}