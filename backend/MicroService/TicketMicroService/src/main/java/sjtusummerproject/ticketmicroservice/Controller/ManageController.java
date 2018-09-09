package sjtusummerproject.ticketmicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.ticketmicroservice.Service.ManageTicketService;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@RestController("/Manager")
@RequestMapping(value = "/Manager")
public class ManageController {

    RestTemplate restTemplate = new RestTemplate();
    @Value("${authservice.url}")
    private String url;

    @Autowired
    ManageTicketService manageTicketService;

    @RequestMapping(value = "/Add")
    public TicketEntity add(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(name = "type")String type,
                            @RequestParam(name = "startDate")String startDateString,
                            @RequestParam(name = "endDate")String endDateString,
                            @RequestParam(name = "time")String time,// time : 20:00
                            @RequestParam(name = "city")String city,
                            @RequestParam(name = "venue")String venue,
                            @RequestParam(name = "title")String title,
                            @RequestParam(name = "image")MultipartFile image,
                            @RequestParam(name = "intro")String intro,
                            @RequestParam(name = "stock")Long stock,
                            @RequestParam(name = "lowprice")Double lowprice,
                            @RequestParam(name = "highprice")Double highprice
                            ){
        if(identifyAuth(request,response)!=2) return null;
        return manageTicketService.add(type,startDateString,endDateString,time,city,venue,title,image,intro,stock,lowprice,highprice);
    }

    @RequestMapping(value = "/Delete")
    @Transactional
    public String deleteOne(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam(name = "ticketids")String ticketidsString){
        if(identifyAuth(request,response)!=2) return null;
        else return manageTicketService.delete(ticketidsString);
    }

    @RequestMapping(value = "/Update")
    public TicketEntity update(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(name = "ticketid")Long ticketid,
                            @RequestParam(name = "type",required = false)String type,
                            @RequestParam(name = "startDate",required = false)String startDateString,
                            @RequestParam(name = "endDate",required = false)String endDateString,
                            @RequestParam(name = "time",required = false)String time,// time : 20:00
                            @RequestParam(name = "city",required = false)String city,
                            @RequestParam(name = "venue",required = false)String venue,
                            @RequestParam(name = "title",required = false)String title,
                            @RequestParam(name = "image",required = false)MultipartFile image,
                            @RequestParam(name = "intro",required = false)String intro,
                            @RequestParam(name = "stock",required = false)Long stock,
                            @RequestParam(name = "lowprice",required = false)Double lowprice,
                            @RequestParam(name = "highprice",required = false)Double highprice
    ){
        if(identifyAuth(request,response)!=2) return null;
        return manageTicketService.update(ticketid,type,startDateString,endDateString,time,city,venue,title,image,intro,stock,lowprice,highprice);
    }

    private int identifyAuth(HttpServletRequest request,
                         HttpServletResponse response
                         ){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
        return result;
    }

    private UserEntity callAuthService(String token){
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("token", token);
        return restTemplate.postForObject(url, multiValueMap, UserEntity.class);
    }

    /*此时这个user的身份一定要是manager*/
    private int authUser(UserEntity userEntity){
        if (userEntity == null) return 1;
        else if (userEntity.getAuthority().equals("Manager")) return 2;
        else if (userEntity.getStatus().equals("Frozen")) return 3;
        else return 0;
    }
}
