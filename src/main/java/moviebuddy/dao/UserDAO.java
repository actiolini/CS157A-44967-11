package moviebuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;
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

    public String createStaff(String role, String theatreLocation, String userName, String email, String password)
            throws Exception {
        String INSERT_USER = "INSERT INTO user(type) VALUES('registered');";
        String INSERT_REGISTERED_USER = "INSERT INTO registered_user(account_id, name, email, hashpw, salt) VALUES (LAST_INSERT_ID(), ?, ?, ?, ?);";
        String INSERT_MEMBERSHIP_USER = "INSERT INTO membership(account_id, auto_renew) VALUES (LAST_INSERT_ID(), ?);";
        String INSERT_STAFF = "INSERT INTO provider(account_id, role_id) VALUES (LAST_INSERT_ID(), (SELECT role_id FROM role WHERE title=?));";
        String INSERT_EMPLOY = "INSERT INTO employ(staff_id, theatre_id) VALUES (LAST_INSERT_ID(), (SELECT theatre_id FROM theatre WHERE theatre_id=?));";
        int AUTO_RENEW = 1;
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
            if (!theatreLocation.isEmpty()) {
                PreparedStatement insertEmploy = conn.prepareStatement(INSERT_EMPLOY);
                insertEmploy.setString(1, theatreLocation);
                insertEmploy.executeUpdate();
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
            return "Fail to create staff user";
        } finally {
            conn.setAutoCommit(true);
        }
        return "";
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
        String QUERY_STAFF_USER = "SELECT ru.account_id, ru.name, ru.email, ru.hashpw, ru.salt, ru.zip_code, m.points, m.auto_renew, m.end_date, p.staff_id, r.title, e.theatre_id FROM registered_user ru JOIN membership m ON ru.account_id=m.account_id JOIN provider p ON p.account_id=m.account_id JOIN role r ON r.role_id=p.role_id LEFT OUTER JOIN employ e ON p.staff_id=e.staff_id WHERE p.staff_id=?;";
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
            user.setTheatre_id(res.getInt("theatre_id"));
        }
        getRegisteredUser.close();
        conn.close();
        return user;
    }

    public List<User> listAdminUser() throws Exception {
        String QUERY_ADMINS = "SELECT p.staff_id, ru.name, r.title, ru.email FROM provider p JOIN registered_user ru ON p.account_id = ru.account_id JOIN role r ON p.role_id = r.role_id WHERE r.title='admin' ORDER BY p.staff_id;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryEmployees = conn.prepareStatement(QUERY_ADMINS);
        ResultSet res = queryEmployees.executeQuery();
        List<User> admins = new ArrayList<>();
        while (res.next()) {
            User admin = new User();
            admin.setStaffId(res.getInt("staff_id"));
            admin.setUserName(res.getString("name"));
            admin.setRole(res.getString("title"));
            admin.setEmail(res.getString("email"));
            admins.add(admin);
        }
        return admins;
    }

    public List<User> listStaffByTheatreId(String theatreId) throws Exception {
        String QUERY_EMPLOYEES = "SELECT p.staff_id, ru.name, r.title, ru.email FROM provider p JOIN registered_user ru ON p.account_id = ru.account_id JOIN role r ON p.role_id = r.role_id JOIN employ e ON p.staff_id = e.staff_id WHERE e.theatre_id = ? ORDER BY r.title DESC, ru.name ;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryEmployees = conn.prepareStatement(QUERY_EMPLOYEES);
        queryEmployees.setString(1, theatreId);
        ResultSet res = queryEmployees.executeQuery();
        List<User> staffs = new ArrayList<>();
        while (res.next()) {
            User staff = new User();
            staff.setStaffId(res.getInt("staff_id"));
            staff.setUserName(res.getString("name"));
            staff.setRole(res.getString("title"));
            staff.setEmail(res.getString("email"));
            staffs.add(staff);
        }
        return staffs;
    }

    public int getEmployTheatreId(String staffId) throws Exception {
        String QUERY_EMPLOY_THEATRE_ID = "SELECT theatre_id FROM employ WHERE staff_id=?;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryEmployTheatreId = conn.prepareStatement(QUERY_EMPLOY_THEATRE_ID);
        queryEmployTheatreId.setString(1, staffId);
        ResultSet res = queryEmployTheatreId.executeQuery();
        int theatreId = 0;
        while (res.next()) {
            theatreId = res.getInt("theatre_id");
        }
        queryEmployTheatreId.close();
        conn.close();
        return theatreId;
    }

    public String deleteStaff(String staffId) throws Exception {
        String DELETE_EMPLOY = "DELETE FROM employ WHERE staff_id=?;";
        String QUERY_ACCOUNT_ID = "SELECT account_id FROM provider WHERE staff_id=?";
        String DELETE_PROVIDER = "DELETE FROM provider WHERE account_id=?;";
        String DELETE_MEMBERSHIP = "DELETE FROM membership WHERE account_id=?;";
        String DELETE_REGISTERED_USER = "DELETE FROM registered_user WHERE account_id=?;";
        String DELETE_USER = "DELETE FROM user WHERE account_id=?;";
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            PreparedStatement deleteEmploy = conn.prepareStatement(DELETE_EMPLOY);
            deleteEmploy.setString(1, staffId);
            deleteEmploy.executeUpdate();
            PreparedStatement queryAccountId = conn.prepareStatement(QUERY_ACCOUNT_ID);
            queryAccountId.setString(1, staffId);
            ResultSet res = queryAccountId.executeQuery();
            String accountId = "";
            while (res.next()) {
                accountId = res.getString("account_id");
            }
            PreparedStatement deleteProvider = conn.prepareStatement(DELETE_PROVIDER);
            deleteProvider.setString(1, accountId);
            deleteProvider.executeUpdate();
            PreparedStatement deleteMembership = conn.prepareStatement(DELETE_MEMBERSHIP);
            deleteMembership.setString(1, accountId);
            deleteMembership.executeUpdate();
            PreparedStatement deleteRegisteredUser = conn.prepareStatement(DELETE_REGISTERED_USER);
            deleteRegisteredUser.setString(1, accountId);
            deleteRegisteredUser.executeUpdate();
            PreparedStatement deleteUser = conn.prepareStatement(DELETE_USER);
            deleteUser.setString(1, accountId);
            deleteUser.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Fail to delete staff account";
        } finally {
            conn.setAutoCommit(true);
        }
        return "";
    }
}
