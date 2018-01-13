package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mysql.cj.jdbc.Blob;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.awt.*;


public class Product {

    @JsonView(View.Public.class)
    private long id = 0;

    @NotEmpty
    @Length(min = 8)
    @JsonView(View.Public.class)
    private String name;

    @NotEmpty
    @Length(min = 8)
    @JsonView(View.Public.class)
    private String description;

    @NotEmpty
    @JsonView(View.Public.class)
    private Category category;

    @NotEmpty
    @JsonView(View.Public.class)
    private double price;

    @JsonView(View.Public.class)
    private String image;


    public Product() {

    }

    public Product(long id, String name, String description, Category category, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public Product(long id, String name, String description, Category category, double price, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.image = image;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
