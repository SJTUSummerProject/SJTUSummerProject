package sjtusummerproject.signmicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sjtusummerproject.signmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.signmicroservice.Service.InvokeEmailMessageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/Sign")
public class SignController {

    @Autowired
    InvokeEmailMessageService invokeEmailMessageService;

    @PostMapping(value="/Up")
    @ResponseBody
    public void SignUp(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setStatus("UnActive");

        invokeEmailMessageService.AddEmailServiceRabbit(user);
    }
}
