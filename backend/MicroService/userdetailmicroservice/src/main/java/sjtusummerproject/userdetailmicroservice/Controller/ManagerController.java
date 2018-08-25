package sjtusummerproject.userdetailmicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
@RequestMapping(value = "/Manager")
public class ManagerController {
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    ManageUserDetailService manageUserDetailService;

    @Value("${authservice.url}")
    private String url;
    @Value("${user.manager.service.url}")   //http://user-microservice:8080/Manager
    private String userManagerServiceUrl;

    @RequestMapping(value = "/Delete")
    public boolean delete(HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam(name = "id")Long toDeleteUserid){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
        if (result != 0) return false;
        /* delete in user service */
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id", toDeleteUserid);
        String deleteUserUrl = userManagerServiceUrl+"/Delete";
        restTemplate.postForObject(deleteUserUrl, multiValueMap, void.class);
        /* delete in user-detail service */
        manageUserDetailService.deleteUserDetail(toDeleteUserid);
        return true;
    }

    @RequestMapping(value = "/QueryById")
    UserEntity queryUserById(HttpServletRequest request,
                             HttpServletResponse response,
                             @RequestParam(name = "id")Long toQueryUserId){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
        if (result != 0) return null;
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id", toQueryUserId);
        String queryUserUrl = userManagerServiceUrl+"/QueryById";
        return restTemplate.postForObject(queryUserUrl, multiValueMap, UserEntity.class);
    }

    @RequestMapping(value = "/QueryBatch")
    Page<UserEntity> queryBatchUser(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @RequestParam(name = "pagenumber")int pagenumber){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
        if (result != 0) return null;
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("pagenumber", pagenumber);
        String queryBatchUserUrl = userManagerServiceUrl+"/QueryBatch";
        return restTemplate.postForObject(queryBatchUserUrl, multiValueMap, Page.class);
    }

    @RequestMapping(value = "/UpdateStatus")
    String updateUserStatus(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(name = "id")Long userid,
                            @RequestParam(name = "status")String status){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
        if (result != 0) return null;
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id", userid);
        multiValueMap.add("status", status);
        String updateUserStatusUrl = userManagerServiceUrl+"/UpdateStatus";
        return restTemplate.postForObject(updateUserStatusUrl, multiValueMap, String.class);
    }

    @RequestMapping(value = "/UpdatePassword")
    String updateUserPassword(HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestParam(name = "id")Long userid,
                              @RequestParam(name = "password")String password){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
        if (result != 0) return null;
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id", userid);
        multiValueMap.add("password", password);
        String updateUserPasswordUrl = userManagerServiceUrl+"/UpdatePassword";
        return restTemplate.postForObject(updateUserPasswordUrl, multiValueMap, String.class);
    }

    private UserEntity callAuthService(String token){
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("token", token);
        return restTemplate.postForObject(url, multiValueMap, UserEntity.class);
    }

    /*此时这个user的身份一定要是manager*/
    private int authUser(UserEntity userEntity){
        if (userEntity == null) return 1;
        else if (!userEntity.getAuthority().equals("Manager")) return 2;
        else if (userEntity.getStatus().equals("Frozen")) return 3;
        else return 0;
    }
}
