package com.sjtusummerproject.cartmicroservice;

import com.sjtusummerproject.cartmicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class CartmicroserviceApplicationTests {
    public TicketEntity getTicketEntity1(){
		TicketEntity t1 = new TicketEntity(1l,"1","1",null,null,"1","1","1","1","1","1",100l,100.0,200.0);
		return t1;

	}
    public TicketEntity getTicketEntity2(){
		TicketEntity t2 = new TicketEntity(2l,"2","2",null,null,"2","2","2","2","2","2",200l,200.0,200.0);
		return t2;
	}
	public UserEntity getUserEntity(){
    	UserEntity userEntity = new UserEntity();
    	userEntity.setId(1l);
    	return userEntity;
	}
	public Pageable getPagable(int pagenumber){
		return new PageRequest(pagenumber-1, 16, new Sort(Sort.Direction.ASC, "id"));
	}
	@Test
	public void contextLoads() {

	}

}
