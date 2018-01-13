package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.NotEmpty;

public class CartItem {

    @JsonView(View.Public.class)
    private Product product;

    @JsonView(View.Public.class)
    private int total = 1;

    public CartItem() {

    }

    public CartItem(Product product, int total) {
        this.product = product;
        if (total > 0 ) {
            this.total = total;
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        if (total > 0 ) {
            this.total = total;
        }
    }
}
