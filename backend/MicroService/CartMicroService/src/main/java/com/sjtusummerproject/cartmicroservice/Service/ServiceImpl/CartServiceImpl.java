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

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartPageRepository cartPageRepository;

    @Override
    public CartEntity saveInDetailPageByMultiInfo(UserEntity userEntity, TicketEntity ticketEntity, double price, String date, Long number) {

        CartEntity cartEntity = new CartEntity();

        cartEntity.setUserId(userEntity.getId());
        cartEntity.setTicketId(ticketEntity.getId());
        cartEntity.setPrice(price);
        cartEntity.setImage(ticketEntity.getImage());
        cartEntity.setNumber(number);
        cartEntity.setTitle(ticketEntity.getTitle());
        cartEntity.setDate(date);
        cartEntity.setVenue(ticketEntity.getVenue());
        cartEntity.setCity(ticketEntity.getCity());

        CartEntity isSavedIndb = cartRepository.findByUserIdAndTicketIdAndDateAndAndPrice(cartEntity.getUserId(),cartEntity.getTicketId(),cartEntity.getDate(),cartEntity.getPrice());
        if(isSavedIndb != null){
            /*在已有的entity数量的基础上加上新的数量*/
            isSavedIndb.setNumber(isSavedIndb.getNumber()+cartEntity.getNumber());
            return cartRepository.save(isSavedIndb);
        }
        else{
            cartEntity.setId(0L);
            return cartRepository.save(cartEntity);
        }
    }


    @Override
    public CartEntity numberEditInCartById(Long id, Long number) {
        if(number < 0) return null;
        CartEntity cartEntity = cartRepository.findById(id);
        cartEntity.setNumber(number);
        return cartRepository.save(cartEntity);
    }

    @Override
    @Transactional
    public String deleteBatchInCartByIds(String ids) {
        String[] splitIds = ids.trim().replace("[","").replace("]","").split(",");

        for(String eachId : splitIds){
            cartRepository.deleteById(Long.parseLong(eachId.trim()));
        }
        return "ok";
    }

    @Override
    public Page<CartEntity> findInCartByUserid(Long id, Pageable pageable) {
        long startTime = System.currentTimeMillis();
        Page<CartEntity> cartEntities = cartPageRepository.findAllByUserId(id,pageable);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        return  cartEntities;
    }

    @Override
    public CartEntity queryById(Long id) {
        return cartRepository.findById(id);
    }

    /* 假设ids的格式如下
    *  [1,2,3,4,5,6]
    * */
    @Override
    public List<CartEntity> queryByBatchId(String ids) {
        if (ids == null) return null;
        String[] idSplit = ids.trim().replace("[","").replace("]","").split(",");
        List<CartEntity> res = new LinkedList<>();
        for(String eachId : idSplit){
            res.add(queryById(Long.parseLong(eachId.trim())));
        }
        return res;
    }
}
