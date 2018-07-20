package com.sjtusummerproject.commentmicroservice.DataModel.Domain;

import com.sjtusummerproject.commentmicroservice.Annotation.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Document(collection = "Comment")
public class CommentEntity {
    /*评论entity 只能是评论票品*/
    @Id
    @GeneratedValue
    Long id = 0L;

    Long ownerId; //发表评论的用户id
    String ownername; //发表评论的用户名
    Long targetTicketId; //评论的票品的id
    String content; //评论内容
    String replys;
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

    public Long getTargetTicketId() {
        return targetTicketId;
    }

    public void setTargetTicketId(Long targetTicketId) {
        this.targetTicketId = targetTicketId;
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getReplys() {
        return replys;
    }

    public void setReplys(String replys) {
        this.replys = replys;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }
}
