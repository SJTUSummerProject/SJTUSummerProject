package sjtusummerproject.usermicroservice.Controller;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sjtusummerproject.usermicroservice.DataModel.Dao.UserRepository;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.usermicroservice.Service.ManageUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value="/User")
public class UserController {
    @Autowired
    ManageUserService manageUserService;

    @GetMapping(value="/Query")
    @ResponseBody
    public UserEntity QueryUser(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        UserEntity user = manageUserService.QueryUserOption(username);
        return user;
    }

    @RequestMapping(value = "/QueryById")
    @ResponseBody
    public UserEntity QueryUserById(HttpServletRequest request, HttpServletResponse response){
        Long id = Long.parseLong(request.getParameter("userid"));
        UserEntity user = manageUserService.QueryUserByIdOption(id);
        return user;
    }

    @PostMapping(value = "/Add")
    @ResponseBody
    public String AddUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email, @RequestParam("status")String status, String authority ,HttpServletRequest request, HttpServletResponse response){
        //System.out.println("in add user controller");
        return manageUserService.AddUserOption(username,password,email,status);
    }

    @GetMapping(value="/Delete")
    @ResponseBody
    public void DeleteUser(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        manageUserService.DeleteUserOption(username);
    }

    @PostMapping(value="/UpdateStatus")
    @ResponseBody
    public void UpdateUserStatus(@RequestParam(name="username")String username,@RequestParam(name="status") String status,HttpServletRequest request, HttpServletResponse response){
        //System.out.println("in update status");
        //System.out.println("the username "+username);
        manageUserService.UpdateUserStatusOption(username,status);
    }

    @PostMapping(value="/UpdatePassword")
    @ResponseBody
    public void UpdateUserPassword(@RequestParam(name="username")String username,@RequestParam(name="password") String password,HttpServletRequest request, HttpServletResponse response){
        manageUserService.UpdateUserPasswordOption(username,password);
    }

    @PostMapping(value="/UpdateEmail")
    @ResponseBody
    public void UpdateUserEmail(@RequestParam(name="username")String username,@RequestParam(name="email") String email,HttpServletRequest request, HttpServletResponse response){
        manageUserService.UpdateUserEmailOption(username,email);
    }

    @PostMapping(value="/UpdateAuthority")
    @ResponseBody
    public void UpdateUserAuthority(@RequestParam(name="username")String username,@RequestParam(name="authority") String autority,HttpServletRequest request, HttpServletResponse response){
        manageUserService.UpdateUserAuthorityOption(username,autority);
    }
}
