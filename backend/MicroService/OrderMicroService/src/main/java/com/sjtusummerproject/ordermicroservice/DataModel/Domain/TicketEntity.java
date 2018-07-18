package com.sjtusummerproject.ordermicroservice.DataModel.Domain;

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
