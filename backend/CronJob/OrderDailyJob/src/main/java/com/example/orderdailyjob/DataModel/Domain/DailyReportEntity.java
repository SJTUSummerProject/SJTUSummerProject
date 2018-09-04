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
    //城市
    private String city;
    //报表日期
    @Temporal(TemporalType.DATE)
    private Date date;
    //票名
    private String title;

}
