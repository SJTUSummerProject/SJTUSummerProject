package com.sjtusummerproject.commentmicroservice.Controller;

import com.sjtusummerproject.commentmicroservice.DataModel.Dao.CommentRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@RequestMapping(value = "/Comment")
@RestController
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    String type;  //评论的种类： 评论票品("toTicket") or 评论别人的评论("toComment")
    Long targetCommentId; //如果是评论别人的评论，这是那个评论的id; 如果不是 则为null
    String content; //评论内容

    @Temporal(TemporalType.DATE)
    Date createTime; //创建时间

    @RequestMapping(value = "/Add")
    @ResponseBody
    public String add(@RequestParam(value = "userid") Long userid,@RequestParam(value = "ticketid") Long ticketId,
                      @RequestParam(value = "targetComment",required = false)Long targetCommentId,@RequestParam(value = "content",defaultValue = "")String content){
        String type;
        if(content.trim().equals(""))
            return "the content is null";
        if(targetCommentId)
    }

    @RequestMapping(value = "/test1")
    @ResponseBody
    public String test1(){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent("123123123123");

        commentRepository.save(commentEntity);
        // service.update(stu);
        System.out.println("已生成ID：" + commentEntity.getId());

        CommentEntity commentEntity1 = new CommentEntity();
        commentEntity1.setContent("1234");

        commentRepository.save(commentEntity1);
        // service.update(stu);
        System.out.println("已生成ID1：" + commentEntity1.getId());
        return "ok";
    }

}
