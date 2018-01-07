package nl.hsleiden.persistence;

import nl.hsleiden.dbConnections.MysqlDbAccess;
import nl.hsleiden.model.Product;
import nl.hsleiden.model.ShoppingCart;
import nl.hsleiden.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShoppingCartDAO {

    final String GET_ALL_ITEMS_FROM_SHOPPING_CART = "SELECT `shoppingCart`.*, `product`.*, " +
                                                    "`productCategory`.`id` AS 'cat_id', " +
                                                    "`productCategory`.`name` AS 'cat_name', `blob` " +
                                                    "FROM `shoppingCart`" +
                                                    "LEFT JOIN `product` " +
                                                    " ON `shoppingCart`.`product_id` = `product`.`id`" +
                                                    "LEFT JOIN `productCategory` " +
                                                    " ON `product`.`product_category_id` = `productCategory`.`id` " +
                                                    "LEFT JOIN `blobData` ON `parent_id` = `product`.`id` " +
                                                    " AND `blobData`.`table` = 'product'" +
                                                    "WHERE `product`.`product_status` = 'ACTIVE' " +
                                                    " AND `shoppingCart`.`user_id` = ?;";

    final String ADD_ITEMS_TO_SHOPPING_CART = "INSERT INTO `shoppingCart` (`user_id`, `product_id`) " +
                                              "VALUES (?, ?);";

    final String COUNT_TOTAL_ITEMS_IN_SHOPPING_CART = "SELECT COUNT(*) as 'total_shopping_cart' " +
                                                      "FROM `shoppingCart` " +
                                                      "WHERE `user_id` = ?";


    final String DELETE_WHOLE_SHOPPING_CART = "DELETE " +
                                              "FROM `shoppingCart` " +
                                              "WHERE `user_id` = ?;";


    // insert product to shopping cart
    public Boolean insert(Product product, User user) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()){

            // Insert user
            PreparedStatement insert_shoppingCart_ps = connection.prepareStatement(ADD_ITEMS_TO_SHOPPING_CART, PreparedStatement.RETURN_GENERATED_KEYS);
            insert_shoppingCart_ps.setLong(1, user.getId());
            insert_shoppingCart_ps.setLong(2, product.getId());

            insert_shoppingCart_ps.execute();

            // Close client streams
            insert_shoppingCart_ps.close();

            connection.close();
            return true;
        }
    }


    // Get shopping cart with user id
    public ShoppingCart findShoppingCartByUserId(User user) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(GET_ALL_ITEMS_FROM_SHOPPING_CART);
            pstmt.setLong(1, user.getId());

            ResultSet rset = pstmt.executeQuery();

            ArrayList<Product> products = new ArrayList<Product>();

            while(rset.next()) {
                products.add(MysqlDbAccess.getDatabase().getProductDao().createProduct(rset));
            }

            ShoppingCart shoppingCart = new ShoppingCart(user, products);

            pstmt.close();
            connection.close();

            return shoppingCart;
        }
    }


    // Get total count of shopping cart
    public int getTotalCountOfShoppingCart(User user) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(COUNT_TOTAL_ITEMS_IN_SHOPPING_CART);
            pstmt.setLong(1, user.getId());

            ResultSet rset = pstmt.executeQuery();
            int totalCount = 0;
            if (rset.next()){
                totalCount = rset.getInt("total_shopping_cart");
            }
            pstmt.close();
            connection.close();

            return totalCount;
        }
    }

    // Delete whole shopping cart of an user (always after checkout)
    public boolean deleteCompleteShoppingCart(User user) throws SQLException{
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(DELETE_WHOLE_SHOPPING_CART);
            pstmt.setLong(1, user.getId());

            int rowsAffected = pstmt.executeUpdate();

            pstmt.close();
            connection.close();
            return rowsAffected > 0;
        }
    }


}
