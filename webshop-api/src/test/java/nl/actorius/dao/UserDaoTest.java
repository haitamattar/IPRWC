package nl.actorius.dao;

import nl.hsleiden.model.User;
import nl.hsleiden.persistence.UserDAO;
import org.junit.Test;

import java.sql.SQLException;

public class UserDaoTest {

    private static UserDAO dao;
    private static User testClient;


    @Test
    public void getGebruikerById() throws SQLException {
        try {
            testClient = dao.findByEmail("joans@hotmail.com");
        } catch (SQLException a) {}

            testClient.getName();
    }
}
