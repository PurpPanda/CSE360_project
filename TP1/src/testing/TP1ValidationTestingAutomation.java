package testing;

import validation.InputValidator;
import emailAddressTestbed.EmailAddressRecognizer;
import java.util.ArrayList;

public class TP1ValidationTestingAutomation {

    private static int passed = 0;
    private static int failed = 0;

    private static ArrayList<String> passedTests = new ArrayList<>();
    private static ArrayList<String> failedTests = new ArrayList<>();

    public static void main(String[] args) {

        // PASSWORD TESTS
        check("TC-PW-01", InputValidator.validatePassword("Aa!15678").isEmpty());
        check("TC-PW-02", !InputValidator.validatePassword("").isEmpty());
        check("TC-PW-03", !InputValidator.validatePassword("Aa!14u").isEmpty());
        check("TC-PW-04", !InputValidator.validatePassword(makeLong(65)).isEmpty());
        check("TC-PW-05", !InputValidator.validatePassword("Aabc1234").isEmpty());
        check("TC-PW-06", !InputValidator.validatePassword("aa!15678").isEmpty());
        check("TC-PW-07", !InputValidator.validatePassword("AA!15678").isEmpty());
        check("TC-PW-08", !InputValidator.validatePassword("Aa!bcdef").isEmpty());
        check("TC-PW-09", !InputValidator.validatePassword("Aa!15678 ").isEmpty());

        // EMAIL TESTS
        check("TC-UN-01", InputValidator.validateEmail("game.e@gmail.com").isEmpty());
        check("TC-UN-02", InputValidator.validateEmail("Wall_o@mail.edu.com").isEmpty());
        check("TC-UN-03", !InputValidator.validateEmail(" ").isEmpty());
        check("TC-UN-04", !InputValidator.validateEmail("@1Walle").isEmpty());
        check("TC-UN-06", !InputValidator.validateEmail("W@i").isEmpty());
        check("TC-UN-07", !InputValidator.validateEmail("Walle@gmail.com_").isEmpty());
        check("TC-UN-08", !InputValidator.validateEmail("walle@gmail..com").isEmpty());
        check("TC-UN-09", !InputValidator.validateEmail("-21@gmail.com").isEmpty());
        check("TC-UN-10", !InputValidator.validateEmail(makeLong(70) + "@x.com").isEmpty());

        // PHONE TESTS
        check("TC-PN-01", !InputValidator.validatePhoneNumber("5551112222").isEmpty());
        check("TC-PN-02", !InputValidator.validatePhoneNumber("480542542311").isEmpty());
        check("TC-PN-03", !InputValidator.validatePhoneNumber("4805425").isEmpty());
        check("TC-PN-04", InputValidator.validatePhoneNumber("4805425423").isEmpty());

        // NAME TESTS
        check("TC-N-01", !InputValidator.validateName("Ga2n", true, "Name").isEmpty());
        check("TC-N-02", !InputValidator.validateName(makeLong(51), true, "Name").isEmpty());
        check("TC-N-03", !InputValidator.validateName("", true, "Name").isEmpty());
        check("TC-N-04", InputValidator.validateName("Dave", true, "Name").isEmpty());

        // SECURITY TESTS
        check("TC-SC-EMAIL", !InputValidator.validateEmail(makeLong(200) + "@x.com").isEmpty());
        check("TC-SC-NAME", !InputValidator.validateName(makeLong(200), true, "Name").isEmpty());
        check("TC-SC-PHONE", !InputValidator.validatePhoneNumber(makeLong(200)).isEmpty());
        check("TC-SC-PW", !InputValidator.validatePassword(makeLong(200)).isEmpty());

        printResults();
    }

    private static void check(String testId, boolean passedCondition) {

        if (passedCondition) {
            System.out.println(testId + " -> PASS");
            passed++;
            passedTests.add(testId);
        } else {
            System.out.println(testId + " -> FAIL");
            failed++;
            failedTests.add(testId);
        }
    }

    private static void printResults() {

        System.out.println("\n===== FINAL RESULTS =====");
        System.out.println("Total Passed: " + passed);
        System.out.println("Total Failed: " + failed);

        System.out.println("\nPassed Tests:");
        for (String t : passedTests) {
            System.out.println("  " + t);
        }

        System.out.println("\nFailed Tests:");
        for (String t : failedTests) {
            System.out.println("  " + t);
        }
    }

    private static String makeLong(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("A");
        }
        return sb.toString();
    }
}
