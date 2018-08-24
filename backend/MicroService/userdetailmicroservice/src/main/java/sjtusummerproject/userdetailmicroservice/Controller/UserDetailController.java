package sjtusummerproject.userdetailmicroservice.Controller;

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
import sjtusummerproject.userdetailmicroservice.DataModel.Domain.UserDetailEntity;
import sjtusummerproject.userdetailmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.userdetailmicroservice.Service.ManageUserDetailService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/UserDetail")
public class UserDetailController {
    @Autowired
    ManageUserDetailService manageUserDetailService;

    RestTemplate restTemplate = new RestTemplate();

    @Value("${authservice.url}")
    private String url;
    @Value("${userservice.url}")
    private String userUrl;

    @RequestMapping(value = "/InitialSave")
    public String initialSave(HttpServletRequest request, HttpServletResponse response){
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        if (manageUserDetailService.saveByUserId(userEntity) == null) return null;
        return "success";
    }

    @RequestMapping(value = "/UpdateByUserid")
    @ResponseBody
    public UserDetailEntity updateByUserid(HttpServletRequest request,
                                           @RequestParam(name = "avatar", required = false) MultipartFile frontAvatar,
                                           @RequestParam(name = "account", required = false) Double account,
                                           HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
        if (result != 0) return null;
        Long userid = userEntity.getId();
        String avatar = manageUserDetailService.saveAvatar(frontAvatar);
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String nickName = request.getParameter("nickname");

        return manageUserDetailService.updateByUserId(userid,avatar,phone,address,account,nickName);
    }

    @RequestMapping(value = "/UpdateOldPassword")
    public boolean modifhOldPassword(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
        if (result != 0) return false;
        Long userid = userEntity.getId();
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id", userid);
        multiValueMap.add("oldpassword", request.getParameter("oldpassword"));
        multiValueMap.add("newpassword", request.getParameter("newpassword"));
        return restTemplate.postForObject(userUrl, multiValueMap, boolean.class);
    }

    @RequestMapping(value = "/QueryByUserid")
    @ResponseBody
    public UserDetailEntity queryByUserid(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
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
