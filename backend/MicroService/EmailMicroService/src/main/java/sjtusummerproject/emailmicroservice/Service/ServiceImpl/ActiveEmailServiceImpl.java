package sjtusummerproject.emailmicroservice.Service.ServiceImpl;

import org.springframework.stereotype.Service;
import sjtusummerproject.emailmicroservice.Service.ActiveEmailService;

@Service
public class ActiveEmailServiceImpl implements ActiveEmailService {
    /**
     * 激活用户
     * @param code 用户激活码
     * @return 是否激活成功
     */
    public Boolean Active(String code){
        String username = "hello from the outside!";
        System.out.println("Active!");
//      UserDao userDao=new UserDao();
//      String username=userDao.findUserByCode(code);
        if(username!=null && username!=""){
            //如果存在用户，将此用户状态设为可用
//          userDao.setState(username);
            return true;
        }else{
            return false;
        }
    }
}
