package nl.hsleiden.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.inject.Singleton;
import nl.hsleiden.View;
import nl.hsleiden.model.Product;
import nl.hsleiden.service.ProductService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.Collection;

@Singleton
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
    private ProductService service;

    @Inject
    public ProductResource(ProductService service)
    {
        this.service = service;
    }

    @GET
    @JsonView(View.Public.class)
    public Collection<Product> retrieveAll() throws SQLException {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    @JsonView(View.Public.class)
    public Product retrieve(@PathParam("id") long id) throws SQLException {
        return service.getById(id);
    }

}
