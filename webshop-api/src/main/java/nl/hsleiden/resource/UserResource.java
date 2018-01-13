package nl.hsleiden.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.inject.Singleton;
import io.dropwizard.auth.Auth;
import nl.hsleiden.View;
import nl.hsleiden.model.User;
import nl.hsleiden.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
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


    @GET
    @RolesAllowed({"ADMIN"})
    @JsonView(View.Public.class)
    public Collection<User> retrieveAll() throws SQLException {
        return service.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Public.class)
    public User create(User user)
    {
        user.setRole("CUSTOMER");
        user = service.insertUser(user);
        return user;
    }

//    @POST
//    @Path("/addAdmin")
//    @RolesAllowed({"ADMIN"})
//    @Consumes(MediaType.APPLICATION_JSON)
//    @JsonView(View.Public.class)
//    public User createUserAdmin(User user)
//    {
//        user.setRole("ADMIN");
//        user = service.insertUser(user);
//        return user;
//    }

    @PUT
    @RolesAllowed({"CUSTOMER"})
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Public.class)
    public Boolean update(@Auth User authUser,@Valid User user)
    {
        // User could send an other id so use Auth user object for the id and second user object for new data
        user.setId(authUser.getId());
        return service.update(user);
    }

    @DELETE
    @RolesAllowed({"ADMIN"})
    @Path("/{id}")
    @JsonView(View.Public.class)
    public boolean deleteUser(@PathParam("id")long id) throws SQLException {
        System.out.println("PRINT id: " + id);
        return service.deleteUser(id);
    }

}
