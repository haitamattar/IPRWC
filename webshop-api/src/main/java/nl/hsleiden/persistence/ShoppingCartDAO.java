package nl.hsleiden.persistence;

import nl.hsleiden.dbConnections.MysqlDbAccess;
import nl.hsleiden.model.CartItem;
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

    final String ADD_ITEMS_TO_SHOPPING_CART = "INSERT INTO `shoppingCart` (`user_id`, `product_id`, `total`) " +
                                              "VALUES (?, ?, ?);";

    final String DELETE_WHOLE_SHOPPING_CART = "DELETE " +
                                              "FROM `shoppingCart` " +
                                              "WHERE `user_id` = ?;";

    final String UPDATE_SHOPPING_CART = "UPDATE `shoppingCart` " +
                                        "SET `total`= ? WHERE `user_id`=? AND `product_id` = ?;";


    // insert product to shopping cart
    public Boolean insert(CartItem cartItem, User user) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()){

            // Insert user
            PreparedStatement insert_shoppingCart_ps = connection.prepareStatement(ADD_ITEMS_TO_SHOPPING_CART, PreparedStatement.RETURN_GENERATED_KEYS);
            insert_shoppingCart_ps.setLong(1, user.getId());
            insert_shoppingCart_ps.setLong(2, cartItem.getProduct().getId());
            insert_shoppingCart_ps.setLong(3, cartItem.getTotal());

            insert_shoppingCart_ps.execute();

            // Close client streams
            insert_shoppingCart_ps.close();

            connection.close();
            return true;
        }
    }

    // insert shopping cart to shopping cart
    public Boolean insert(ShoppingCart shoppingCart) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()){
            connection.setAutoCommit(false);

            // Insert user
            PreparedStatement insert_shoppingCart_ps = connection.prepareStatement(ADD_ITEMS_TO_SHOPPING_CART, PreparedStatement.RETURN_GENERATED_KEYS);
            for(CartItem cartItem : shoppingCart.getProducts()) {
                insert_shoppingCart_ps.setLong(1, shoppingCart.getUser().getId());
                insert_shoppingCart_ps.setLong(2, cartItem.getProduct().getId());
                insert_shoppingCart_ps.setLong(3, cartItem.getTotal());
                insert_shoppingCart_ps.addBatch();
            }

            insert_shoppingCart_ps.executeBatch();
            connection.commit();
            // Close client streams
            insert_shoppingCart_ps.close();

            connection.close();
            return true;
        }
    }

    // Update products in shopping cart
    public boolean updateShoppingCart(ShoppingCart shoppingCart) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement update_ps = connection.prepareStatement(UPDATE_SHOPPING_CART);

            for(CartItem cartItem : shoppingCart.getProducts()) {
                update_ps.setInt(1, cartItem.getTotal());
                update_ps.setLong(2, shoppingCart.getUser().getId());
                update_ps.setLong(3, cartItem.getProduct().getId());
                update_ps.addBatch();
            }

            update_ps.executeBatch();
            connection.commit();

            update_ps.close();
            return true;
        }

    }


    // Get shopping cart with user id
    public ShoppingCart findShoppingCartByUserId(User user) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(GET_ALL_ITEMS_FROM_SHOPPING_CART);
            pstmt.setLong(1, user.getId());

            ResultSet rset = pstmt.executeQuery();

            ArrayList<CartItem> cartItems = new ArrayList<CartItem>();

            while(rset.next()) {
                CartItem cartItem = new CartItem(MysqlDbAccess.getDatabase().getProductDao().createProduct(rset),
                                                 rset.getInt("total"));
                cartItems.add(cartItem);
            }

            ShoppingCart shoppingCart = new ShoppingCart(user, cartItems);

            pstmt.close();
            connection.close();

            return shoppingCart;
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
            System.out.println("GOEIEEE");
            return rowsAffected > 0;
        }
    }


}
