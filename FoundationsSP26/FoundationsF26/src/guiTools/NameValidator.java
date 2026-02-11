package guiTools;

public class NameValidator {
	/*-********************************************************************************************

	Syntax and formating check for name inputs 
	
	**********************************************************************************************/

	/**********
	 * <p> Title: NameValidator Class</p>
	 * 
	 * <p> Description: This static class supports the actions initiated by the ViewUserUpdate
	 * class. In this case, there is just one method, no constructors, and no attributes.</p>
	 *
	 */

	/*-********************************************************************************************/
	public static String name = "";		// The input being processed
	public static int nameIndexofError = -1;
	private static int state = 0;						// The current state value
	private static int nextState = 0;					// The next state value
	private static String inputLine = "";				// The input line
	private static char currentChar;					// The current character in the line
	private static int currentCharNdx;					// The index of the current character
	private static boolean running;	
	
	private static void moveToNextCharacter() {
		currentCharNdx++;
		if (currentCharNdx < inputLine.length())
			currentChar = inputLine.charAt(currentCharNdx);
		else {
			currentChar = ' ';
			running = false;
		}
	}
	
	
	
	public static String checkName(String input) {

	    if (input == null || input.isBlank()) {
	        return "Name cannot be empty.";
	    }

	    input = input.trim();

	    if (input.length() > 50) {
	        return "A name must be no more than 50 characters.";
	    }

	    state = 0;
	    inputLine = input;
	    currentCharNdx = 0;
	    currentChar = inputLine.charAt(0);
	    running = true;

	    while (running) {
	        nextState = -1;

	        switch (state) {
	            case 0:
	                if (Character.isLetter(currentChar)) {
	                    nextState = 1;
	                } else {
	                    running = false;
	                }
	                break;

	            case 1:
	                if (currentChar == '-') {
	                    nextState = 0;
	                } else if (Character.isLetter(currentChar)) {
	                    nextState = 1; // allow consecutive letters
	                } else {
	                    running = false;
	                }
	                break;
	        }

	        if (running) {
	            moveToNextCharacter();
	            state = nextState;
	        }
	    }

	    // If we stopped because we hit the end, currentCharNdx == inputLine.length()
	    boolean consumedAll = (currentCharNdx >= inputLine.length());

	    if (consumedAll && state == 1) {
	        return ""; // valid: ended after a letter
	    }

	    if (!consumedAll) {
	        return "Invalid character at position " + (currentCharNdx + 1) + ". Use only letters and '-'.";
	    }

	    // consumed all but ended in state 0 => ended right after '-'
	    return "Name cannot end with '-'.";
	}
}
