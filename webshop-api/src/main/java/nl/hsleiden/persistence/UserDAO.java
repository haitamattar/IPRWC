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

    String GET_ALL_USERS = "SELECT * FROM user;";

    String FIND_BY_ID = "SELECT * FROM `user` WHERE `id` = ?;";

    String FIND_BY_EMAIL = "SELECT * FROM `user` WHERE `email` = ?;";

    String INSERT_USER = "INSERT INTO `user` (`email`, `password`, `fullname`, `postalcode`, `streetnumber`, `role`) " +
                         "VALUES (?, ?, ?, ?, ?, ?);";


    // insert user
    public User insert(User user) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()){

            // Insert user
            PreparedStatement insert_user_ps = connection.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
            insert_user_ps.setString(1, user.getEmail());
            insert_user_ps.setString(2, user.getPassword());
            insert_user_ps.setString(3, user.getFullName());
            insert_user_ps.setString(4, user.getPostalcode());
            insert_user_ps.setString(5, user.getStreetnumber());
            insert_user_ps.setString(6, user.getRole());

            insert_user_ps.execute();

            // Get created user id
            ResultSet user_rs = insert_user_ps.getGeneratedKeys();
            if (!user_rs.next()) {
                throw new SQLException("No key returned for user");
            }
            user.setId(user_rs.getLong(1));

            // Close client streams
            user_rs.close();
            insert_user_ps.close();

            connection.close();
            return user;
        }
    }

    // Get all users
    public List<User> all() throws SQLException {
        System.out.println("LIST DATA");
        ArrayList<User> users = new ArrayList<User>();

        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(GET_ALL_USERS);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                users.add(createUser(rset));
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
                user = createUser(rset);
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
                user = createUser(rset);
            }

            pstmt.close();
            connection.close();

            return user;
        }
    }


    // create gebruiker
    private User createUser(ResultSet rset) throws SQLException {
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
