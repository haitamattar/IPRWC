/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hsleiden.service;

import java.sql.SQLException;
import java.util.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.basic.BasicCredentials;
import javax.inject.Inject;
import javax.inject.Singleton;

import nl.hsleiden.model.User;
import nl.hsleiden.persistence.UserDAO;

/**
 *
 * @author Peter van Vliet
 */
@Singleton
public class AuthenticationService implements Authenticator<BasicCredentials, User>, Authorizer<User>
{
    private UserDAO userDAO;

    @Inject
    public AuthenticationService(UserDAO dao)
    {
        this.userDAO = dao;
    }


    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException
    {
        User user = null;
        try {
            user = userDAO.findByEmail(credentials.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (user != null && user.getPassword().equals(credentials.getPassword()))
        {

            return Optional.of(user);
        }

        return Optional.empty();
    }

    @Override
    public boolean authorize(User user, String roleName)
    {
        return true;
    }

}
