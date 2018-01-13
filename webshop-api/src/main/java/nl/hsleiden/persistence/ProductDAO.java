package nl.hsleiden.persistence;

import nl.hsleiden.dbConnections.MysqlDbAccess;
import nl.hsleiden.model.Category;
import nl.hsleiden.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    final String GET_ALL_PRODUCTS = "SELECT `product`.*, `productCategory`.`id` as 'cat_id', " +
                                    " `productCategory`.`name` as 'cat_name', `blob` " +
                                    "FROM `product` " +
                                    "LEFT JOIN `productCategory` " +
                                    " ON `product`.`product_category_id` = `productCategory`.`id` " +
                                    "LEFT JOIN `blobData` ON `parent_id` = `product`.`id` " +
                                    " AND `blobData`.`table` = 'product'" +
                                    "WHERE `product`.`product_status` = 'ACTIVE';";

    final String GET_PRODUCT_BY_ID = "SELECT `product`.*, `productCategory`.`id` AS 'cat_id', " +
                                     " `productCategory`.`name` AS 'cat_name', `blob`  " +
                                     "FROM `product` " +
                                     "LEFT JOIN `productCategory` " +
                                     " ON `product`.`product_category_id` = `productCategory`.`id` " +
                                     "LEFT JOIN `blobData` ON `parent_id` = `product`.`id` " +
                                     " AND `blobData`.`table` = 'product'" +
                                     "WHERE `product`.`id` = ? AND `product`.`product_status` = 'ACTIVE';";

    final String INSERT_PRODUCT = "INSERT INTO `product` (`product_name`, `product_description`," +
                                  " `product_category_id`, `product_price`)" +
                                  " VALUES (?, ?, ?, ?);";

    // Soft delete in db
    final String DELETE_PRODUCT = "UPDATE `product` " +
                                  "SET `product_status` = 'DELETED' " +
                                  "WHERE `id` = ?;";

    // Blob data (images)
    final String INSERT_IMAGE = "INSERT INTO `blobData` (`blob`, `table`, `parent_id`) " +
                                "VALUES (?, 'product', ?);";


    // insert product
    public Product insert(Product product) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()){

            // Insert user
            PreparedStatement insert_product_ps = connection.prepareStatement(INSERT_PRODUCT, PreparedStatement.RETURN_GENERATED_KEYS);
            insert_product_ps.setString(1, product.getName());
            insert_product_ps.setString(2, product.getDescription());
            insert_product_ps.setLong(3, product.getCategory().getId());
            insert_product_ps.setDouble(4, product.getPrice());

            insert_product_ps.execute();

            // Get created user id
            ResultSet product_rs = insert_product_ps.getGeneratedKeys();
            if (!product_rs.next()) {
                throw new SQLException("No key returned for product");
            }
            product.setId(product_rs.getLong(1));
            insertBlobImage(product);
            // Close client streams
            product_rs.close();
            insert_product_ps.close();

            connection.close();
            return product;
        }
    }

    public void insertBlobImage(Product product) throws SQLException {

        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement insert_image_ps = connection.prepareStatement(INSERT_IMAGE, PreparedStatement.RETURN_GENERATED_KEYS);

            insert_image_ps.setString(1, product.getImage());
            insert_image_ps.setDouble(2, product.getId());

            insert_image_ps.execute();
            insert_image_ps.close();
            connection.close();

        }

    }


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


    // Delete product
    public int delete(Product product) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement delete_pr = connection.prepareStatement(DELETE_PRODUCT);
            delete_pr.setLong(1, product.getId());

            int rowsAffected = delete_pr.executeUpdate();

            delete_pr.close();
            connection.close();
            return rowsAffected;
        }
    }

    // create product
    public Product createProduct(ResultSet rset) throws SQLException {

        Category category = new Category(rset.getLong("cat_id"), rset.getString("cat_name"));


        Product product = new Product(
                rset.getLong("id"),
                rset.getString("product_name"),
                rset.getString("product_description"),
                category,
                rset.getDouble("product_price"),
                rset.getString("blob"));
        return product;
    }


}
