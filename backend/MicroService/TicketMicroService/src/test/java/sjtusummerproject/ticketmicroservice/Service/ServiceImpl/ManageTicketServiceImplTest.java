package sjtusummerproject.ticketmicroservice.Service.ServiceImpl;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import sjtusummerproject.ticketmicroservice.DataModel.Dao.TicketRepository;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;
import sjtusummerproject.ticketmicroservice.Service.ManageTicketService;
import sjtusummerproject.ticketmicroservice.TicketmicroserviceApplicationTests;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ManageTicketServiceImplTest extends TicketmicroserviceApplicationTests {

    @Test
    public void aqueryTicketPageOptionShow() {
        List<TicketEntity> tickets = generateTickets();
        for (TicketEntity t: tickets){
            ticketRepository.save(t);
        }
        Page<TicketEntity> ticketEntities = manageTicketService.QueryTicketPageOptionShow(getPagable(1));
        assertEquals(2, ticketEntities.getNumberOfElements());
        assertEquals("1",ticketEntities.getContent().get(0).getCity());
    }

    @Test
    public void queryTicketPageOptionByTitle(){
        Page<TicketEntity> ticketEntities = manageTicketService.QueryTicketPageOptionByTitle("",getPagable(1));
        assertEquals(2, ticketEntities.getNumberOfElements());
    }
    @Test
    public void queryTicketPageOptionByTitleSecond(){
        Page<TicketEntity> ticketEntities = manageTicketService.QueryTicketPageOptionByTitle("1",getPagable(1));
        assertEquals(1, ticketEntities.getNumberOfElements());
    }

    @Test
    public void queryTicketPageOptionByType() {
        Page<TicketEntity> ticketEntities = manageTicketService.QueryTicketPageOptionByType("1",getPagable(1));
        assertEquals(1, ticketEntities.getNumberOfElements());
    }

    @Test
    public void queryTicketPageOptionByCity() {
        Page<TicketEntity> ticketEntities = manageTicketService.QueryTicketPageOptionByCity("1",getPagable(1));
        assertEquals(1, ticketEntities.getNumberOfElements());
    }

    @Test
    public void queryTicketPageOptionByCityAndType() {
        Page<TicketEntity> ticketEntities = manageTicketService.QueryTicketPageOptionByCityAndType("1","1",getPagable(1));
        assertEquals(1, ticketEntities.getNumberOfElements());
    }

    @Test
    public void queryTicketPageOptionByPriceRange() {
        Page<TicketEntity> ticketEntities = manageTicketService.QueryTicketPageOptionByPriceRange(0,500,getPagable(1));
        assertEquals(2, ticketEntities.getNumberOfElements());
    }
    @Test
    public void queryTicketPageOptionByDateRange(){
        Page<TicketEntity> ticketEntities = manageTicketService.QueryTicketPageOptionByDateRange("2016-10-25","2019-03-04",getPagable(1));
        assertEquals(2, ticketEntities.getNumberOfElements());
    }

    @Test
    public void queryTicketOptionById() {
        TicketEntity ticketEntity = manageTicketService.QueryTicketOptionById(1l);
        assertEquals("1", ticketEntity.getCity());
    }

    @Test
    public void queryTicketOptionByBatchIds() {
        List<TicketEntity> ticketEntities = manageTicketService.QueryTicketOptionByBatchIds("[1,2]");
        assertEquals("1",ticketEntities.get(0).getCity());
        assertEquals("2",ticketEntities.get(1).getCity());

    }

    @Test
    public void updateStockMinusByIdSuccess() {
        boolean result = manageTicketService.updateStockMinusById(1l,100l);
        assertEquals(true, result);
        assertEquals(0l, (long)ticketRepository.findById(1l).getStock());
    }
    public void bupdateStockMinusByIdfail() {
        boolean result = manageTicketService.updateStockMinusById(1l,500l);
        assertEquals(false, result);
        assertEquals(100l, (long)ticketRepository.findById(1l).getStock());
    }

    @Test
    public void updateStockPlusById(){
        boolean result = manageTicketService.updateStockPlusById(2l,100l);
        assertEquals(300l, (long)ticketRepository.findById(2l).getStock());
    }
    @Test
    public void changeStringToDateSuccess() {
        Date result = manageTicketService.ChangeStringToDate("1999-05-25");
        assertEquals(4,result.getMonth());
    }
    @Test
    public void changeStringToDateFail(){
        Date result = manageTicketService.ChangeStringToDate("1111111");
        assertNull(result);
    }
}