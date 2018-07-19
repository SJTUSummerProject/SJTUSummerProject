package com.sjtusummerproject.ordermicroservice.DataModel.Domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long orderId;

    private Long userId;
    private String receiver;
    private String phone;
    private String address;
    /*
    * 退款审核
    * 已退款
    * 待付款
    * 已过期
    * 已删除
    * 待发货 - attention：没有 "已付款" 这个状态，付款之后就直接变成待发货的状态
    * 已签收
    * */
    private String status;

    /* 下订单的具体时间 */
    @Temporal(TemporalType.DATE)
    private Date orderTime;

    /*拥有的票品项*/
    @OneToMany(mappedBy="orderEntity",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<ItemEntity> items = new HashSet<>();
    //拥有mappedBy注解的实体类为关系被维护端

    /*************************/
    /* getter and setter */
    public OrderEntity() {
    }
    //mappedBy="orderEntity"中的orderEntity是ItemEntity中的OrderEntity属性

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Set<ItemEntity> getItems() {
        return items;
    }

    public void setItems(Set<ItemEntity> items) {
        this.items = items;
    }

    public Long getOrderId() {

        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
