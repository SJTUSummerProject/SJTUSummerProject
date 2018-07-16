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
import sjtusummerproject.ticketmicroservice.Service.ManageTicketService;

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



}
