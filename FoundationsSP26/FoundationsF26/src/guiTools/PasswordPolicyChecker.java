package guiTools;

public class PasswordPolicyChecker {

    public static class Result {
        public final boolean ok;
        public final String message;
        public final int errorIndex;

        public Result(boolean ok, String message, int errorIndex) {
            this.ok = ok;
            this.message = message;
            this.errorIndex = errorIndex;
        }
    }
    
    public static Result check(String input) {
        final int MIN_LEN = 8;
        final int MAX_LEN = 64;	
        
        if (input == null || input.isEmpty()) 
            return new Result(false, "Password is empty.", 0);
        
        
        if (input.length() > MAX_LEN) 
            return new Result(false, "Password is too long (max " + MAX_LEN + ").", MAX_LEN);
        
        
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        
        String specials = "~`!@#$%^&*()-+={}[]|\\:;\"'<>,.?/";
        		
        for (int i = 0; i < input.length(); i++) { 
                    char c = input.charAt(i);		
        
        if (c >= 'A' && c <= 'Z') hasUpper = true;
        else if (c >= 'a' && c <= 'z') hasLower = true;
        else if (c >= '0' && c <= '9') hasDigit = true;
        else if (specials.indexOf(c) >= 0) hasSpecial = true;
        else return new Result(false, "Invalid character: '" + c + "'", i);
        }
        if (input.length() < MIN_LEN) 
            return new Result(false, "Password must be at least " + MIN_LEN + " characters.", input.length());
        
        if (!hasUpper) return new Result(false, "Missing an uppercase letter.", input.length());
        if (!hasLower) return new Result(false, "Missing a lowercase letter.", input.length());
        if (!hasDigit) return new Result(false, "Missing a digit.", input.length());
        if (!hasSpecial) return new Result(false, "Missing a special character.", input.length());

        return new Result(true, "OK", -1);
    

    }
 }