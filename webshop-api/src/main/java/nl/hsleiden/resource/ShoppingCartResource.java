package nl.hsleiden.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.inject.Singleton;
import io.dropwizard.auth.Auth;
import nl.hsleiden.View;
import nl.hsleiden.model.CartItem;
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
        ShoppingCart shoppingCart = service.getByUserId(user);
        if(shoppingCart.getProducts().size() < 1){
            throw new WebApplicationException(404);
        }
        return shoppingCart;
    }

    @POST
    @RolesAllowed({"CUSTOMER"})
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Public.class)
    public boolean addItemToShoppingCart(CartItem cartItem, @Auth User user)
    {
        return service.insertShoppingCartProduct(cartItem, user);
    }

    @DELETE
    @RolesAllowed({"CUSTOMER"})
    @JsonView(View.Public.class)
    public boolean deleteUser(@Auth User user) throws SQLException {
        return service.deleteCompleteShoppingCart(user);
    }

    @PUT
    @RolesAllowed({"CUSTOMER"})
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Public.class)
    public boolean addItemToShoppingCart(ShoppingCart shoppingCart, @Auth User user) {
        shoppingCart.setUser(user);
        return service.updateShoppingCart(shoppingCart);
    }


}
