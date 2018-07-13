package sjtusummerproject.ticketmicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;
import sjtusummerproject.ticketmicroservice.Service.FilterTicketService;
import sjtusummerproject.ticketmicroservice.Service.InputDataService;
import sjtusummerproject.ticketmicroservice.Service.ManageTicketService;
import sjtusummerproject.ticketmicroservice.Service.OpenFileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value="/Ticket")
public class TicketController {
    @Value("${ticket.page.size}")
    private int PageSize;
    @Value("${ticket.page.offset}")
    private int PageOffset;

    @Autowired
    ManageTicketService manageTicketService;

    @Autowired
    OpenFileService openFileService;

    @Autowired
    InputDataService inputDataService;

    @Autowired
    FilterTicketService filterTicketService;

    /* Get Pageable */
    Pageable CreatePageable(HttpServletRequest request){
        return new PageRequest(Integer.parseInt(request.getParameter("pagenumber"))-PageOffset, PageSize, new Sort(Sort.Direction.ASC, "id"));
    }

    /**************************************************************/
    /* page */

    @GetMapping(value="/QueryShowPage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketShowPage(HttpServletRequest request, HttpServletResponse response){
        System.out.println("page:"+request.getParameter("pagenumber"));
        return manageTicketService.QueryTicketPageOptionShow(CreatePageable(request));
    }

    @GetMapping(value="/QueryByTypePage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketByTypePage(HttpServletRequest request, HttpServletResponse response){
        String type = request.getParameter("type");
        return manageTicketService.QueryTicketPageOptionByType(type,CreatePageable(request));
    }

    @GetMapping(value="/QueryByCityPage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketByCityPage(HttpServletRequest request, HttpServletResponse response){
        String city = request.getParameter("city");
        return manageTicketService.QueryTicketPageOptionByCity(city,CreatePageable(request));
    }

    @GetMapping(value="/QueryByDateRangePage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketByDateRangePage(HttpServletRequest request, HttpServletResponse response){
        String firstDateString = request.getParameter("firstdate");
        String secondDateString = request.getParameter("seconddate");
        return manageTicketService.QueryTicketPageOptionByDateRange(firstDateString,secondDateString,CreatePageable(request));
    }

    @GetMapping(value="/QueryByPriceRangePage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketByPriceRangePage(HttpServletRequest request, HttpServletResponse response){
        double lowprice = Double.valueOf(request.getParameter("lowprice"));
        double highprice = Double.valueOf(request.getParameter("highprice"));
        return manageTicketService.QueryTicketPageOptionByPriceRange(lowprice,highprice,CreatePageable(request));
    }

    @GetMapping(value="/QueryByCityAndDateRangePage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketByCityAndDateRangePage(HttpServletRequest request, HttpServletResponse response){
        String city = request.getParameter("city");
        String firstDate = request.getParameter("firstdate");
        String secondDate = request.getParameter("seconddate");

        return manageTicketService.QueryTicketPageOptionByCityAndDateRange(city,firstDate,secondDate,CreatePageable(request));
    }

    @GetMapping(value="/QueryByCityAndPriceRangePage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketByCityAndPriceRangePage(HttpServletRequest request, HttpServletResponse response){
        String city = request.getParameter("city");
        double lowprice = Double.valueOf(request.getParameter("lowprice"));
        double highprice = Double.valueOf(request.getParameter("highprice"));

        return manageTicketService.QueryTicketPageOptionByCityAndPriceRange(city,lowprice,highprice,CreatePageable(request));
    }

    @GetMapping(value="/QueryByCityAndPriceRangeAndDateRangePage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketByCityAndPriceRangeAndDateRangePage(HttpServletRequest request, HttpServletResponse response){
        String city = request.getParameter("city");
        String firstDate = request.getParameter("firstdate");
        String secondDate = request.getParameter("seconddate");
        double lowprice = Double.valueOf(request.getParameter("lowprice"));
        double highprice = Double.valueOf(request.getParameter("highprice"));

        return manageTicketService.QueryTicketPageOptionByCityAndPriceRangeAndDateRange(city,lowprice,highprice,firstDate,secondDate,CreatePageable(request));
    }
    /**************************************************************/
    /* no page */
    @GetMapping(value="/QueryById")
    @ResponseBody
    public TicketEntity QueryTicketByID(HttpServletRequest request, HttpServletResponse response){
        Long TicketId = Long.parseLong(request.getParameter("id"));
        TicketEntity resTicket = manageTicketService.QueryTicketOptionById(TicketId);
        return resTicket;
    }

    @GetMapping(value="/QueryByExactDate")
    @ResponseBody
    public List<TicketEntity> QueryTicketByExactDate(HttpServletRequest request, HttpServletResponse response){
        String date = request.getParameter("date");
        List<TicketEntity> list = manageTicketService.QueryTicketOptionByExactDate(date);
        return list;
    }

    @GetMapping(value="/QueryByDateRange")
    @ResponseBody
    public List<TicketEntity> QueryTicketByDateRange(HttpServletRequest request, HttpServletResponse response){
        String firstDate = request.getParameter("firstdate");
        String secondDate = request.getParameter("seconddate");
        System.out.println(firstDate);
        System.out.println(secondDate);
        List<TicketEntity> list = manageTicketService.QueryTicketOptionByDateRange(firstDate,secondDate);
        return list;
    }

