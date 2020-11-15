package moviebuddy.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    public static Connection connect() {
        Connection conn = null;
        try {
            String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            String dbConfigPath = rootPath + "db.properties";
            Properties props = new Properties();
            props.load(new FileInputStream(dbConfigPath));
            Class.forName(props.getProperty("driver"));
            String endpoint = props.getProperty("endpoint");
            String port = props.getProperty("port");
            String dbname = props.getProperty("dbname");
            String url = "jdbc:mysql://" + endpoint + ":" + port + "/" + dbname;
            String user = props.getProperty("user");
            String pass = props.getProperty("pass");
            conn = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}