package com.sjtusummerproject.commentmicroservice.Controller;

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

@RestController
@RequestMapping(value = "/Reply")
public class ReplyController {

    @Autowired
    ReplyService replyService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @Value("${reply.page.size}")
    private int PageSize;
    @Value("${reply.page.offset}")
    private int PageOffset;

    /*按照时间顺序 递减排列*/
    public Pageable createPageable(HttpServletRequest request){
        return new PageRequest(Integer.parseInt(request.getParameter("pagenumber"))-PageOffset, PageSize, new Sort(Sort.Direction.DESC, "createTime"));
    }

    @RequestMapping(value = "/AddToComment")
    @ResponseBody
    public String replyComment(@RequestParam(value = "userid") Long ownerId,
                               @RequestParam(value = "commentid")Long commentId,
                               @RequestParam(value = "content",required = false) String content){
        if(content == null)
            return "the content is empty";
        UserEntity userEntity = userService.queryById(ownerId);
        CommentEntity commentEntity = commentService.queryByCommentId(commentId);
        replyService.addToComment(userEntity,commentEntity,content);
        return "ok";
    }

    @RequestMapping(value = "/AddToReply")
    @ResponseBody
    public String replyReply(@RequestParam(value = "userid")Long ownerId,
                             @RequestParam(value = "replyid")Long replyId,
                             @RequestParam(value = "commentid")Long commentId,
                             @RequestParam(value = "content", required = false) String content){
        if(content == null)
            return "the contetn is empty";
        UserEntity ownerUser = userService.queryById(ownerId);
        /*被回复的reply*/
        ReplyEntity replied = replyService.queryById(replyId);
        replyService.addToReply(ownerUser,replied,commentId,content);
        return "ok";
    }

    /* page */
    @RequestMapping(value = "/QueryByParentId")
    @ResponseBody
    public Page<ReplyEntity> queryByCommentId(@RequestParam(value = "parentid") Long parentId,
                                              HttpServletRequest request){
        return replyService.queryByParentId(parentId,createPageable(request));
    }

    /* page */
    @RequestMapping(value = "/QueryByReplyId")
    @ResponseBody
    public Page<ReplyEntity> queryByReplyId(@RequestParam(value = "replyid") Long replyId,
                                            HttpServletRequest request){
        return replyService.queryByTargetObjectId(replyId,createPageable(request));
    }

    @RequestMapping(value = "/Delete")
    @ResponseBody
    public String deleteById(@RequestParam(value = "replyid")Long replyId){
        return replyService.deleteById(replyId);
    }
}
