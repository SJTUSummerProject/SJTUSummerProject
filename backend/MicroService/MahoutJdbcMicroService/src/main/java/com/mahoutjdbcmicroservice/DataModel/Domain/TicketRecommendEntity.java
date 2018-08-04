//package com.mahoutjdbcmicroservice.DataModel.Domain;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "TicketRecommand")
//public class TicketRecommendEntity {
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    Long id;
//
//    Long ticketId;
//    /* 所属订单*/
//    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)//可选属性optional=false,表示orderEntity不能为空
//    @JoinColumn(name="user_id")//设置在item表中的关联字段(外键)
//    @JsonIgnore
//    private UserRecommendEntity userRecommendEntity;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getTicketId() {
//        return ticketId;
//    }
//
//    public void setTicketId(Long ticketId) {
//        this.ticketId = ticketId;
//    }
//
//    public UserRecommendEntity getUserRecommendEntity() {
//        return userRecommendEntity;
//    }
//
//    public void setUserRecommendEntity(UserRecommendEntity userRecommendEntity) {
//        this.userRecommendEntity = userRecommendEntity;
//    }
//}