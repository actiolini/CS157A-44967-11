package moviebuddy.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import moviebuddy.model.Schedule;
import moviebuddy.model.ShowTime;

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

    public static String validateRole(String role) {
        if (role.equals("none")) {
            return "Please select a role\n";
        }
        if (!(role.equals("admin") || role.equals("manager") || role.equals("faculty"))) {
            return "Invalid role\n";
        }
        return "";
    }

    public static String validateTheatreLocation(String location) {
        if (location.isEmpty() || location.equals("none")) {
            return "Please select a theatre location\n";
        }
        return "";
    }

    public static String validateStaffId(String staffId) {
        if (staffId.length() != STAFF_ID_LENGTH || !staffId.matches("[0-9]+")) {
            return "Incorrect staff ID number format\n";
        }
        return "";
    }

    public static String validateNumber(String number) {
        if (!number.matches("[0-9]+")) {
            return "Invalid number input\n";
        }
        return "";
    }

    public static String validateDate(String date) {
        try {
            LocalDate.parse(date);
        } catch (Exception e) {
            return "Invalid date input\n";
        }
        return "";
    }

    public static String validateTime(String time) {
        try {
            LocalTime.parse(time);
        } catch (Exception e) {
            return "Invalid time input\n";
        }
        return "";
    }

    public static String validateDouble(String number) {
        try {
            Double.parseDouble(number);
        } catch (Exception e) {
            return "Invalid double input\n";
        }
        return "";
    }

    public static String checkScheduleConflict(List<Schedule> schedule, ShowTime interval) {
        for (Schedule s : schedule) {
            ShowTime st = s.getShowTime();
            if (interval.isConflict(st)) {
                String message = String.format("Time conflict - Schedule ID: %s on %s at %s-%s room: %s", s.getScheduleId(),
                        s.displayShowDate(), st.getStartTime(), st.getEndTime(), s.getRoomNumber());
                return message;
            }
        }
        return "";
    }
}
