package emailAddressTestbed;


public class EmailAddressRecognizer {
	/**
	 * <p> Title: FSM-translated EmailAddressRecognizer. </p>
	 * 
	 * <p> Description: A demonstration of the mechanical translation of Finite State Machine 
	 * diagram into an executable Java program using the Email Address Recognizer. The code 
	 * detailed design is based on a while loop with a select list</p>
	 * 
	 * <p> Copyright: Lynn Robert Carter © 2022 </p>
	 * 
	 * @author Lynn Robert Carter
	 * 
	 * @version 0.00		2018-02-04	Initial baseline 
	 * @version 2.00		2022-01-06	Rewritten to recognize email addresses and enhanced
	 * 										to support FSM with up through 999 states for the 
	 * 										trace output to align nicely
	 * @version 3.00		2022-03-22	Adjusted to clean up the code and resolving alignment
	 * 										issues with the design and to correct the issue
	 * 										with an empty email address
	 * 
	 */

	/**********************************************************************************************
	 * 
	 * Result attributes to be used for GUI applications where a detailed error message and a 
	 * pointer to the character of the error will enhance the user experience.
	 * 
	 */

	public static String emailAddressErrorMessage = "";
    public static String emailAddressInput = "";
    public static int emailAddressIndexofError = -1;

    private static int state = 0;
    private static int nextState = 0;
    private static String inputLine = "";
    private static char currentChar;
    private static int currentCharNdx;
    private static boolean running;
    private static int domainPartCounter = 0;

    private static String displayInput(String input, int currentCharNdx) {
        return input.substring(0,currentCharNdx) + "?\n";
    }

    private static void displayDebuggingInfo() {
        if (currentCharNdx >= inputLine.length())
            System.out.println("State: " + state + "  End of input");
        else
            System.out.println("State: " + state +
                    "  Char: " + currentChar +
                    "  Next: " + nextState +
                    "  DomainCount: " + domainPartCounter);
    }

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
            emailAddressErrorMessage = "There was no email address found.\n";
            return emailAddressErrorMessage + displayInput(input, 0);
        }

        if (input.length() > 255) {
            emailAddressErrorMessage =
                "A valid email address must be no more than 255 characters.\n";
            return emailAddressErrorMessage + displayInput(input, 255);
        }

        currentChar = input.charAt(0);
        running = true;

        while (running) {

            nextState = -1;

            switch (state) {

            case 0:
                if (Character.isLetterOrDigit(currentChar))
                    nextState = 1;
                else
                    running = false;
                break;

            case 1:
                if (Character.isLetterOrDigit(currentChar))
                    nextState = 1;
                else if (currentChar == '@') {
                    nextState = 2;
                    domainPartCounter = 0;
                }
                else
                    running = false;
                break;

            case 2:
                if (Character.isLetterOrDigit(currentChar)) {
                    nextState = 3;
                    domainPartCounter = 1;
                }
                else
                    running = false;
                break;

            case 3:
                if (Character.isLetterOrDigit(currentChar)) {
                    nextState = 3;
                    domainPartCounter++;
                }
                else if (currentChar == '-') {
                    nextState = 4;
                    domainPartCounter++;
                }
                else if (currentChar == '.') {
                    nextState = 2;
                    domainPartCounter = 0;
                }
                else
                    running = false;

                if (domainPartCounter > 63)
                    running = false;
                break;

            case 4:
                if (Character.isLetterOrDigit(currentChar)) {
                    nextState = 3;
                    domainPartCounter++;
                }
                else
                    running = false;

                if (domainPartCounter > 63)
                    running = false;
                break;
            }

            if (running) {
                moveToNextCharacter();
                state = nextState;
            }
        }

        emailAddressIndexofError = currentCharNdx;

        switch (state) {

        case 0:
            emailAddressErrorMessage =
                "The email must start with a letter or digit.\n";
            return emailAddressErrorMessage + displayInput(input, currentCharNdx);

        case 1:
            emailAddressErrorMessage =
                "The email must contain '@' and only letters or digits before it.\n";
            return emailAddressErrorMessage + displayInput(input, currentCharNdx);

        case 2:
            emailAddressErrorMessage =
                "The domain must start with a letter or digit.\n";
            return emailAddressErrorMessage + displayInput(input, currentCharNdx);

        case 3:
            if (currentCharNdx < input.length()) {
                emailAddressErrorMessage =
                    "This must be the end of the input.\n";
                return emailAddressErrorMessage + displayInput(input, currentCharNdx);
            }
            else {
                emailAddressIndexofError = -1;
                emailAddressErrorMessage = "";
                return "";
            }

        case 4:
            emailAddressErrorMessage =
                "A hyphen in the domain must be followed by a letter or digit.\n";
            return emailAddressErrorMessage + displayInput(input, currentCharNdx);

        default:
            return "";
        }
    }
}