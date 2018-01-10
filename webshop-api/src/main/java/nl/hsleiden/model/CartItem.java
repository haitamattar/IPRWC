package nl.hsleiden.model;

public class CartItem {
    private Product product;
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
