package com.sjtusummerproject.cartmicroservice.Service;

import com.sjtusummerproject.cartmicroservice.DataModel.Domain.CartEntity;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CartService {
    public String saveInDetailPageByMultiInfo(UserEntity userEntity, TicketEntity ticketEntity, double price, String date,int number);
    public String numberPlusOneInCartById(Long id);
    public String numberMinusOneInCartById(Long id);
    public String numberEditInCartById(Long id, int number);
    public String deleteInCartById(Long id);
    public String deleteBatchInCartByIds(String ids);
    public CartEntity queryById(Long id);
    public List<CartEntity> queryByBatchId(String ids);
    public Page<CartEntity> findInCartByUserid(Long id, Pageable pageable);
}
