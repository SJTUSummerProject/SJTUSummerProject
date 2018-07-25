package com.sjtusummerproject.ordermicroservice.Service;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.UserDetailEntity;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.tomcat.util.buf.UDecoder;

public interface UserDetailService {
    public UserDetailEntity queryUserDetailById(String token);
    public Boolean updateAccountMinus(Long userid, double toMinus);
    public Boolean updateAccountPlus(Long userid, double toPlus);
}
