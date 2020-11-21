package moviebuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticateDAO {
    private final int USERNAME_MIN_LENGTH = 2;
    private final int USERNAME_MAX_LENGTH = 20;
    private final int PASSWORD_MIN_LENGTH = 8;

    public String signUp(String username, String email, String pass, String rePass) throws Exception {
        String message = validateUserName(username);
        message += validateEmail(email);
        message += validatePassword(pass);
        message += validateRePassword(pass, rePass);
        if (message.isEmpty()) {
            return "";
        }
        return message;
    }

    public String signIn(String email, String pass) {
        return "";
    }

    public String signOut(String email, String pass) {
        return "";
    }

    private String validateUserName(String username) {
        if (username.isEmpty()) {
            return "Username field cannot be empty\n";
        }
        if (!username.matches("(.*[a-zA-Z0-9].*)")) {
            return "Username must contain only letters and numbers\n";
        }
        if (!username.matches("(^[a-zA-Z].*)")) {
            return "Username must a letter as first character\n";
        }
        if (username.length() < USERNAME_MIN_LENGTH) {
            return "Username must contain at least " + USERNAME_MIN_LENGTH + " characters\n";
        }
        if (username.length() > USERNAME_MAX_LENGTH) {
            return "Username must not contain more than " + USERNAME_MAX_LENGTH + " characters\n";
        }
        return "";
    }

    private String validateEmail(String email) {
        if (email.isEmpty()) {
            return "Email address field cannot be empty\n";
        }
        if (!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            return "Invalid email address\n";
        }
        return "";
    }

    private String validatePassword(String pass) {
        if (pass.isEmpty()) {
            return "Passwords field cannot be empty\n";
        }
        if (!pass.matches("(.*[a-z].*)")) {
            return "Passwords must contain at least a lowercase letter\n";
        }
        if (!pass.matches("(.*[A-Z].*)")) {
            return "Passwords must contain at least an uppercase letter\n";
        }
        if (!pass.matches("(.*[0-9].*)")) {
            return "Passwords must contain at least a number\n";
        }
        if (pass.length() < PASSWORD_MIN_LENGTH) {
            return "Passwords must be at least " + PASSWORD_MIN_LENGTH + " characters\n";
        }
        return "";
    }

    private String validateRePassword(String pass, String rePass) {
        if (rePass.isEmpty()) {
            return "Re-enter passwords field cannot be empty\n";
        }
        if (!pass.equals(rePass)) {
            return "Passwords are not matched\n";
        }
        return "";
    }
}
