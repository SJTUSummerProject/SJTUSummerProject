package sjtusummerproject.usermicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.usermicroservice.Service.ManageUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value="/Manager")
public class ManagerController {
    @Autowired
    ManageUserService manageUserService;

    @Value("${user.page.size}")
    private int PageSize;
    @Value("${user.page.offset}")
    private int PageOffset;

//    /*递减排列*/
//    public Pageable createPageable(HttpServletRequest request){
//        return new PageRequest(Integer.parseInt(request.getParameter("pagenumber"))-PageOffset, PageSize, new Sort(Sort.Direction.DESC, "id"));
//    }

    @RequestMapping(value="/Delete")
    void deleteUser(@RequestParam(name = "id")Long userid){
        manageUserService.DeleteUserOption(userid);
    }

    @RequestMapping(value = "/QueryById")
    UserEntity queryUserById(@RequestParam(name = "id")Long userid){
        return manageUserService.QueryUserByIdOption(userid);
    }

    @RequestMapping(value = "/QueryBatch")
    Page<UserEntity> queryBatchUser(@RequestParam(name = "pagenumber")int pagenumber){
        Pageable pageable = new PageRequest(pagenumber-PageOffset,PageSize,new Sort(Sort.Direction.DESC, "id");
        return manageUserService.QueryBatch(pageable);
    }

    @RequestMapping(value = "/UpdateStatus")
    String updateUserStatus(@RequestParam(name = "id")Long userid,
                                @RequestParam(name = "status")String status){
        UserEntity afterUpdateUser = manageUserService.UpdateUserStatusOptionById(userid,status);
        return afterUpdateUser==null ? "user not exist" : "ok";
    }

    @RequestMapping(value = "/UpdatePassword")
    String updateUserPassword(@RequestParam(name = "id")Long userid,
                              @RequestParam(name = "password")String password){
        UserEntity afterUpdateUser = manageUserService.UpdateUserPasswordOptionById(userid,password);
        return afterUpdateUser==null? "user not exist" : "ok";
    }
}
