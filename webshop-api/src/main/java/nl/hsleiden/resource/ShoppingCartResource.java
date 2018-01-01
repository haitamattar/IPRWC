package nl.hsleiden.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.inject.Singleton;
import io.dropwizard.auth.Auth;
import nl.hsleiden.View;
import nl.hsleiden.model.Product;
import nl.hsleiden.model.ShoppingCart;
import nl.hsleiden.model.User;
import nl.hsleiden.service.ShoppingCartService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Singleton
@Path("/shoppingCart")
@Produces(MediaType.APPLICATION_JSON)
public class ShoppingCartResource {

    private ShoppingCartService service;

    @Inject
    public ShoppingCartResource(ShoppingCartService service)
    {
        this.service = service;
    }

    @GET
    @RolesAllowed({"CUSTOMER"})
    @JsonView(View.Public.class)
    public ShoppingCart retrieveShoppingCartWithUserId(@Auth User user) throws SQLException {
        return service.getByUserId(user);
    }

    @GET
    @Path("/cartSize")
    @RolesAllowed({"CUSTOMER"})
    @JsonView(View.Public.class)
    public int getTotalCountShoppingCart(@Auth User user) throws SQLException {
        return service.getTotalCountShoppingCart(user);
    }


    @POST
    @RolesAllowed({"CUSTOMER"})
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Public.class)
    public boolean addItemToShoppingCart(Product product, @Auth User user)
    {
        return service.insertShoppingCartProduct(product, user);
    }

    @DELETE
    @RolesAllowed({"CUSTOMER"})
    @JsonView(View.Public.class)
    public boolean deleteUser(@Auth User user) throws SQLException {
        return service.deleteCompleteShoppingCart(user);
    }


}
