package sjtusummerproject.emailmicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sjtusummerproject.emailmicroservice.Service.ActiveEmailService;
import sjtusummerproject.emailmicroservice.Service.SendEmailService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(value="/Email")
@RestController
public class EmailController {

    @Autowired
    SendEmailService sendEmailService;

    @Autowired
    ActiveEmailService activeEmailService;

    @GetMapping(value="/Send")
    @ResponseBody
    public void SendEmail(HttpServletRequest request, HttpServletResponse response){
        sendEmailService.sendMail("1755405701@qq.com","123");
    }

    @GetMapping(value="/Active")
    @ResponseBody
    public boolean ActiveEmail(HttpServletRequest request, HttpServletResponse response){
        return activeEmailService.Active(request.getParameter("code"));
    }
}
