package guiTools;

public class PasswordPolicyCheckerTest {

    public static void main(String[] args) {

        String[] tests = {

            "Aa!15678",
            "",
            "Aa!14u",
            "Aa!15678Aa!15678Aa!15678Aa!15678Aa!15678Aa!15678Aa!15678Aa!15678", 
            "Aabc1234",
            "aa!15678",
            "AA!15678",
            "Aa!bcdef",
            "Aa!1567 "
        };

        System.out.println("=== Password Test Cases ===");
        
        for(int i = 0; i < tests.length;i ++) {
        	
        	var result = guiTools.PasswordPolicyChecker.check(tests[i]);
        	
        	System.out.println(
        			"TC-PW-" + String.format("%02d", (i+1))
        			+ " | Input: " + tests[i] + "" + "->" + result.ok + "|"
        			+ result.message);
        }
    }
}