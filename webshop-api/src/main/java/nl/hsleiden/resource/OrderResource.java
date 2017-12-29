package nl.hsleiden.resource;


import com.fasterxml.jackson.annotation.JsonView;
import com.google.inject.Singleton;
import nl.hsleiden.View;
import nl.hsleiden.model.Order;
import nl.hsleiden.service.OrderService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

    @GET
    @JsonView(View.Public.class)
    public Collection<Order> retrieveAll() throws SQLException {
        return service.getAll();
    }
}
