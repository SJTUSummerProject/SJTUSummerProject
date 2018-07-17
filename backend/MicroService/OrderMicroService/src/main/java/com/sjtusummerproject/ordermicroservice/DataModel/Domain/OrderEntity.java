package com.sjtusummerproject.ordermicroservice.DataModel.Domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long orderId;

    private Long userId;
    private String orderStatus;

    /* 下订单的具体时间 */
    @Temporal(TemporalType.DATE)
    private Date orderTime;

    /*拥有的票品项*/
    @OneToMany(mappedBy="orderEntity",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<ItemEntity> items = new LinkedList<>();
    //拥有mappedBy注解的实体类为关系被维护端
    //mappedBy="orderEntity"中的orderEntity是ItemEntity中的OrderEntity属性

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderid() {
        return orderId;
    }

    public void setOrderid(Long orderid) {
        this.orderId = orderid;
    }

}