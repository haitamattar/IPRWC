package nl.hsleiden.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.inject.Singleton;
import io.dropwizard.auth.Auth;
import nl.hsleiden.View;
import nl.hsleiden.model.User;
import nl.hsleiden.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.Collection;


@Singleton
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource
{
    private UserService service;


    @Inject
    public UserResource(UserService service)
{
    this.service = service;
}


    @GET
    @JsonView(View.Public.class)
    public Collection<User> retrieveAll() throws SQLException {
        return service.getAll();
    }


    @GET
    @Path("/{id}")
    @JsonView(View.Public.class)
    public User retrieve(@PathParam("id") long id) throws SQLException {
        return service.getById(id);
    }


    @GET
    @Path("/me")
    @JsonView(View.Private.class)
    public User authenticate(@Auth User authenticator)
    {
        return authenticator;
    }

}
