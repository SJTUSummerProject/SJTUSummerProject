package sjtusummerproject.reportmicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sjtusummerproject.reportmicroservice.DataModel.Domain.DailyReportEntity;
import sjtusummerproject.reportmicroservice.DataModel.Domain.MonthlyReportEntity;
import sjtusummerproject.reportmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.reportmicroservice.DataModel.Domain.WeeklyReportEntity;
import sjtusummerproject.reportmicroservice.Service.ReportService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

@RestController
@RequestMapping(value = "/Manager")
public class ReportController {
    RestTemplate restTemplate = new RestTemplate();
    @Value("${authservice.url}")
    private String url;
    @Value("${ticket.page.size}")
    private int PageSize;
    @Value("${ticket.page.offset}")
    private int PageOffset;

    /* Get Pageable */
    Pageable createPageable(HttpServletRequest request){
        return new PageRequest(Integer.parseInt(request.getParameter("pagenumber"))-PageOffset, PageSize, new Sort(Sort.Direction.ASC, "id"));
    }

    @Autowired
    ReportService reportService;

    @RequestMapping(value = "/DailyQueryByTicketidAndDate")
    public DailyReportEntity queryDailyByTicketidAndDate(HttpServletRequest request,
                                                         HttpServletResponse response,
                                                         @RequestParam(name = "date")String date,
                                                         @RequestParam(name = "ticketid")Long ticketid
                                                         ) throws ParseException {
        if(identifyAuth(request,response)!=2) return null;
        return reportService.queryDailyByTicketidAndDate(ticketid,date);
    }

    //page!
    @RequestMapping(value = "/DailyQueryByCityAndDate")
    public Page<DailyReportEntity> queryDailyByCityAndDate(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               @RequestParam(name = "date")String date,
                                                               @RequestParam(name = "city")String city
    ) throws ParseException {
        if(identifyAuth(request,response)!=2) return null;
        return reportService.queryDailyByCityAndDate(city,date,createPageable(request));
    }

    @RequestMapping(value = "/WeeklyQueryByTicketidAndWeek")
    public WeeklyReportEntity queryWeeklyByTicketidAndWeek(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           @RequestParam(name = "year")int year,
                                                           @RequestParam(name = "month")int month,
                                                           @RequestParam(name = "week")int week,
                                                           @RequestParam(name = "ticketid")Long ticketid
    ) throws ParseException {
        if(identifyAuth(request,response)!=2) return null;
        return reportService.queryWeeklyByTicketidAndWeek(ticketid, year, month, week);
    }

    @RequestMapping(value = "/WeeklyQueryByCityAndWeek")
    public Page<WeeklyReportEntity> queryWeeklyByCityAndWeek(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           @RequestParam(name = "year")int year,
                                                           @RequestParam(name = "month")int month,
                                                           @RequestParam(name = "week")int week,
                                                           @RequestParam(name = "city")String city
    ) throws ParseException {
        if(identifyAuth(request,response)!=2) return null;
        return reportService.queryWeeklyByCityAndWeek(city,year,month,week,createPageable(request));
    }

    @RequestMapping(value = "/MonthlyQueryByTicketidAndWeek")
    public MonthlyReportEntity queryMonthlyByTicketidAndWeek(HttpServletRequest request,
                                                              HttpServletResponse response,
                                                              @RequestParam(name = "year")int year,
                                                              @RequestParam(name = "month")int month,
                                                              @RequestParam(name = "ticketid")Long ticketid
    ) throws ParseException {
        if(identifyAuth(request,response)!=2) return null;
        return reportService.queryMonthlyByTicketidAndMonth(ticketid, year, month);
    }

    @RequestMapping(value = "/MonthlyQueryByCityAndWeek")
    public Page<MonthlyReportEntity> queryMonthlyByCityAndMonth(HttpServletRequest request,
                                                             HttpServletResponse response,
                                                             @RequestParam(name = "year")int year,
                                                             @RequestParam(name = "month")int month,
                                                             @RequestParam(name = "city")String city
    ) throws ParseException {
        if(identifyAuth(request,response)!=2) return null;
        return reportService.queryMonthlyByCityAndMonth(city,year,month,createPageable(request));
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
