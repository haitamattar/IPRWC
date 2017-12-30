package nl.hsleiden.service;

import nl.hsleiden.model.Product;
import nl.hsleiden.persistence.ProductDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Singleton
public class ProductService {

    private ProductDAO productDAO;

    @Inject
    public ProductService(ProductDAO dao)
    {
        this.productDAO = dao;
    }

    // List all products
    public Collection<Product> getAll() throws SQLException {
        try {
            List<Product> productList = productDAO.all();
            return productList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get product by id
    public Product getById(long id)throws SQLException {
        return productDAO.findById(id);
    }

    // Get product by id
    public int delete(Product product)throws SQLException {
        return productDAO.delete(product);
    }

    // Insert new product
    public Product insertProduct(Product product){
        try {
            return productDAO.insert(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
