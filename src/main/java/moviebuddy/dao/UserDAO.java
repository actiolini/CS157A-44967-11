package moviebuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import moviebuddy.util.DBConnection;
import moviebuddy.util.Passwords;
import moviebuddy.model.User;

public class UserDAO {

    public boolean signUp(String userName, String email, String password) throws Exception {
        String INSERT_USER = "INSERT INTO user(type) values('registered');";
        String INSERT_REGISTERED_USER = "INSERT INTO registered_user(account_id, name, email, hashpw, salt) values ((SELECT LAST_INSERT_ID()), ?, ?, ?, ?);";
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

    public boolean signIn(String email, String password) throws Exception {
        User user = getRegisteredUser(email);
        if (user == null) {
            return false;
        }
        return Passwords.isExpectedPassword(password.toCharArray(), user.getSalt(), user.getHashPassword());
    }

    public User getRegisteredUser(String email) throws Exception {
        String QUERY_REGISTERED_USER = "SELECT account_id, name, email, hashpw, salt, zip_code FROM registered_user WHERE email = ? ;";
        Connection conn = DBConnection.connect();
        PreparedStatement getRegisteredUser = conn.prepareStatement(QUERY_REGISTERED_USER);
        getRegisteredUser.setString(1, email);
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
        }
        getRegisteredUser.close();
        conn.close();
        return user;
    }
}
