package dev.ncrousset.models;

import java.util.Date;

public class Product {

    private Long id;
    private String name;
    private Integer price;
    private Date date;
    private Category category;

    public Product() {
    }

    public Product(Long id, String name, Integer price, Date date, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
        this.category = category;
    }

    public Product(String name, Integer price, Date date, Category category) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return  id + " | " + name  + " | " + price + " | " + date;
    }
}
