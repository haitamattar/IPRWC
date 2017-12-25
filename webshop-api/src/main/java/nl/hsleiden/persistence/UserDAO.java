package nl.hsleiden.persistence;

import nl.hsleiden.dbConnections.MysqlDbAccess;
import nl.hsleiden.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    String GET_ALL_USERS = "SELECT * FROM user";

    String FIND_BY_ID = "SELECT * FROM `user` WHERE `id` = ?";

    String FIND_BY_EMAIL = "SELECT * FROM `user` WHERE `email` = ?";


    // Get all users
    public List<User> all() throws SQLException {
        System.out.println("LIST DATA");
        ArrayList<User> users = new ArrayList<User>();

        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(GET_ALL_USERS);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                users.add(createGebruiker(rset));
            }

            pstmt.close();
            connection.close();

            return users;
        }
    }


    // Get gebruiker by id
    public User findById(long id) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(FIND_BY_ID);
            pstmt.setLong(1, id);

            User user = null;
            ResultSet rset = pstmt.executeQuery();

            while(rset.next()){
                user = createGebruiker(rset);
            }

            pstmt.close();
            connection.close();

            return user;
        }
    }


    // Get gebruiker by email
    public User findByEmail(String email) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(FIND_BY_EMAIL);
            pstmt.setString(1, email);
            User user = null;
            ResultSet rset = pstmt.executeQuery();

            while(rset.next()) {
                user = createGebruiker(rset);
            }

            pstmt.close();
            connection.close();

            return user;
        }
    }


    // create gebruiker
    private User createGebruiker(ResultSet rset) throws SQLException {
        User user = new User(
                rset.getLong("id"),
                rset.getString("email"),
                rset.getString("password"),
                rset.getString("fullname"),
                rset.getString("postalcode"),
                rset.getString("streetnumber"),
                rset.getString("role"));
        return user;
    }
}
