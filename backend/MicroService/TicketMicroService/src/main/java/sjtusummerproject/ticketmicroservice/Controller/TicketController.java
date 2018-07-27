package sjtusummerproject.ticketmicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;
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
    /* Get Pageable */
    Pageable CreatePageable(HttpServletRequest request){
        return new PageRequest(Integer.parseInt(request.getParameter("pagenumber"))-PageOffset, PageSize, new Sort(Sort.Direction.ASC, "id"));
    }

    /**************************************************************/
    /* page */

    @GetMapping(value="/QueryShowPage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketShowPage(HttpServletRequest request, HttpServletResponse response){
        //System.out.println("page:"+request.getParameter("pagenumber"));
        return manageTicketService.QueryTicketPageOptionShow(CreatePageable(request));
    }
    @RequestMapping(value = "/QueryByTitle")
    public Page<TicketEntity> QueryTicketByTitle(HttpServletRequest request, HttpServletResponse response){
        String title = request.getParameter("title");
        return manageTicketService.QueryTicketPageOptionByTitle(title, CreatePageable(request));
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


    @GetMapping(value="/QueryByCityAndTypePage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketByCityAndTypePage(HttpServletRequest request, HttpServletResponse response){
        String city = request.getParameter("city");
        String type = request.getParameter("type");
        String title = request.getParameter("title");
        if (city == null || city.equals("all")) city = "%";
        if (type == null || type.equals("all")) type = "%";
        if (title == null || title.equals("all")) title = "%";
        else title = "%"+title.trim()+"%";
        return manageTicketService.QueryTicketPageOptionByCityAndTypeAndTitle(city, type, title, CreatePageable(request));
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
    /** add type **/
    @RequestMapping(value="/QueryByTypeAndCityPage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketByTypeAndCityPage(HttpServletRequest request, HttpServletResponse response){
        String city = request.getParameter("city");
        String type = request.getParameter("type");
        return manageTicketService.QueryTicketPageOptionByTypeAndCity(type,city,CreatePageable(request));
    }

    @RequestMapping(value="/QueryByTypeAndDateRangePage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketByTypeAndDateRangePage(HttpServletRequest request, HttpServletResponse response){
        String firstDateString = request.getParameter("firstdate");
        String secondDateString = request.getParameter("seconddate");
        String type = request.getParameter("type");
        return manageTicketService.QueryTicketPageOptionByTypeAndDateRange(type,firstDateString,secondDateString,CreatePageable(request));
    }

    @RequestMapping(value="/QueryByTypeAndPriceRangePage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketByTypeAndPriceRangePage(HttpServletRequest request, HttpServletResponse response){
        double lowprice = Double.valueOf(request.getParameter("lowprice"));
        double highprice = Double.valueOf(request.getParameter("highprice"));
        String type = request.getParameter("type");
        return manageTicketService.QueryTicketPageOptionByTypeAndPriceRange(type,lowprice,highprice,CreatePageable(request));
    }

    @RequestMapping(value="/QueryByTypeAndCityAndDateRangePage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketByTypeCityAndDateRangePage(HttpServletRequest request, HttpServletResponse response){
        String city = request.getParameter("city");
        String type = request.getParameter("type");
        String firstDate = request.getParameter("firstdate");
        String secondDate = request.getParameter("seconddate");
        return manageTicketService.QueryTicketPageOptionByTypeAndCityAndDateRange(type,city,firstDate,secondDate,CreatePageable(request));
    }

    @RequestMapping(value="/QueryByTypeAndCityAndPriceRangePage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketByTypeAndCityAndPriceRangePage(HttpServletRequest request, HttpServletResponse response){
        String city = request.getParameter("city");
        String type = request.getParameter("type");
        double lowprice = Double.valueOf(request.getParameter("lowprice"));
        double highprice = Double.valueOf(request.getParameter("highprice"));

        return manageTicketService.QueryTicketPageOptionByTypeAndCityAndPriceRange(type,city,lowprice,highprice,CreatePageable(request));
    }

    @GetMapping(value="/QueryByTypeAndCityAndPriceRangeAndDateRangePage")
    @ResponseBody
    public Page<TicketEntity> QueryTicketByTypeCityAndPriceRangeAndDateRangePage(HttpServletRequest request, HttpServletResponse response){
        String city = request.getParameter("city");
        String type = request.getParameter("type");
        String firstDate = request.getParameter("firstdate");
        String secondDate = request.getParameter("seconddate");
        double lowprice = Double.valueOf(request.getParameter("lowprice"));
        double highprice = Double.valueOf(request.getParameter("highprice"));

        return manageTicketService.QueryTicketPageOptionByTypeAndCityAndPriceRangeAndDateRange(type,city,lowprice,highprice,firstDate,secondDate,CreatePageable((request)));
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
    @GetMapping(value="/QueryNoCacheById")
    @ResponseBody
    public TicketEntity QueryTicketNoCacheByID(HttpServletRequest request, HttpServletResponse response){
        Long TicketId = Long.parseLong(request.getParameter("id"));
        TicketEntity resTicket = manageTicketService.QueryTicketById(TicketId);
        return resTicket;
    }


    @RequestMapping(value = "/QueryByBatchIds")
    @ResponseBody
    public List<TicketEntity> QueryTicketByIds(HttpServletRequest request, HttpServletResponse response){
        String ids = request.getParameter("batchid");
        return manageTicketService.QueryTicketOptionByBatchIds(ids);
    }

    @RequestMapping(value = "/MinusStock")
    @ResponseBody
    public Boolean minusStock(HttpServletRequest request, HttpServletResponse response){
        Long ticketid = Long.parseLong(request.getParameter("ticketid").trim());
        Long toMinus = Long.parseLong(request.getParameter("minus").trim());

        return manageTicketService.updateStockMinusById(ticketid,toMinus);
    }

    @RequestMapping(value = "/PlusStock")
    @ResponseBody
    public Boolean plusStock(HttpServletRequest request, HttpServletResponse response){
        Long ticketid = Long.parseLong(request.getParameter("ticketid").trim());
        Long toPlus = Long.parseLong(request.getParameter("plus").trim());

        return manageTicketService.updateStockPlusById(ticketid,toPlus);
    }
}
