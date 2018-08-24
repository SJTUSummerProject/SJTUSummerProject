package sjtusummerproject.signmicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sjtusummerproject.signmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.signmicroservice.Service.InvokeCodeService;
import sjtusummerproject.signmicroservice.Service.InvokeUserService;
import sjtusummerproject.signmicroservice.Service.InvokeEmailMessageService;
import sjtusummerproject.signmicroservice.Service.RedisUserManageService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping(value = "/Sign")
public class SignController {

    @Autowired
    InvokeEmailMessageService invokeEmailMessageService;

    @Autowired
    InvokeUserService invokeUserService;

    @Autowired
    RedisUserManageService redisUserManageService;

    @Autowired
    InvokeCodeService invokeCodeService;

    RestTemplate restTemplate = new RestTemplate();

    String status = "UnActive";

    @PostMapping(value="/Up")
    @ResponseBody
    public String SignUp(HttpServletRequest request, HttpServletResponse response){
        String answer = request.getParameter("answer");

        if (!invokeCodeService.validCode(answer, answer)) return "code";
        /* 去除username的首尾空格 */
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        UserEntity user = invokeUserService.GenerateUser(username, password, email, status);
        //check rename situation
        UserEntity findUser = invokeUserService.QueryUserMicroService(username);
        // 如果数据库中完全没有user的信息，说明是全新的用户
        if(findUser == null){
            invokeUserService.AddUserMicroService(user);    /* 用户信息入库 */
            redisUserManageService.AddUserStatusRedis(user.getUsername());   /* 用户信息入redis */
            invokeEmailMessageService.AddEmailServiceRabbit(user); /* 发送邮件 */
            return "success";
        }
        //数据库中存在未激活用户,则重新发送邮件
        else if(findUser.getStatus().equals("UnActive")){
            invokeEmailMessageService.AddEmailServiceRabbit(user); /* 重新发送邮件 */
            return "resend";
        }
        //用户名已经存在
        return "exited";
    }

    @PostMapping(value="/In")
    public String SignIn(HttpServletRequest request, HttpServletResponse response){
        //validate the code
        String answer = request.getParameter("answer");
        boolean isValid = invokeCodeService.validCode(answer, answer);
        if (!isValid) return "code";

        //validate the user
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserEntity userEntity = invokeUserService.validUser(username, password);
        //get the result
        if (userEntity == null) return "fail";
        else if (userEntity.getStatus().equals("UnActive")){
            return "UnActive";
        }
        else{
            String token = UUID.randomUUID().toString();
            redisUserManageService.AddTokenUserRedis(token, userEntity);
            return token;
        }
    }

    @PostMapping(value="/Out")
    public void SignOut(HttpServletRequest request, HttpServletResponse response){
        String token = request.getParameter("token");
        redisUserManageService.DeleteTokenRedis(token);
    }
}