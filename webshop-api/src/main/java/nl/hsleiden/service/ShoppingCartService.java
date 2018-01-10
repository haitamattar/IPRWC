package nl.hsleiden.service;

import nl.hsleiden.model.CartItem;
import nl.hsleiden.model.Product;
import nl.hsleiden.model.ShoppingCart;
import nl.hsleiden.model.User;
import nl.hsleiden.persistence.ShoppingCartDAO;

import javax.inject.Inject;
import java.sql.SQLException;

public class ShoppingCartService {

    private ShoppingCartDAO shoppingCartDAO;

    @Inject
    public ShoppingCartService(ShoppingCartDAO dao)
    {
        this.shoppingCartDAO = dao;
    }

    // Get shopping cart by user id
    public ShoppingCart getByUserId(User user)throws SQLException {
        return shoppingCartDAO.findShoppingCartByUserId(user);
    }

    // Insert product to shopping cart
    public boolean insertShoppingCartProduct(CartItem cartItem, User user){
        try {
            return shoppingCartDAO.insert(cartItem, user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete whole shopping cart of an user
    public boolean deleteCompleteShoppingCart(User user){
        try {
            return shoppingCartDAO.deleteCompleteShoppingCart(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateShoppingCart(ShoppingCart shoppingCart) {
        try {
            return shoppingCartDAO.updateShoppingCart(shoppingCart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
