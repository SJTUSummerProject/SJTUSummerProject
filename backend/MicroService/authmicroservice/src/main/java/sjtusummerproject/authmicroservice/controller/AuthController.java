package sjtusummerproject.authmicroservice.controller;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sjtusummerproject.authmicroservice.entity.UserEntity;
import sjtusummerproject.authmicroservice.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/Auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @RequestMapping("/User")
    public UserEntity authUser(HttpServletRequest request, HttpServletResponse response){
        String token = request.getParameter("token");
        return authService.AuthUser(token);
    }
}
