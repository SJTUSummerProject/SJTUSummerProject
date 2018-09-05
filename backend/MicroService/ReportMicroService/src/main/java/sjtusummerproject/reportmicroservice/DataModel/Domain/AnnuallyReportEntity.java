package sjtusummerproject.reportmicroservice.DataModel.Domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="annuallyreport")
public class AnnuallyReportEntity {
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
}
