package sjtusummerproject.usermicroservice.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/Test")
    String test(){
        return "1";
    }
}
