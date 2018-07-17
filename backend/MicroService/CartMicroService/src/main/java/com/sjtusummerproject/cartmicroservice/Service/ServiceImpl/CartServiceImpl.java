package com.sjtusummerproject.cartmicroservice.Service.ServiceImpl;


import com.sjtusummerproject.cartmicroservice.DataModel.Dao.CartPageRepository;
import com.sjtusummerproject.cartmicroservice.DataModel.Dao.CartRepository;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.CartEntity;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.cartmicroservice.Service.CartService;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartPageRepository cartPageRepository;

    @Override
    public String saveInDetailPageByMultiInfo(UserEntity userEntity, TicketEntity ticketEntity, double price, String date, int number) {

        CartEntity cartEntity = new CartEntity();

        cartEntity.setUserId(userEntity.getId());
        cartEntity.setTicketId(ticketEntity.getId());
        cartEntity.setPrice(price);
        cartEntity.setImage(ticketEntity.getImage());
        cartEntity.setNumber(number);
        cartEntity.setTitle(ticketEntity.getTitle());
        cartEntity.setDate(date);
        cartEntity.setVenue(ticketEntity.getVenue());

        CartEntity isSavedIndb = cartRepository.findByUserIdAndTicketIdAndDateAndAndPrice(cartEntity.getUserId(),cartEntity.getTicketId(),cartEntity.getDate(),cartEntity.getPrice());
        if(isSavedIndb != null){
            /*在已有的entity数量的基础上加上新的数量*/
            isSavedIndb.setNumber(isSavedIndb.getNumber()+cartEntity.getNumber());
            cartRepository.save(isSavedIndb);
        }
        else{
            cartEntity.setId(0L);
            cartRepository.save(cartEntity);
        }
        return "ok";
    }

    @Override
    public String numberPlusOneInCartById(Long id) {
        CartEntity cartEntity = cartRepository.findById(id);
        cartEntity.setNumber(cartEntity.getNumber()+1);
        cartRepository.save(cartEntity);
        return "ok";
    }

    @Override
    public String numberMinusOneInCartById(Long id) {
        CartEntity cartEntity = cartRepository.findById(id);

        if(cartEntity.getNumber() == 0){
            return "已经是0 不能再减了";
        }

        cartEntity.setNumber(cartEntity.getNumber()-1);
        cartRepository.save(cartEntity);
        return "ok";
    }

    @Override
    public String numberEditInCartById(Long id, int number) {
        CartEntity cartEntity = cartRepository.findById(id);

        if(number < 0)
            return "number是负数 不能save 成负数";

        cartEntity.setNumber(number);
        cartRepository.save(cartEntity);
        return "ok";
    }

    @Override
    public String deleteInCartById(Long id) {
        cartRepository.deleteById(id);
        return "ok";
    }

    @Override
    public String deleteBatchInCartByIds(String ids) {
        String[] splitIds = ids.trim().replace("[","").replace("]","").split(",");

        for(String eachId : splitIds){
            cartRepository.deleteById(Long.parseLong(eachId));
        }
        return "ok";
    }

    @Cacheable(value = "10m" ,key = "#id + ':'+#pageable.getPageNumber()")
    @Override
    public Page<CartEntity> findInCartByUserid(Long id, Pageable pageable) {
        return cartPageRepository.findAllByUserId(id,pageable);
    }
}
