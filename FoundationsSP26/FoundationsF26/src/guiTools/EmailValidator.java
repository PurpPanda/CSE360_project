package guiTools;

/*-********************************************************************************************

Syntax and formating check for name inputs 

**********************************************************************************************/

/**********
 * <p> Title: EmailValidator Class</p>
 * 
 * <p> Description: Checks inputs for proper email format, else returns proper error statement.</p>
 *
 */

/*-********************************************************************************************/

public class EmailValidator {
	

	public static String emailAddressErrorMessage = "";	// The error message text
	public static String emailAddressInput = "";		// The input being processed
	public static int emailAddressIndexofError = -1;
	private static int state = 0;						// The current state value
	private static int nextState = 0;					// The next state value
	private static String inputLine = "";				// The input line
	private static char currentChar;					// The current character in the line
	private static int currentCharNdx;					// The index of the current character
	private static boolean running;						// The flag that specifies if the FSM is 
									
	
	private static void moveToNextCharacter() {
		currentCharNdx++;
		if (currentCharNdx < inputLine.length())
			currentChar = inputLine.charAt(currentCharNdx);
		else {
			currentChar = ' ';
			running = false;
		}
	}
	
	public static String checkEmailAddress(String input) {
		state = 0;
		inputLine = input;
		currentCharNdx = 0;
		emailAddressInput = input;

		if (input.length() <= 0) {
			return "There was no email address found";
		}
		currentChar = input.charAt(0);


		if (input.length() > 255) {
			return "A valid email address must be no more than 255 characters.";
		}
		running = true;


		while (running) {
			nextState = -1;		
			
			switch (state) {
			case 0: 
				if ((currentChar >= 'A' && currentChar <= 'Z')|| 		// Upper case
						(currentChar >= 'a' && currentChar <= 'z') ||	// Lower case
						(currentChar >= '0' && currentChar <= '9')) {	// Digit
					nextState = 1;
				}
				else { 
					running = false;
				}
				
				break;				

			
			case 1: 
				if ((currentChar >= 'A' && currentChar <= 'Z')|| 		// Upper case
						(currentChar >= 'a' && currentChar <= 'z') ||	// Lower case
						(currentChar >= '0' && currentChar <= '9')) {	// Digit
					nextState = 1;
				}
				else if (currentChar == '.') {
					nextState = 0;
				}
				
				else if (currentChar == '@') {
					nextState = 2;
				}
								
				else { 
					running = false;
				}
				
				break;
							
			case 2:
				if ((currentChar >= 'A' && currentChar <= 'Z')|| 		// Upper case
						(currentChar >= 'a' && currentChar <= 'z') ||	// Lower case
						(currentChar >= '0' && currentChar <= '9')) {	// Digit
					nextState = 3;
				}
								
				else { 
					running = false;
				}

				break;
	
			case 3:
				if ((currentChar >= 'A' && currentChar <= 'Z')|| 		// Upper case
						(currentChar >= 'a' && currentChar <= 'z') ||	// Lower case
						(currentChar >= '0' && currentChar <= '9')) {	// Digit
					nextState = 3;
				}
				else if (currentChar == '.') {
					nextState = 2;
				}
				
				else if (currentChar == '-') {
					nextState = 4;
				}
								
				else { 
					running = false;
				}

				break;

			case 4: 

				if ((currentChar >= 'A' && currentChar <= 'Z')|| 		// Upper case
						(currentChar >= 'a' && currentChar <= 'z') ||	// Lower case
						(currentChar >= '0' && currentChar <= '9')) {	// Digit
					nextState = 3;
				}

				else { 
					running = false;
				}

				break;

			}
			
			if (running) {
				
				moveToNextCharacter();
				// Move to the next state
				state = nextState;
				nextState = -1;
			}

		}
		

		emailAddressIndexofError = currentCharNdx;
		switch (state) {
		case 0:
			// State 0 is not a final state, so we can return a very specific error message
			emailAddressIndexofError = currentCharNdx;		// Copy the index of the current character;
			return "May only be alphanumberic.\n";

		case 1:
			// State 1 is not a final state, so we can return a very specific error message

			emailAddressIndexofError = currentCharNdx;		// Copy the index of the current character;
			return "Email must contain @ \n";


		case 2:
			// State 2 is not a final state, so we can return a very specific error message
						
			emailAddressIndexofError = currentCharNdx;		// Copy the index of the current character;
			return "Invalid email domain \n";

		case 3:
			// State 3 is a Final State, so this is not an error if the input is empty, otherwise

				return "";
	

		case 4:
			// State 4 is not a final state, so we can return a very specific error message. 

			emailAddressIndexofError = currentCharNdx;		// Copy the index of the current character;
			return "Email cannot end in '-' \n";

		default:
			return "";
		}
	}


}

