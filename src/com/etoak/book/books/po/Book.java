package com.etoak.book.books.po;

import java.util.Date;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
public class Book {
    private String id;
    private String name;
    private Double price;
    private String author;
    private Date publishdate;
    private Category category;
    private String categoryid;
    private String status;

    public Book() {
    }

    public Book(String id, String name, Double price, String author, Date publishdate, String categoryid, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.author = author;
        this.publishdate = publishdate;
        this.categoryid = categoryid;
        this.status = status;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", author='" + author + '\'' +
                ", publishdate=" + publishdate +
                ", category=" + category +
                ", categoryid='" + categoryid + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
