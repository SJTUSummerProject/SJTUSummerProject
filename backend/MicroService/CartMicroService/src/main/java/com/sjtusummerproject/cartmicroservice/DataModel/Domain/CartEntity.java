package com.sjtusummerproject.cartmicroservice.DataModel.Domain;

import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;


@Entity
@Table(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    Long userId;
    Long ticketId;
    double price;
    int number;
    String image;
    String title;
    String date;
    String venue;

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
