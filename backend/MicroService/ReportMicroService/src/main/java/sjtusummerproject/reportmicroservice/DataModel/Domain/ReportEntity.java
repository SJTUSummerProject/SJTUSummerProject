package sjtusummerproject.reportmicroservice.DataModel.Domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table( name = "report" )
public class ReportEntity {
    @Id
    @GeneratedValue()
    private Long id;
    //报表生成日期
    @Temporal(TemporalType.DATE)
    private Date generateDate;
    //统计起始日期
    @Temporal(TemporalType.DATE)
    private Date startDate;
    //统计终止日期
    @Temporal(TemporalType.DATE)
    private Date endDate;
    //票id
    private Long ticketid;
    //票名
    private String title;
    //销售数量
    private Long number;
    //单价
    private String unitprices;
    //总售出额
    private double totalprice;
    //城市
    private String city;
    //上座率
    private double placingrate;
    //日表-0 周表-1 月表-2 年表-3 type
    private int type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getTicketid() {
        return ticketid;
    }

    public void setTicketid(Long ticketid) {
        this.ticketid = ticketid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getUnitprices() {
        return unitprices;
    }

    public void setUnitprices(String unitprices) {
        this.unitprices = unitprices;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getPlacingrate() {
        return placingrate;
    }

    public void setPlacingrate(double placingrate) {
        this.placingrate = placingrate;
    }

    public Date getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(Date generateDate) {
        this.generateDate = generateDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
