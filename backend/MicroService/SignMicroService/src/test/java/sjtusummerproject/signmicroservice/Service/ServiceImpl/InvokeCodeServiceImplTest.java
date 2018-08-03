package sjtusummerproject.signmicroservice.Service.ServiceImpl;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import sjtusummerproject.signmicroservice.SignmicroserviceApplicationTests;

import static org.junit.Assert.*;

public class InvokeCodeServiceImplTest extends SignmicroserviceApplicationTests {

    @Test
    public void validCodeSuccess() {
        invokeCodeService.setTemplate(codeSuccessMock);
        boolean result = invokeCodeService.validCode("1","1");
        assertEquals(true, result);
    }

    @Test
    public void validCodeFail(){
        invokeCodeService.setTemplate(codeFailMock);
        boolean result = invokeCodeService.validCode("1","1");
        assertEquals(false, result);

    }
}