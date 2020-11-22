package moviebuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import moviebuddy.util.DBConnection;
import moviebuddy.model.User;

public class UserDAO {

    public boolean signUp(String userName, String email, String password, String rePassword) throws Exception {
        return true;
    }

    public String signIn(String email, String password) {
        return "";
    }

    public User getRegisterdUser(String email) throws Exception {
        String QUERY_REGISTERD_USER = "SELECT account_id, name, email, hashpw, salt, zip_code FROM registered_user WHERE email = ? ;";
        Connection conn = DBConnection.connect();
        PreparedStatement preparedStatement = conn.prepareStatement(QUERY_REGISTERD_USER);
        preparedStatement.setString(1, email);
        ResultSet res = preparedStatement.executeQuery();
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
        preparedStatement.close();
        conn.close();
        return user;
    }
}
