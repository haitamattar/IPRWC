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

    final String GET_ALL_USERS = "SELECT * FROM user;";

    final String FIND_BY_ID = "SELECT * FROM `user` WHERE `id` = ?;";

    final String FIND_BY_EMAIL = "SELECT * FROM `user` WHERE `email` = ?;";

    final String INSERT_USER = "INSERT INTO `user` (`email`, `password`, `fullname`, " +
                               " `postalcode`, `city`, `street`, `streetnumber`, `role`) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    // Delete only customers
    final String DELETE_USER = "DELETE " +
                               "FROM `user` " +
                               "WHERE `id` = ? AND `role` = 'CUSTOMER';";


    final String UPDATE_USER = "UPDATE `user` " +
                               "SET `email` = ?, `password` = ?, `fullname` = ?, `postalcode` = ?, `city` = ?," +
                               " `street` = ?, `streetnumber` = ? " +
                               "WHERE `id` = ?;";
    // insert user
    public User insert(User user) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()){

            // Insert user
            PreparedStatement insert_user_ps = connection.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
            insert_user_ps.setString(1, user.getEmail());
            insert_user_ps.setString(2, user.getPassword());
            insert_user_ps.setString(3, user.getFullName());
            insert_user_ps.setString(4, user.getPostalcode());
            insert_user_ps.setString(5, user.getCity());
            insert_user_ps.setString(6, user.getStreet());
            insert_user_ps.setString(7, user.getStreetnumber());
            insert_user_ps.setString(8, user.getRole());

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


    public boolean update(User user) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement update_ps = connection.prepareStatement(UPDATE_USER);

            update_ps.setString(1, user.getEmail());
            update_ps.setString(2, user.getPassword());
            update_ps.setString(3, user.getFullName());
            update_ps.setString(4, user.getPostalcode());
            update_ps.setString(5, user.getCity());
            update_ps.setString(6, user.getStreet());
            update_ps.setString(7, user.getStreetnumber());
            update_ps.setLong(8, user.getId());

            update_ps.execute();

            update_ps.close();
            return true;
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


    // Get user by id
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


    // Get user by email
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

    // Delete user
    public boolean deleteUser(Long id) throws SQLException{
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(DELETE_USER);
            pstmt.setLong(1, id);

            int rowsAffected = pstmt.executeUpdate();

            pstmt.close();
            connection.close();
            return rowsAffected > 0;
        }
    }


    // create user
    private User createUser(ResultSet rset) throws SQLException {
        User user = new User(
                rset.getLong("id"),
                rset.getString("email"),
                rset.getString("password"),
                rset.getString("fullname"),
                rset.getString("postalcode"),
                rset.getString("city"),
                rset.getString("street"),
                rset.getString("streetnumber"),
                rset.getString("role"));
        return user;
    }
}
