package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;
import java.util.List;

public class Order {

    @NotEmpty
    @JsonView(View.Public.class)
    private long id = 0;

    @JsonView(View.Public.class)
    private Timestamp orderDateTime;

    @NotEmpty
    @JsonView(View.Public.class)
    private User user;

    @NotEmpty
    @JsonView(View.Public.class)
    private List<OrderDetail> ordersDetail;



    public Order() {

    }

    public Order(long id, Timestamp orderDateTime, User user, List<OrderDetail> ordersDetail) {
        this.id = id;
        this.user = user;
        this.orderDateTime = orderDateTime;
        this.ordersDetail = ordersDetail;
    }

    public Order(User user, List<OrderDetail> ordersDetail) {
        this.id = id;
        this.user = user;
        this.orderDateTime = orderDateTime;
        this.ordersDetail = ordersDetail;
    }

    public Timestamp getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Timestamp orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderDetail> getOrdersDetail() {
        return ordersDetail;
    }

    public void setOrdersDetail(List<OrderDetail> ordersDetail) {
        this.ordersDetail = ordersDetail;
    }
}
