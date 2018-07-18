package sjtusummerproject.usermicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserDetailEntity;
import sjtusummerproject.usermicroservice.Service.ManageUserDetailService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/UserDetail")
public class UserDetailController {
    @Autowired
    ManageUserDetailService manageUserDetailService;

    @RequestMapping(value = "/SaveByUserid")
    @ResponseBody
    public UserDetailEntity saveByUserid(HttpServletRequest request, HttpServletResponse response){
        Long userid = Long.parseLong(request.getParameter("userid"));
        String avatar = request.getParameter("avatar"); //头像
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        double account = Double.parseDouble(request.getParameter("account"));

        UserDetailEntity partUserDetail = new UserDetailEntity();
        partUserDetail.setId(userid);
        partUserDetail.setAvatar(avatar);
        partUserDetail.setPhone(phone);
        partUserDetail.setAddress(address);
        partUserDetail.setAccount(account);

        return manageUserDetailService.saveByUserId(userid,partUserDetail);
    }
}
