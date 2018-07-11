package sjtusummerproject.ticketmicroservice.DataModel.Domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "ticket" )
public class TicketEntity {
    @Id
    @GeneratedValue()
    private Long id;
    //类型：演唱会 体育赛事等等
    private String type;
    //日期
    private String date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
