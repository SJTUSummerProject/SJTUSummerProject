package sjtusummerproject.signmicroservice.Service;

import org.springframework.stereotype.Service;
import sjtusummerproject.signmicroservice.DataModel.Domain.UserEntity;

public interface InvokeEmailMessageService {
    public Object AddEmailServiceRabbit(UserEntity user);
}
