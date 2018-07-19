package sjtusummerproject.usermicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserDetailEntity;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserEntity;
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

    RestTemplate restTemplate = new RestTemplate();

    @Value("${authservice.url}")
    private String url;

    @RequestMapping(value = "/UpdateByUserid")
    @ResponseBody
    public UserDetailEntity updateByUserid(HttpServletRequest request,
                                           @RequestParam(name = "avatar", required = false) MultipartFile frontAvatar,
                                           HttpServletResponse response){
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        int result = authUser(userEntity);
        response.addIntHeader("errorNum", result);
        if (result != 0) return null;
        Long userid = userEntity.getId();
        String avatar = manageUserDetailService.saveAvatar(frontAvatar);
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String account = request.getParameter("account").trim();

        return manageUserDetailService.updateByUserId(userid,avatar,phone,address,account);
    }

    @RequestMapping(value = "/QueryByUserid")
    @ResponseBody
    public UserDetailEntity queryByUserid(HttpServletRequest request, HttpServletResponse response){
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        int result = authUser(userEntity);
        response.addIntHeader("errorNum", result);
        if (result != 0) return null;
        Long userid = userEntity.getId();
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
    private UserEntity callAuthService(String token){
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("token", token);
        return restTemplate.postForObject(url, multiValueMap, UserEntity.class);
    }

    private int authUser(UserEntity userEntity){

        if (userEntity == null) return 1;
        else if (!userEntity.getAuthority().equals("Customer")) return 2;
        else if (userEntity.getStatus().equals("Frozen")) return 3;
        else return 0;
    }

}
