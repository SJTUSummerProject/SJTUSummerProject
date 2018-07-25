package com.sjtusummerproject.cartmicroservice.Service.ServiceImpl;

import com.sjtusummerproject.cartmicroservice.CartmicroserviceApplicationTests;
import com.sjtusummerproject.cartmicroservice.DataModel.Dao.CartRepository;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.CartEntity;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.cartmicroservice.Service.CartService;
import com.sjtusummerproject.cartmicroservice.Service.TicketService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CartServiceImplTest extends CartmicroserviceApplicationTests {

    @Autowired
    CartService cartService;
    @Autowired
    CartRepository cartRepository;
    @Test
    public void asaveInDetailPageByMultiInfoSingle1() {
        TicketEntity ticketEntity = getTicketEntity1();
        UserEntity userEntity = getUserEntity();
        CartEntity result = cartService.saveInDetailPageByMultiInfo(userEntity,ticketEntity,100.0,"2017-05-25",2l);
        assertEquals(2l, (long)result.getNumber());

    }

    @Test
    public void asaveInDetailPageByMultiInfoSingle2() {
        TicketEntity ticketEntity = getTicketEntity2();
        UserEntity userEntity = getUserEntity();
        CartEntity result = cartService.saveInDetailPageByMultiInfo(userEntity,ticketEntity,100.0,"2017-05-25",3l);
        assertEquals(3l, (long)result.getNumber());


    }
    @Test
    public void bsaveInDetailPageByMultiInfomulti() {
        TicketEntity ticketEntity = getTicketEntity1();
        UserEntity userEntity = getUserEntity();
        CartEntity result = cartService.saveInDetailPageByMultiInfo(userEntity,ticketEntity,100.0,"2017-05-25",2l);
        assertEquals(4l, (long)result.getNumber());

    }


    @Test
    public void numberEditInCartByIdSuccess() {
        CartEntity result = cartService.numberEditInCartById(1l, 5l);
        assertEquals(5l, (long)result.getNumber());
    }

    @Test
    public void numberEditInCartByIdFail() {
        CartEntity result = cartService.numberEditInCartById(2l, -1l);
        assertNull(result);
        assertEquals(3l, (long)cartRepository.findById(2l).getNumber());
    }

    @Test
    public void zdeleteBatchInCartByIds() {
        cartService.deleteBatchInCartByIds("[1,2]");
        assertNull(cartRepository.findById(1l));
        assertNull(cartRepository.findById(2l));
    }

    @Test
    public void yfindInCartByUserid() {
        Page<CartEntity> cartEntities = cartService.findInCartByUserid(1l,getPagable(1));
        assertEquals(2l, (long)cartEntities.getTotalElements());
    }

    @Test
    public void queryById() {
        CartEntity resulr = cartService.queryById(2l);
        assertEquals(3l, (long)resulr.getNumber());
    }

    @Test
    public void yqueryByBatchId() {
        List<CartEntity> cartEntities = cartService.queryByBatchId("[1,2]");
        assertEquals(3l,(long)cartEntities.get(1).getNumber());
        assertEquals(5l,(long)cartEntities.get(0).getNumber());
    }
}