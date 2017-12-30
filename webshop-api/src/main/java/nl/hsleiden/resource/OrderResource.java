package nl.hsleiden.resource;


import com.fasterxml.jackson.annotation.JsonView;
import com.google.inject.Singleton;
import io.dropwizard.auth.Auth;
import nl.hsleiden.View;
import nl.hsleiden.model.Order;
import nl.hsleiden.model.User;
import nl.hsleiden.service.OrderService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.Collection;

@Singleton
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    private OrderService service;

    @Inject
    public OrderResource(OrderService service)
    {
        this.service = service;
    }

    // Get all orders of all users (only for admin)
    @GET
    @RolesAllowed({"ADMIN"})
    @JsonView(View.Public.class)
    public Collection<Order> retrieveAll() throws SQLException {
        return service.getAll();
    }

    @GET
    @Path("/userOrders")
    @RolesAllowed({"CUSTOMER"})
    @JsonView(View.Public.class)
    public Collection<Order> retrieveAllordersWithUserId(@Auth User user) throws SQLException {
        return service.getAllWithUserId(user);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"CUSTOMER"})
    @JsonView(View.Public.class)
    public Order retrieveOrderWithIdAndCurrentUser(@PathParam("id") Long id, @Auth User user) throws SQLException {
        return service.getOrderByIdAndUserAuth(id, user);
    }

    @GET
    @Path("/userOrder/{id}")
    @RolesAllowed({"ADMIN"})
    @JsonView(View.Public.class)
    public Order retrieveOrderWithId(@PathParam("id") Long id) throws SQLException {
        return service.getOrderById(id);
    }

    @POST
    @RolesAllowed({"ADMIN", "FRANCHISER"})
    @JsonView(View.Public.class)
    @Consumes(MediaType.APPLICATION_JSON)
    public Order insert(Order order){
        return service.insert(order);
    }




}
