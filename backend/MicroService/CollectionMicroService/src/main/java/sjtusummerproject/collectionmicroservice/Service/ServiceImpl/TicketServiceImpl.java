package sjtusummerproject.collectionmicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sjtusummerproject.collectionmicroservice.DataModel.Domain.TicketEntity;
import sjtusummerproject.collectionmicroservice.Service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${ticketservice.url}")
    String baseUrl;

    @Override
    public TicketEntity queryTicketById(Long id) {
        /* 发送给 TicketMicroService */
        String url=baseUrl+"/Ticket/QueryById?"+"id="+id;
        /* 注意：必须 http、https……开头，不然报错，浏览器地址栏不加 http 之类不出错是因为浏览器自动帮你补全了 */
        TicketEntity result = restTemplate.getForObject(url, TicketEntity.class);

        //System.out.println("the result in query ticket "+result);
        return result;
    }

    @Override
    public void setRestTemplate(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

}
