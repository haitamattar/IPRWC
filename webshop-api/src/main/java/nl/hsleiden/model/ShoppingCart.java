package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class ShoppingCart {

    @NotEmpty
    @JsonView(View.Public.class)
    private User user;

    @NotEmpty
    @JsonView(View.Public.class)
    private List<CartItem> cartItems;

    public ShoppingCart(){

    }

    public ShoppingCart(User user, List<CartItem> cartItems) {
        this.user = user;
        this.cartItems = cartItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getProducts() {
        return cartItems;
    }

    public void setProducts(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
