package sjtusummerproject.ticketmicroservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import sjtusummerproject.ticketmicroservice.DataModel.Dao.TicketRepository;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;
import sjtusummerproject.ticketmicroservice.Service.ManageTicketService;
import sjtusummerproject.ticketmicroservice.Service.ServiceImpl.ManageTicketServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@SpringBootTest
public class TicketmicroserviceApplicationTests {
	@Autowired
	public ManageTicketService manageTicketService;

	@Autowired
	public TicketRepository ticketRepository;

	public List<TicketEntity> generateTickets(){
		List<TicketEntity> result = new ArrayList<>();
		TicketEntity t1 = new TicketEntity("1","1",manageTicketService.ChangeStringToDate("2018-01-01"),manageTicketService.ChangeStringToDate("2018-05-25"),"1","1","1","1","1","1",100l,100.0,200.0);
		TicketEntity t2 = new TicketEntity("2","2",manageTicketService.ChangeStringToDate("2017-01-01"),manageTicketService.ChangeStringToDate("2017-09-01"),"2","2","2","2","2","2",200l,200.0,300.0);
		t1.setId(1l);t2.setId(2l);
		result.add(t1);result.add(t2);
		return result;
	}

	public Pageable getPagable(int pagenumber){
		return new PageRequest(pagenumber-1, 16, new Sort(Sort.Direction.ASC, "id"));
	}

	@Test
	public void contextLoads() {
	}
}
