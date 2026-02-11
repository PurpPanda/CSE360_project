package guiTools;

public class PhoneNumbValidator {
	/*-********************************************************************************************

	The Controller for ViewUserUpdate 
	
	**********************************************************************************************/

	/**********
	 * <p> Title: PhoneNumbValidator Class</p>
	 * 
	 * <p> Description: This static class verifies a proper phone number is inputed, else returns
	 * the proper error message.</p>
	 *
	 */

	/*-********************************************************************************************/
	public static String checkPhoneNumber(String input) {

        if (input == null || input.isBlank()) {
            return "Phone number cannot be empty.";
        }

        input = input.trim();

        // Must be exactly 10 digits
        if (input.length() != 10) {
            return "Phone number must be exactly 10 digits.";
        }

        // Ensure all characters are digits
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return "Phone number may contain digits only.";
            }
        }

        // Extract area code (first 3 digits)
        String areaCode = input.substring(0, 3);

        if (!(areaCode.equals("480") ||
              areaCode.equals("520") ||
              areaCode.equals("928") ||
              areaCode.equals("602"))) {
            return "Invalid area code. Must begin with 480, 520, 928, or 602.";
        }

        return ""; // Valid phone number
    }

}