    @GetMapping(value="/QueryByCity")
    @ResponseBody
    public List<TicketEntity> QueryTicketByCity(HttpServletRequest request, HttpServletResponse response){
        String city = request.getParameter("city");

        List<TicketEntity> res = manageTicketService.QueryTicketOptionByCity(city);
        return res;
    }

    @GetMapping(value="/QueryByType")
    @ResponseBody
    public List<TicketEntity> QueryTicketByType(HttpServletRequest request, HttpServletResponse response){
        String type = request.getParameter("type");

        List<TicketEntity> res = manageTicketService.QueryTicketOptionByType(type);
        return res;
    }

    @GetMapping(value="/QueryByPriceRange")
    @ResponseBody
    public List<TicketEntity> QueryTicketByPriceRange(HttpServletRequest request, HttpServletResponse response){
        double lowprice = Double.valueOf(request.getParameter("lowprice"));
        double highprice = Double.valueOf(request.getParameter("highprice"));

        List<TicketEntity> res = manageTicketService.QueryTicketOptionByPriceRange(lowprice,highprice);
        return res;
    }

    @GetMapping(value="/QueryByCityAndDateRange")
    @ResponseBody
    public List<TicketEntity> QueryTicketByCityAndDateRange(HttpServletRequest request, HttpServletResponse response){
        String city = request.getParameter("city");
        String firstDate = request.getParameter("firstdate");
        String secondDate = request.getParameter("seconddate");

        List<TicketEntity> firstFilter = manageTicketService.QueryTicketOptionByCity(city);
        if(firstFilter==null)
            return null;
        List<TicketEntity> secondFilter = filterTicketService.FilterTicketOptionByDateRange(firstFilter,firstDate,secondDate);
        return secondFilter;
    }

    @GetMapping(value="/QueryByCityAndPriceRange")
    @ResponseBody
    public List<TicketEntity> QueryTicketByCityAndPriceRange(HttpServletRequest request, HttpServletResponse response){
        String city = request.getParameter("city");
        double lowprice = Double.valueOf(request.getParameter("lowprice"));
        double highprice = Double.valueOf(request.getParameter("highprice"));

        List<TicketEntity> firstFilter = manageTicketService.QueryTicketOptionByCity(city);
        if(firstFilter==null)
            return null;
        List<TicketEntity> secondFilter = filterTicketService.FilterTicketOptionByPriceRange(firstFilter,lowprice,highprice);

        return secondFilter;
    }

    @GetMapping(value="/QueryByPriceRangeAndDateRange")
    @ResponseBody
    public List<TicketEntity> QueryTicketByPriceRangeAndDateRange(HttpServletRequest request, HttpServletResponse response){
        double lowprice = Double.valueOf(request.getParameter("lowprice"));
        double highprice = Double.valueOf(request.getParameter("highprice"));

        String firstDate = request.getParameter("firstdate");
        String secondDate = request.getParameter("seconddate");

        List<TicketEntity> firstFilter = manageTicketService.QueryTicketOptionByPriceRange(lowprice,highprice);
        if(firstFilter == null)
            return null;
        List<TicketEntity> secondFilter = filterTicketService.FilterTicketOptionByDateRange(firstFilter,firstDate,secondDate);
        return secondFilter;
    }

    @GetMapping(value="/QueryByCityAndPriceRangeAndDateRange")
    @ResponseBody
    public List<TicketEntity> QueryTicketByCityAndPriceRangeAndDateRange(HttpServletRequest request, HttpServletResponse response){
        String city = request.getParameter("city");

        double lowprice = Double.valueOf(request.getParameter("lowprice"));
        double highprice = Double.valueOf(request.getParameter("highprice"));

        String firstDate = request.getParameter("firstdate");
        String secondDate = request.getParameter("seconddate");

        List<TicketEntity> firstFilter = manageTicketService.QueryTicketOptionByCity(city);
        if(firstFilter == null)
            return null;
        List<TicketEntity> secondFilter = filterTicketService.FilterTicketOptionByPriceRange(firstFilter,lowprice,highprice);
        if(secondFilter == null)
            return null;
        List<TicketEntity> thirdFilter = filterTicketService.FilterTicketOptionByDateRange(firstFilter,firstDate,secondDate);
        return thirdFilter;
    }

    @GetMapping(value="/InputData")
    @ResponseBody
    public String InputData(){
        List<String> list = openFileService.getfilestring();
        inputDataService.inputdata(list);
        return "ok";
    }

    @PostMapping(value = "/FilterByExactDate")
    @ResponseBody
    public List<TicketEntity> FilterTicketByExactDate(HttpServletRequest request, HttpServletResponse response){
        List<TicketEntity> toFilterEntity = inputDataService.getfilterlist(request.getParameter("filterlist"));
        System.out.println(request.getParameter("date"));
        if(toFilterEntity==null)
            return null;
        List<TicketEntity> res = filterTicketService.FilterTicketOptionByExactDate(toFilterEntity,request.getParameter("date"));
        return res;
    }

}
