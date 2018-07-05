package sjtusummerproject.usermicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sjtusummerproject.usermicroservice.DataModel.Dao.UserRepository;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value="/User")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping(value="/Query")
    @ResponseBody
    public UserEntity QueryUser(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        UserEntity user = userRepository.findFirstByUsername(username);
        return user;
    }
}
