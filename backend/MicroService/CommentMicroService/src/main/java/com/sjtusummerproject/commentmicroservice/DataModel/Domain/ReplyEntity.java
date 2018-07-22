package com.sjtusummerproject.commentmicroservice.DataModel.Domain;


import javax.persistence.*;
import java.util.Date;

@Table(name = "Reply")
@Entity
public class ReplyEntity {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    Long id;

    Long ownerId; //回复"评论"or回复"回复"的用户id
    String ownername; //回复 的 用户username

    Long targetUserId; //
    String targetUsername; //
    Long targetObjectId; // 回复目标id，是commentId or replyId
    Long parentId;//comment|reply
    String type; // 回复类型 - "toComment" or "toReply"
    String content; //回复内容

    @Temporal(TemporalType.DATE)
    Date createTime; //创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public Long getTargetObjectId() {
        return targetObjectId;
    }

    public void setTargetObjectId(Long targetObjectId) {
        this.targetObjectId = targetObjectId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
