package validation;

import emailAddressTestbed.EmailAddressRecognizer;
import passwordPopUpWindow.Model;

public class InputValidator {

    // You said phone is REQUIRED
    public static final boolean PHONE_REQUIRED = true;

    // =========================
    // 1) Username (Email) - FSM
    // =========================
    public static String validateEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            return "Enter a valid email address (example@domain.com).";
        }

        email = email.trim();

        if (email.length() < 3 || email.length() > 64) {
            return "Enter a valid email address (example@domain.com).";
        }

        if (email.contains(" ")) {
            return "Enter a valid email address (example@domain.com).";
        }

        int atIndex = email.indexOf('@');

        // Must contain @ and have at least 3 chars before it
        if (atIndex < 3) {
            return "Enter a valid email address (example@domain.com).";
        }

        // Must start with alphanumeric
        if (!Character.isLetterOrDigit(email.charAt(0))) {
            return "Enter a valid email address (example@domain.com).";
        }

        // Validate characters before @ (allow . and _)
        for (int i = 0; i < atIndex; i++) {
            char c = email.charAt(i);

            if (!(Character.isLetterOrDigit(c) || c == '.' || c == '_')) {
                return "Enter a valid email address (example@domain.com).";
            }
        }

        // Must end with alphanumeric
        if (!Character.isLetterOrDigit(email.charAt(email.length() - 1))) {
            return "Enter a valid email address (example@domain.com).";
        }

        // ---- KEEP FSM (domain validation only) ----

        String domainOnly = "a@" + email.substring(atIndex + 1);
        String fsmErr = EmailAddressRecognizer.checkEmailAddress(domainOnly);

        if (fsmErr != null && !fsmErr.isEmpty()) {
            return "Enter a valid email address (example@domain.com).";
        }

        return "";
    }


    // =========================
    // 2) Password - length + FSM complexity + confirm
    // =========================
    public static String validatePassword(String password) {

        if (password == null || password.isEmpty()) {
            return "Passwords must be at least 8 characters.";
        }

        if (password.length() < 8) {
            return "Passwords must be at least 8 characters.";
        }

        if (password.length() > 64) {
            return "Password must be 64 characters or fewer.";
        }

        // Call professor password evaluator (FSM-based)
        String eval = Model.evaluatePassword(password);
        if (eval != null && !eval.isEmpty()) {
            return eval; // show their helpful message
        }

        return "";
    }

    public static String validatePasswordMatch(String p1, String p2) {
        if (p1 == null) p1 = "";
        if (p2 == null) p2 = "";

        if (!p1.equals(p2)) {
            return "Passwords do not match.";
        }
        return "";
    }

    // =========================
    // 3) Phone Number - REQUIRED
    // =========================
    public static String validatePhoneNumber(String phone) {

        if (phone == null) phone = "";
        phone = phone.trim();

        if (phone.isEmpty()) {
            return PHONE_REQUIRED ? "Phone number is required." : "";
        }

        if (phone.length() != 10) {
            return "Phone number must be exactly 10 digits.";
        }

        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return "Phone number can only contain numbers.";
            }
        }

        String areaCode = phone.substring(0, 3);
        if (!(areaCode.equals("602") ||
              areaCode.equals("480") ||
              areaCode.equals("623") ||
              areaCode.equals("520") ||
              areaCode.equals("928"))) {
            return "Phone number must start with 602, 480, 623, 520, or 928.";
        }

        return "";
    }

    // =========================
    // 4) Names - letters + hyphen placement rules
    // =========================
    public static String validateName(String name, boolean required, String label) {

        if (name == null) name = "";
        name = name.trim();

        if (name.isEmpty()) {
            return required ? (label + " is required.") : "";
        }

        if (name.length() < 1 || name.length() > 50) {
            return label + " must be between 1 and 50 characters.";
        }

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!(Character.isLetter(c) || c == '-')) {
                return "Name can only contain letters, hyphens.";
            }
        }

        // hyphen placement rules (important)
        if (name.startsWith("-") || name.endsWith("-") || name.contains("--")) {
            return "Name can only contain letters, hyphens.";
        }

        return "";
    }
}
