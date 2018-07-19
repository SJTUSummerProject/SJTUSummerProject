package sjtusummerproject.usermicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserDetailEntity;
import sjtusummerproject.usermicroservice.Service.ManageUserDetailService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping(value = "/UserDetail")
public class UserDetailController {
    @Autowired
    ManageUserDetailService manageUserDetailService;

    @RequestMapping(value = "/SaveByUserid")
    @ResponseBody
    public UserDetailEntity saveByUserid(HttpServletRequest request,
                                         @RequestParam(name = "avatar", required = false) MultipartFile frontAvatar,
                                         HttpServletResponse response){
        Long userid = Long.parseLong(request.getParameter("userid").trim());
        String avatar = manageUserDetailService.saveAvatar(frontAvatar); //头像
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        double account = Double.parseDouble(request.getParameter("account").trim());

        UserDetailEntity partUserDetail = new UserDetailEntity();
        partUserDetail.setId(userid);
        partUserDetail.setAvatar(avatar);
        partUserDetail.setPhone(phone);
        partUserDetail.setAddress(address);
        partUserDetail.setAccount(account);

        return manageUserDetailService.saveByUserId(userid,partUserDetail);
    }

    @RequestMapping(value = "/UpdateByUserid")
    @ResponseBody
    public UserDetailEntity updateByUserid(HttpServletRequest request,
                                           @RequestParam(name = "avatar") MultipartFile frontAvatar,
                                           HttpServletResponse response){
        Long userid = Long.parseLong(request.getParameter("userid").trim());
        String avatar = manageUserDetailService.saveAvatar(frontAvatar);
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String account = request.getParameter("account").trim();

        return manageUserDetailService.updateByUserId(userid,avatar,phone,address,account);
    }

    @RequestMapping(value = "/QueryByUserid")
    @ResponseBody
    public UserDetailEntity queryByUserid(HttpServletRequest request, HttpServletResponse response){
        Long userid = Long.parseLong(request.getParameter("userid").trim());
        return manageUserDetailService.queryByUserId(userid);
    }

    @RequestMapping(value = "/MinusAccount")
    @ResponseBody
    public Boolean minusAccount(HttpServletRequest request, HttpServletResponse response){
        Long userid = Long.parseLong(request.getParameter("userid").trim());
        double toMinus = Double.parseDouble(request.getParameter("minus").trim());

        return manageUserDetailService.updateAccountMinusById(userid,toMinus);
    }
    @RequestMapping(value = "/PlusAccount")
    @ResponseBody
    public Boolean plusAccount(HttpServletRequest request, HttpServletResponse response){
        Long userid = Long.parseLong(request.getParameter("userid").trim());
        double toPlus = Double.parseDouble(request.getParameter("plus").trim());

        return manageUserDetailService.updateAccountPlusById(userid,toPlus);
    }

}
