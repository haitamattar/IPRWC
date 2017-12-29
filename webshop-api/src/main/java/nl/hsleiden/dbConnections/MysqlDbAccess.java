package nl.hsleiden.dbConnections;

import nl.hsleiden.persistence.OrderDAO;
import nl.hsleiden.persistence.ProductDAO;
import nl.hsleiden.persistence.UserDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MysqlDbAccess {

    private String host;
    private String username;
    private String password;
    private String driver;

    public MysqlDbAccess() {
        Properties prop = new Properties();

        String cfgPath = System.getProperty("user.dir") + "/mydb.cfg".replace("/", File.separator);

        // Load file with db connection details (host, username, passw)
        try {
            prop.load(new FileInputStream(cfgPath));
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to find database settings file at: " + cfgPath);
        }

        host = prop.getProperty("host");
        username = prop.getProperty("username");
        password = prop.getProperty("password");
        driver = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Cannot load db driver (" + driver + ")");
        }
    }


    public Connection openConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(host, username, password);
        return connection;
    }

    public static MysqlDbAccess getDatabase() {
        return new MysqlDbAccess();
    }


    public UserDAO getUserDao() {
        return new UserDAO();
    }

    public ProductDAO getProductDao() {
        return new ProductDAO();
    }

    public OrderDAO getOrderDao() {
        return new OrderDAO();
    }

}
