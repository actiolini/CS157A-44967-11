package moviebuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;

import moviebuddy.util.DBConnection;
import moviebuddy.util.Passwords;
import moviebuddy.model.User;

public class UserDAO {

    public boolean signUp(String userName, String email, String password) throws Exception {
        String INSERT_USER = "INSERT INTO user(type) VALUES('registered');";
        String INSERT_REGISTERED_USER = "INSERT INTO registered_user(account_id, name, email, hashpw, salt) VALUES (LAST_INSERT_ID(), ?, ?, ?, ?);";
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            PreparedStatement insertUser = conn.prepareStatement(INSERT_USER);
            insertUser.executeUpdate();
            PreparedStatement insertRegisteredUser = conn.prepareStatement(INSERT_REGISTERED_USER);
            insertRegisteredUser.setString(1, userName);
            insertRegisteredUser.setString(2, email);
            byte[] salt = Passwords.getSalt();
            byte[] hashpw = Passwords.hash(password.toCharArray(), salt);
            insertRegisteredUser.setBytes(3, hashpw);
            insertRegisteredUser.setBytes(4, salt);
            insertRegisteredUser.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            conn.setAutoCommit(true);
        }
        return true;
    }

    public User signIn(String email, String password) throws Exception {
        User user = getRegisteredUser(email);
        if (user != null
                && Passwords.isExpectedPassword(password.toCharArray(), user.getSalt(), user.getHashPassword())) {
            return user;
        }
        return null;
    }

    public User getRegisteredUser(String email) throws Exception {
        String QUERY_REGISTERED_USER = "SELECT account_id, name, hashpw, salt, zip_code FROM registered_user WHERE email = ? ;";
        Connection conn = DBConnection.connect();
        PreparedStatement getRegisteredUser = conn.prepareStatement(QUERY_REGISTERED_USER);
        getRegisteredUser.setString(1, email);
        ResultSet res = getRegisteredUser.executeQuery();
        User user = null;
        while (res.next()) {
            user = new User();
            user.setAccountId(res.getInt("account_id"));
            user.setUserName(res.getString("name"));
            user.setEmail(email);
            user.setHashPassword(res.getBytes("hashpw"));
            user.setSalt(res.getBytes("salt"));
            user.setZip(res.getString("zip_code"));
        }
        getRegisteredUser.close();
        conn.close();
        return user;
    }

    public int createStaff(String role, String userName, String email, String password) throws Exception {
        String INSERT_USER = "INSERT INTO user(type) VALUES('registered');";
        String INSERT_REGISTERED_USER = "INSERT INTO registered_user(account_id, name, email, hashpw, salt) VALUES (LAST_INSERT_ID(), ?, ?, ?, ?);";
        String INSERT_MEMBERSHIP_USER = "INSERT INTO membership(account_id, auto_renew) VALUES (LAST_INSERT_ID(), ?);";
        String INSERT_STAFF = "INSERT INTO provider(account_id, role_id) VALUES (LAST_INSERT_ID(), (SELECT role_id FROM role WHERE title=?));";
        String QUERY_STAFF_ID = "SELECT LAST_INSERT_ID() as id";
        int AUTO_RENEW = 1;
        int staffId = -1;
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            PreparedStatement insertUser = conn.prepareStatement(INSERT_USER);
            insertUser.executeUpdate();
            PreparedStatement insertRegisteredUser = conn.prepareStatement(INSERT_REGISTERED_USER);
            insertRegisteredUser.setString(1, userName);
            insertRegisteredUser.setString(2, email);
            byte[] salt = Passwords.getSalt();
            byte[] hashpw = Passwords.hash(password.toCharArray(), salt);
            insertRegisteredUser.setBytes(3, hashpw);
            insertRegisteredUser.setBytes(4, salt);
            insertRegisteredUser.executeUpdate();
            PreparedStatement insertMembershipUser = conn.prepareStatement(INSERT_MEMBERSHIP_USER);
            insertMembershipUser.setInt(1, AUTO_RENEW);
            insertMembershipUser.executeUpdate();
            PreparedStatement insertStaff = conn.prepareStatement(INSERT_STAFF);
            insertStaff.setString(1, role);
            insertStaff.executeUpdate();
            PreparedStatement getStaffId = conn.prepareStatement(QUERY_STAFF_ID);
            ResultSet res = getStaffId.executeQuery();
            while (res.next()) {
                staffId = res.getInt("id");
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return -1;
        } finally {
            conn.setAutoCommit(true);
        }
        return staffId;
    }

    public User signInStaff(String staffId, String password) throws Exception {
        User user = getStaffUser(staffId);
        if (user != null
                && Passwords.isExpectedPassword(password.toCharArray(), user.getSalt(), user.getHashPassword())) {
            return user;
        }
        return null;
    }

    public User getStaffUser(String staffId) throws Exception {
        String QUERY_STAFF_USER = "SELECT ru.account_id, ru.name, ru.email, ru.hashpw, "
                + "ru.salt, ru.zip_code, m.points, m.auto_renew, m.end_date, p.staff_id, r.title "
                + "FROM registered_user ru JOIN membership m ON ru.account_id=m.account_id "
                + "JOIN provider p ON p.account_id=m.account_id JOIN role r ON r.role_id=p.role_id "
                + "WHERE p.staff_id=?;";
        Connection conn = DBConnection.connect();
        PreparedStatement getRegisteredUser = conn.prepareStatement(QUERY_STAFF_USER);
        getRegisteredUser.setString(1, staffId);
        ResultSet res = getRegisteredUser.executeQuery();
        User user = null;
        while (res.next()) {
            user = new User();
            user.setAccountId(res.getInt("account_id"));
            user.setUserName(res.getString("name"));
            user.setEmail(res.getString("email"));
            user.setHashPassword(res.getBytes("hashpw"));
            user.setSalt(res.getBytes("salt"));
            user.setZip(res.getString("zip_code"));
            user.setBuddyPoints(res.getInt("points"));
            user.setAutoRenew(res.getBoolean("auto_renew"));
            user.setEndDate(LocalDate.parse(res.getString("end_date")));
            user.setStaffId(res.getInt("staff_id"));
            user.setRole(res.getString("title"));
        }
        getRegisteredUser.close();
        conn.close();
        return user;
    }
}
