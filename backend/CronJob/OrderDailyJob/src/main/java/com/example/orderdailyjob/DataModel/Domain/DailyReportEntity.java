package com.example.orderdailyjob.DataModel.Domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="dailyreport")
public class DailyReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long ticketId;
    // to simplify the coding, use the string instead of the structure
    String priceAndaMount;
    Long totalPrice;
    Double rate;
    String city;
    @Temporal(TemporalType.DATE)
    Date date;

}
