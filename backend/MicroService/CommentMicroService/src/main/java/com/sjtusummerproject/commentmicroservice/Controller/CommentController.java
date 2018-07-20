package com.sjtusummerproject.commentmicroservice.Controller;

import com.sjtusummerproject.commentmicroservice.DataModel.Dao.CommentRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.ReplyEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.commentmicroservice.Service.CommentService;
import com.sjtusummerproject.commentmicroservice.Service.ReplyService;
import com.sjtusummerproject.commentmicroservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping(value = "/Comment")
@RestController
public class CommentController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    ReplyService replyService;

    @Value("${comment.page.size}")
    private int PageSize;
    @Value("${comment.page.offset}")
    private int PageOffset;

    /*按照时间顺序 递减排列*/
    public Pageable createPageable(HttpServletRequest request){
        return new PageRequest(Integer.parseInt(request.getParameter("pagenumber"))-PageOffset, PageSize, new Sort(Sort.Direction.DESC, "createTime"));
    }

    @RequestMapping(value = "/Add")
    @ResponseBody
    public String add(@RequestParam(value = "userid") Long ownerId,@RequestParam(value = "ticketid") Long targetTicketId,
                      @RequestParam(value = "content",defaultValue = "")String content){
        if(content.trim().equals(""))
            return "the content is null";
        UserEntity userEntity = userService.queryById(ownerId);
        commentService.save(ownerId,targetTicketId,content);
        return "ok";
    }

    @RequestMapping(value = "/QueryByUserid")
    @ResponseBody
    public Page<CommentEntity> queryByUserid(@RequestParam(value = "userid") Long userid,HttpServletRequest request){
        return commentService.queryByOwnerId(userid,createPageable(request));
    }

    @RequestMapping(value = "/QueryByTicketid")
    @ResponseBody
    public Page<CommentEntity> queryByTicketid(@RequestParam(value = "ticketid") Long ticketid,HttpServletRequest request){
        return commentService.queryByTicketId(ticketid,createPageable(request));
    }

    @RequestMapping(value = "/UpdateContentByCommentid")
    @ResponseBody
    public CommentEntity updateContentByCommentid(@RequestParam(value = "commentid") Long commentId,@RequestParam(value = "content") String content){
        if(content.trim().equals(""))
            return null;
        return commentService.updateContentByCommentid(commentId,content);
    }

    @RequestMapping(value = "/DeleteByCommentid")
    @ResponseBody
    public CommentEntity deleteByCommentid(@RequestParam(value = "commentid") Long commentId,@RequestParam(value = "content") String content){
        replyService.deleteByParentId(commentId);
        return commentService.deleteByCommentid(commentId);
    }
    /*****************************************************************/
    /** 测试 **/
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
