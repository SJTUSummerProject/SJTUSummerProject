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
}
