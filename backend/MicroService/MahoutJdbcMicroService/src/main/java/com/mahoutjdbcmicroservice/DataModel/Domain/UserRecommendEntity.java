package com.mahoutjdbcmicroservice.DataModel.Domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "UserRecommand")
public class UserRecommendEntity {
    @Id
    private Long id;//就是userid
    private LinkedList<Long> ticketRecommends;

//    /*拥有的推荐项*/
//    @OneToMany(mappedBy="TicketRecommendEntity",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
//    private Set<TicketRecommendEntity> ticketRecommends = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LinkedList<Long> getTicketRecommends() {
        return ticketRecommends;
    }

    public void setTicketRecommends(LinkedList<Long> ticketRecommends) {
        this.ticketRecommends = ticketRecommends;
    }

    //    public Set<TicketRecommendEntity> getTicketRecommends() {
//        return ticketRecommends;
//    }
//
//    public void setTicketRecommends(Set<TicketRecommendEntity> ticketRecommends) {
//        this.ticketRecommends = ticketRecommends;
//    }
}