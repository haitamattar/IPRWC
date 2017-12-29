package nl.hsleiden.persistence;

import nl.hsleiden.dbConnections.MysqlDbAccess;
import nl.hsleiden.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // Order

    String GET_ORDERS_BY_USERID = "SELECT `order`.*, `orderDetail`.`product_id` AS 'orderDetail_id', " +
                                  " `orderDetail`.`product_id`, `orderDetail`.`product_price` " +
                                  "FROM `order` " +
                                  "LEFT JOIN `orderDetail` ON `order`.`id` = `orderDetail`.`order_id` " +
                                  "WHERE `order`.`user_id` = ? ;";

    String GET_ALL_ORDERS = "SELECT `order`.*,  `user`.`email`, `user`.`fullname`, `user`.`postalcode`," +
                            "  `user`.`streetnumber`, `user`.`role` " +
                            "FROM `order` " +
                            "LEFT JOIN `user` ON `order`.`user_id` = `user`.`id`;";

    String INSERT_ORDER = "INSERT INTO `order` (`user_id`) " +
                          "VALUES (?);";


    // Order details

    String GET_ALL_ORDERDETAILS_WITH_ORDERID = "SELECT `orderDetail`.*, `product`.`product_name`, `product`.`product_description`, " +
                                               " `product`.`product_price` AS 'current_productprice', `product`.`product_category_id`, " +
                                               " `productCategory`.`name` AS 'category_name' " +
                                               "FROM `orderDetail` " +
                                               "LEFT JOIN `product` ON `product_id` = `product`.`id` " +
                                               "LEFT JOIN `productCategory` ON `product_category_id` = `productCategory`.`id` " +
                                               "WHERE `order_id` = ? ;";


    // insert product
    public Order insert(Order order) throws SQLException {
        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()){

            // Insert user
            PreparedStatement insert_product_ps = connection.prepareStatement(INSERT_ORDER, PreparedStatement.RETURN_GENERATED_KEYS);
            insert_product_ps.setDouble(1, order.getUser().getId());


            insert_product_ps.execute();

            // Get created user id
            ResultSet product_rs = insert_product_ps.getGeneratedKeys();
            if (!product_rs.next()) {
                throw new SQLException("No key returned for product");
            }
            order.setId(product_rs.getLong(1));

            // Close client streams
            product_rs.close();
            insert_product_ps.close();

            connection.close();
            return order;
        }
    }

    // Get all orders
    public List<Order> allOrders() throws SQLException {
        System.out.println("LIST DATA");
        ArrayList<Order> orders = new ArrayList<Order>();

        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(GET_ALL_ORDERS);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Order order = createOrder(rset);
                // Get all order details from order
                order.setOrdersDetail(allOrderDetails(order.getId()));
                orders.add(order);
            }

            pstmt.close();
            connection.close();

            return orders;
        }
    }

    // Get all ordersDetails from an order
    public List<OrderDetail> allOrderDetails(Long orderId) throws SQLException {
        ArrayList<OrderDetail> orderDetail = new ArrayList<OrderDetail>();

        try (Connection connection = MysqlDbAccess.getDatabase().openConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(GET_ALL_ORDERDETAILS_WITH_ORDERID);
            pstmt.setLong(1, orderId);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                orderDetail.add(createOrderDetail(rset));
            }

            pstmt.close();
            connection.close();

            return orderDetail;
        }
    }

    // create Order
    private Order createOrder(ResultSet rset) throws SQLException {
//            public User(String email, String fullName, String postcode, String streetnumber, String role) {


            User user = new User(
                rset.getLong("user_id"),
                rset.getString("email"),
                rset.getString("fullname"),
                rset.getString("postalcode"),
                rset.getString("streetnumber"),
                rset.getString("role"));

        Order order = new Order(
                rset.getLong("id"),
                rset.getTimestamp("order_date_time"),
                user,
                null);

        return order;
    }

    // create OrderDetail
    private OrderDetail createOrderDetail(ResultSet rset) throws SQLException {

//            public Product(long id, String name, String description, Category category, double price) {


            Product product = new Product(
                rset.getLong("product_id"),
                rset.getString("product_name"),
                rset.getString("product_description"),
                new Category(rset.getLong("product_category_id"),
                    rset.getString("category_name")),
                rset.getDouble("product_price")
                );



        OrderDetail orderdetail = new OrderDetail(
                rset.getLong("id"),
                product,
                rset.getDouble("product_price"));

        return orderdetail;
    }


}
