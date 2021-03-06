package nl.hsleiden.service;

import nl.hsleiden.model.User;
import nl.hsleiden.persistence.UserDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Singleton
public class UserService extends BaseService<User>
{
    private UserDAO userDAO;

    @Inject
    public UserService(UserDAO dao)
    {
        this.userDAO = dao;
    }

    public Collection<User> getAll() throws SQLException {
        try {
            List<User> userList = userDAO.all();
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getById(long id) {
        try {
            return requireResult(userDAO.findById(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean update(User user) {
        try {
            return userDAO.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public User insertUser(User user){
        try {
            return userDAO.insert(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteUser(Long id) throws SQLException {
        return userDAO.deleteUser(id);
    }



}
