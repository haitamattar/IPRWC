package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.NotEmpty;

public class OrderDetail {

    @JsonView(View.Public.class)
    private long id = 0;

    @NotEmpty
    @JsonView(View.Public.class)
    private Product product;

    @NotEmpty
    @JsonView(View.Public.class)
    private Double productPrice;

    public OrderDetail() {

    }

    public OrderDetail(long id, Product product, Double productPrice) {
        this.id = id;
        this.product = product;
        this.productPrice = productPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

}
