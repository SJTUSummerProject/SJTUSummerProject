package com.sjtusummerproject.cartmicroservice.Service;

import com.sjtusummerproject.cartmicroservice.DataModel.Domain.CartEntity;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CartService {
    public CartEntity saveInDetailPageByMultiInfo(UserEntity userEntity, TicketEntity ticketEntity, double price, String date,Long number);
    public CartEntity numberEditInCartById(Long id, Long number);
    public String deleteBatchInCartByIds(String ids);
    public CartEntity queryById(Long id);
    public List<CartEntity> queryByBatchId(String ids);
    public Page<CartEntity> findInCartByUserid(Long id, Pageable pageable);
}
