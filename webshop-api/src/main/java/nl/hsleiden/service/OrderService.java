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

    // Insert order
    public Order insert(Order order){
        try{
            return orderDAO.insert(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // List all orders
    public Collection<Order> getAll() throws SQLException {
        try {
            List<Order> orderList = orderDAO.allOrders();
            return orderList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // List all orders with userID
    public Collection<Order> getAllWithUserId(User user) throws SQLException {
        try {
            List<Order> orderList = orderDAO.allOrdersWithUserId(user);
            return orderList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get order by id and user auth
    public Order getOrderByIdAndUserAuth(long id, User user)throws SQLException {
        return orderDAO.findOrderById(id, user);
    }

    // Get order by id (only for admins)
    public Order getOrderById(long id)throws SQLException {
        return orderDAO.findOrderById(id);
    }
}
