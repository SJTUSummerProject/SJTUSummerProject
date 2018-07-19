package com.sjtusummerproject.commentmicroservice.DataModel.Domain;

import com.sjtusummerproject.commentmicroservice.Annotation.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Document(collection = "Comment")
public class CommentEntity {
    @Id
    @GeneratedValue
    Long id = 0L;

    Long ownerId; //发表评论的用户id
    String type;  //评论的种类： 评论票品("toTicket") or 评论别人的评论("toComment")
    Long targetTicketId; //如果的票品的id
    Long targetCommentId; //如果是评论别人的评论，这是那个评论的id; 如果不是 则为null
    String content; //评论内容

    @Temporal(TemporalType.DATE)
    Date createTime; //创建时间

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTargetTicketId() {
        return targetTicketId;
    }

    public void setTargetTicketId(Long targetTicketId) {
        this.targetTicketId = targetTicketId;
    }

    public Long getTargetCommentId() {
        return targetCommentId;
    }

    public void setTargetCommentId(Long targetCommentId) {
        this.targetCommentId = targetCommentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
