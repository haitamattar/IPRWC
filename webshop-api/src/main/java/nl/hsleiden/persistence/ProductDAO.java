package nl.hsleiden.persistence;

import nl.hsleiden.dbConnections.MysqlDbAccess;
import nl.hsleiden.model.Category;
import nl.hsleiden.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    String GET_ALL_PRODUCTS = "SELECT product.*, productCategory.id as 'cat_id', productCategory.name as 'cat_name' " +
                              "FROM product " +
                              "LEFT JOIN productCategory on product.product_category_id = productCategory.id";

    String GET_PRODUCT_BY_ID = "SELECT product.*, productCategory.id AS 'cat_id', productCategory.name AS 'cat_name' " +
                               "FROM product " +
                               "LEFT JOIN productCategory " +
                               "ON product.product_category_id = productCategory.id " +
                               "WHERE product.id = ?";

    // Get all products
    public List<Product> all() throws SQLException {
        System.out.println("LIST DATA");
        ArrayList<Product> products = new ArrayList<Product>();

        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(GET_ALL_PRODUCTS);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                products.add(createProduct(rset));
            }

            pstmt.close();
            connection.close();

            return products;
        }
    }

    // Get product by id
    public Product findById(long id) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(GET_PRODUCT_BY_ID);
            pstmt.setLong(1, id);

            Product product = null;
            ResultSet rset = pstmt.executeQuery();

            while(rset.next()){
                product = createProduct(rset);
            }

            pstmt.close();
            connection.close();

            return product;
        }
    }

    // create product
    private Product createProduct(ResultSet rset) throws SQLException {

        Category category = new Category(rset.getLong("cat_id"), rset.getString("cat_name"));


        Product product = new Product(
                rset.getLong("id"),
                rset.getString("product_name"),
                rset.getString("product_description"),
                category,
                rset.getDouble("product_price"));
        return product;
    }


}
