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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/Reply")
public class ReplyController {

    @Autowired
    ReplyService replyService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    RestTemplate restTemplate;

    @Value("${reply.page.size}")
    private int PageSize;
    @Value("${reply.page.offset}")
    private int PageOffset;
    @Value("${authservice.url}")
    private String authUrl;

    /*按照时间顺序 递减排列*/
    public Pageable createPageable(HttpServletRequest request){
        return new PageRequest(Integer.parseInt(request.getParameter("pagenumber"))-PageOffset, PageSize, new Sort(Sort.Direction.DESC, "createTime"));
    }

    @RequestMapping(value = "/AddToComment")
    @ResponseBody
    public String replyComment(@RequestParam(value = "token") String token,
                               @RequestParam(value = "commentid")Long commentId,
                               @RequestParam(value = "content",required = false) String content,
                               HttpServletResponse response){
        UserEntity userEntity = callAuthService(token);
        int result = authUser(userEntity);
        response.addIntHeader("errorNum", result);
        if (result != 0) return null;

        if(content == null)
            return "the content is empty";
        replyService.addToComment(userEntity.getId(),commentId,content);
        return "ok";
    }

    @RequestMapping(value = "/AddToReply")
    @ResponseBody
    public String replyReply(@RequestParam(value = "token") String token,
                             @RequestParam(value = "replyid")Long replyId,
                             @RequestParam(value = "commentid")Long commentId,
                             @RequestParam(value = "content", required = false) String content,
                             HttpServletResponse response){
        UserEntity userEntity = callAuthService(token);
        int result = authUser(userEntity);
        response.addIntHeader("errorNum", result);
        if (result != 0) return null;

        if(content == null)
            return "the contetn is empty";
        replyService.addToReply(userEntity.getId(),replyId,commentId,content);
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
    public String deleteById(@RequestParam(value = "token") String token,
                             @RequestParam(value = "replyid")Long replyId,
                             HttpServletResponse response){
        UserEntity userEntity = callAuthService(token);
        int result = authUser(userEntity);
        response.addIntHeader("errorNum", result);
        if (result != 0) return null;

        return replyService.deleteById(replyId);
    }

    private UserEntity callAuthService(String token){
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("token", token);
        return restTemplate.postForObject(authUrl, multiValueMap, UserEntity.class);
    }

    private int authUser(UserEntity userEntity){
        if (userEntity == null) return 1;
        else if (!userEntity.getAuthority().equals("Customer")) return 2;
        else if (userEntity.getStatus().equals("Frozen")) return 3;
        else return 0;
    }
}
