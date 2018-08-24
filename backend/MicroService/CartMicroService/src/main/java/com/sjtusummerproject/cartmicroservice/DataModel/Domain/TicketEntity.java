package com.sjtusummerproject.cartmicroservice.DataModel.Domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class TicketEntity implements Serializable{

    private Long id;
    //类型：演唱会 体育赛事等等
    private String type;
    //日期
    private String dates;
    //起始日期
    private Date startDate;
    //终止日期
    private Date endDate;
    //时间
    private String time;
    //城市
    private String city;
    //地址-具体地址如某某体育馆
    private String venue;
    //标题
    private String title;
    //图片
    private String image;
    //简介
    private String intro;
    //库存
    private Long stock;
    //底价
    private double lowprice;
    //顶价
    private double highprice;

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public Date getStartDate() {
        return startDate;
    }

    public TicketEntity() {
    }

    public TicketEntity(Long id, String type, String dates, Date startDate, Date endDate, String time, String city, String venue, String title, String image, String intro, Long stock, double lowprice, double highprice) {
        this.id = id;
        this.type = type;
        this.dates = dates;
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.city = city;
        this.venue = venue;
        this.title = title;
        this.image = image;
        this.intro = intro;
        this.stock = stock;
        this.lowprice = lowprice;
        this.highprice = highprice;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLowprice() {
        return lowprice;
    }

    public void setLowprice(double lowprice) {
        this.lowprice = lowprice;
    }

    public double getHighprice() {
        return highprice;
    }

    public void setHighprice(double highprice) {
        this.highprice = highprice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
