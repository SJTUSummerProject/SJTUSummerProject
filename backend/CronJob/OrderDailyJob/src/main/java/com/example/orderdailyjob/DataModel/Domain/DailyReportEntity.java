package com.example.orderdailyjob.DataModel.Domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="dailyreport")
public class DailyReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //票id
    private Long ticketId;
    // to simplify the coding, use the string instead of the structure
    private String priceAndAmount;
    //总售出额
    private Double totalPrice;
    //上座率
    private Double rate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getPriceAndAmount() {
        return priceAndAmount;
    }

    public void setPriceAndAmount(String priceAndAmount) {
        this.priceAndAmount = priceAndAmount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //城市
    private String city;
    //报表日期
    @Temporal(TemporalType.DATE)
    private Date date;
    //票名
    private String title;

}
