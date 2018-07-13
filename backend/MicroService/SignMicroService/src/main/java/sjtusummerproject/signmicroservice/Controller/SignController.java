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

    @Autowired
    RestTemplate restTemplate;

    @PostMapping(value="/Up")
    @ResponseBody
    public String SignUp(HttpServletRequest request, HttpServletResponse response){
        String token = getTokenFromRequest(request, "CodeUUID");
        String answer = request.getParameter("answer");

        if (!invokeCodeService.validCode(token, answer)) return "code";
        /* 去除username的首尾空格 */
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        //System.out.println("username "+username);
        //System.out.println("password "+password);
        //System.out.println("email "+email);

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setStatus("UnActive");

        /* 先看在Redis里有没有缓存username的信息 */
        String IsRedis = redisUserManageService.QueryUserStatusRedis(user.getUsername());
        /* 如果Redis里面有缓存,看数据库是否存有对应的信息 */
        if(IsRedis == null){
            UserEntity findUser = invokeUserService.QueryUserMicroService(user.getUsername());
            /* 如果数据库中完全没有user的信息，说明是全新的用户 */
            if(findUser == null){
                /* 完全没在数据库里 */
                //System.out.println("完全没在数据库里");
                invokeUserService.AddUserMicroService(user);    /* 用户信息入库 */
                redisUserManageService.AddUserStatusRedis(user.getUsername());   /* 用户信息入redis */
                invokeEmailMessageService.AddEmailServiceRabbit(user); /* 发送邮件 */
                return "success";
            }
            else if(findUser.getStatus().equals("UnActive")){
                /* 重新发送邮件 */
                System.out.println("code 过期 重新发送邮件");
                redisUserManageService.AddUserStatusRedis(user.getUsername());   /* 用户信息入redis */
                invokeEmailMessageService.AddEmailServiceRabbit(user); /* 重新发送邮件 */
                return "resend";
            }
            else if(findUser.getStatus().equals("Active")){
                System.out.println("In the database");
                return "exited";
            }
            return "fail";
        }
        /* 如果Redis里面有缓存,则不需要再发邮件 */
        else {
            System.out.println("still in redis");
            return "fail";
        }
    }

    @PostMapping(value="/In")
    public String SignIn(HttpServletRequest request, HttpServletResponse response){
        //validate the code
        String token = getTokenFromRequest(request, "CodeUUID");
        String answer = request.getParameter("answer");
        boolean isValid = invokeCodeService.validCode(token, answer);
        System.out.println("isValid:"+isValid);
        if (!isValid) return "code";

        //validate the user
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String auth = invokeUserService.validUser(username, password);
        if (auth.isEmpty()) return "password";

        //add the user auth to the session server
		token = addCookieToResponse(response);
		redisUserManageService.AddTokenAuthRedis(token, auth);
		return "success";
    }

    @PostMapping(value="/Out")
    public void SignOut(HttpServletRequest request, HttpServletResponse response){
        String token = getTokenFromRequest(request, "Token");
        redisUserManageService.DeleteTokenRedis(token);
    }

    private String addCookieToResponse(HttpServletResponse response){
		UUID uuid = UUID.randomUUID();
		String token = uuid.toString();
        Cookie cookie = new Cookie("Token", token);
        cookie.setPath("/");
        response.addCookie(cookie);
        return token;
    }

    private String getTokenFromRequest(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();
        String token = new String();
        for (int i=0; i<cookies.length; i++) {
            System.out.println("cookie"+i+":"+cookies[i].getName()+":"+cookies[i].getValue());
            if (cookies[i].getName().equals(name)) {
                token = cookies[i].getValue();
            }
        }
        System.out.println("token:"+token);
        return token;
    }
}