package nl.hsleiden.service;

import nl.hsleiden.model.Order;
import nl.hsleiden.model.User;
import nl.hsleiden.persistence.OrderDAO;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class OrderService {

    private OrderDAO orderDAO;

    @Inject
    public OrderService(OrderDAO dao)
    {
        this.orderDAO = dao;
    }


    // List all products
    public Collection<Order> getAll() throws SQLException {
        try {
            List<Order> orderList = orderDAO.allOrders();
            return orderList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // List all products
    public Collection<Order> getAllWithUserId(User user) throws SQLException {
        try {
            List<Order> orderList = orderDAO.allOrdersWithUserId(user);
            return orderList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
