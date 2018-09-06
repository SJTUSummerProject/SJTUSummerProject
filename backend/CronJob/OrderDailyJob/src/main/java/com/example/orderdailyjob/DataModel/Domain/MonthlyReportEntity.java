package com.example.orderdailyjob.DataModel.Domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="monthlyreport")
public class MonthlyReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //票id
    private Long ticketId;
    //票名
    private String title;
    // to simplify the coding, use the string instead of the structure
    private String priceAndAmount;
    //总售出额
    private Double totalPrice;
    //售出率
    private Double rate;
    //城市
    private String city;
    //报表生成日期
    @Temporal(TemporalType.DATE)
    private Date date;

    //报表记录年份
    private int year;
    //报表记录月份
    private int month;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
