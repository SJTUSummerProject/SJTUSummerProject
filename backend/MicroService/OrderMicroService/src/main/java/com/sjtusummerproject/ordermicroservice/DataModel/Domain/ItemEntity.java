package com.sjtusummerproject.ordermicroservice.DataModel.Domain;

import javax.persistence.*;

@Entity
@Table(name = "item")
public class ItemEntity  {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long itemId;

    Long ticketId;
    /* 具体的price */
    double price;
    int number;
    String image;
    String title;
    /* 具体的日期 */
    String date;
    String venue;
    String city;

    /* 所属订单*/
     @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)//可选属性optional=false,表示orderEntity不能为空
     @JoinColumn(name="order_id")//设置在item表中的关联字段(外键)
     private OrderEntity orderEntity;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }
}
