package moviebuddy.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class Validation {
    private static final int USERNAME_MIN_LENGTH = 2;
    private static final int USERNAME_MAX_LENGTH = 20;
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int STAFF_ID_LENGTH = 6;

    private Validation() {
    }

    public static String sanitize(String input) {
        return Jsoup.clean(input, Whitelist.none());
    }

    public static String validateUserName(String userName) {
        if (userName.isEmpty()) {
            return "Username field cannot be empty\n";
        }
        if (!userName.matches("(.*[a-zA-Z0-9].*)")) {
            return "Username must contain only letters and numbers\n";
        }
        if (!userName.matches("(^[a-zA-Z].*)")) {
            return "Username must a letter as first character\n";
        }
        if (userName.length() < USERNAME_MIN_LENGTH) {
            return "Username must contain at least " + USERNAME_MIN_LENGTH + " characters\n";
        }
        if (userName.length() > USERNAME_MAX_LENGTH) {
            return "Username must not contain more than " + USERNAME_MAX_LENGTH + " characters\n";
        }
        return "";
    }

    public static String validateEmail(String email) {
        if (email.isEmpty()) {
            return "Email address field cannot be empty\n";
        }
        if (!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            return "Invalid email address\n";
        }
        return "";
    }

    public static String validatePassword(String password) {
        if (password.isEmpty()) {
            return "Passwords field cannot be empty\n";
        }
        if (!password.matches("(.*[a-z].*)")) {
            return "Passwords must contain at least a lowercase letter\n";
        }
        if (!password.matches("(.*[A-Z].*)")) {
            return "Passwords must contain at least an uppercase letter\n";
        }
        if (!password.matches("(.*[0-9].*)")) {
            return "Passwords must contain at least a number\n";
        }
        if (password.length() < PASSWORD_MIN_LENGTH) {
            return "Passwords must be at least " + PASSWORD_MIN_LENGTH + " characters\n";
        }
        return "";
    }

    public static String validateRePassword(String password, String rePassword) {
        if (rePassword.isEmpty()) {
            return "Re-enter passwords field cannot be empty\n";
        }
        if (!password.equals(rePassword)) {
            return "Passwords are not matched\n";
        }
        return "";
    }

    public static String validateStaffId(String staffId) {
        if (staffId.length() != STAFF_ID_LENGTH || !staffId.matches("[0-9]+")) {
            return "Incorrect staff ID number format";
        }
        return "";
    }
}
