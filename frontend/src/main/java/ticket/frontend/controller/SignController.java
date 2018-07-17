package ticket.frontend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/Sign")
public class SignController {
    @RequestMapping("/In")
    public String signIn(HttpServletRequest request, HttpServletResponse response){
        return null;
    }
}
