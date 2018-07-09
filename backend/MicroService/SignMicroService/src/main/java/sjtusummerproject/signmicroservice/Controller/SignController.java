package sjtusummerproject.signmicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sjtusummerproject.signmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.signmicroservice.Service.InvokeUserService;
import sjtusummerproject.signmicroservice.Service.InvokeEmailMessageService;
import sjtusummerproject.signmicroservice.Service.RedisUserManageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/Sign")
public class SignController {

    @Autowired
    InvokeEmailMessageService invokeEmailMessageService;

    @Autowired
    InvokeUserService invokeUserService;

    @Autowired
    RedisUserManageService redisUserManageService;

    @PostMapping(value="/Up")
    @ResponseBody
    public void SignUp(HttpServletRequest request, HttpServletResponse response){
        /* 去除username的首尾空格 */
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        System.out.println("username "+username);
        System.out.println("password "+password);
        System.out.println("email "+email);

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setStatus("UnActive");

        /* 先看在Redis里有没有缓存username的信息 */
        String IsRedis = redisUserManageService.QueryUserStatusRedis(user.getUsername());
        /* 如果Redis里面有缓存,看数据库是否存有对应的信息 */
        if(IsRedis == null){
            UserEntity findUser = invokeUserService.QueryUserMicroService(user);
            /* 如果数据库中完全没有user的信息，说明是全新的用户 */
            if(findUser == null){
                /* 完全没在数据库里 */
                System.out.println("完全没在数据库里");
                invokeUserService.AddUserMicroService(user);    /* 用户信息入库 */
                redisUserManageService.AddUserStatusRedis(user.getUsername());   /* 用户信息入redis */
                invokeEmailMessageService.AddEmailServiceRabbit(user); /* 发送邮件 */
            }
            else if(findUser.getStatus().equals("UnActive")){
                /* 重新发送邮件 */
                System.out.println("code 过期 重新发送邮件");
                redisUserManageService.AddUserStatusRedis(user.getUsername());   /* 用户信息入redis */
                invokeEmailMessageService.AddEmailServiceRabbit(user); /* 重新发送邮件 */
            }
            else if(findUser.getStatus().equals("Active")){
                System.out.println("In the database");
            }
        }
        /* 如果Redis里面有缓存,则不需要再发邮件 */
        else if(IsRedis.equals("ok")){
            System.out.println("still in redis");
        }
    }

    @PostMapping(value="/In")
    public String SignIn(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserEntity receiveUser = new UserEntity();
        receiveUser.setUsername(username);
        receiveUser.setPassword(password);

        String Ispassword = redisUserManageService.QueryUserPasswordRedis(username);
        /* 已经被缓存 */
        if(Ispassword != null){
            if(Ispassword.equals(password)){
                return "ok";
            }else{
                return "password not match";
            }
        }
        /* 没有被缓存 */
        else {
            UserEntity userEntity = invokeUserService.QueryUserMicroService(receiveUser);

            /* 不存在此用户 */
            if (userEntity == null) {
                return "no user exists";
            } else {
                /* 账号匹配，密码匹配 */
                if (userEntity.getPassword().equals(password)) {
                    redisUserManageService.AddUserPasswordRedis(username,password);
                    return "ok";
                }
                /* 账号匹配，密码不匹配 */
                else {
                    return "password not match";
                }
            }
        }
    }

    @PostMapping(value="/Out")
    public String SignOut(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");

        return null;
    }
}