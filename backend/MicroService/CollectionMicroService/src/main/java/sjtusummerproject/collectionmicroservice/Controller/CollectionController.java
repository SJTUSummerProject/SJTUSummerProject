package sjtusummerproject.collectionmicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sjtusummerproject.collectionmicroservice.DataModel.Domain.CollectionEntity;
import sjtusummerproject.collectionmicroservice.DataModel.Domain.TicketEntity;
import sjtusummerproject.collectionmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.collectionmicroservice.Service.CollectionService;
import sjtusummerproject.collectionmicroservice.Service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/Collection")
public class CollectionController {
    @Autowired
    TicketService ticketService;
    @Autowired
    CollectionService collectionService;

    RestTemplate restTemplate = new RestTemplate();

    @Value("${collection.page.size}")
    private int PageSize;
    @Value("${collection.page.offset}")
    private int PageOffset;

    @Value("${authservice.url}")
    private String url;

    /* Get Pageable */
    Pageable createPageable(HttpServletRequest request){
        return new PageRequest(Integer.parseInt(request.getParameter("pagenumber"))-PageOffset, PageSize, new Sort(Sort.Direction.DESC, "id"));
    }

    /* "/SaveInDetailPage" 包括了更新与插入 */
    @RequestMapping(value = "/Save")
    @ResponseBody
    public String save(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
        if (result != 0) return null;

        Long ticketId = Long.parseLong(request.getParameter("ticketid"));
        TicketEntity ticketEntity = ticketService.queryTicketById(ticketId);

        collectionService.save(userEntity, ticketEntity);
        return "ok";
    }

    @RequestMapping(value = "/DeleteBatch")
    @ResponseBody
    public String deleteBatchInCollection(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
        if (result != 0) return null;
        /* parameters */
        String collectionIds = request.getParameter("batchcollectionid");
        collectionService.deleteBatchByIds(collectionIds);
        return "ok";
    }

    /* 有page! */
    @RequestMapping(value = "/QueryByUserId")
    @ResponseBody
    public Page<CollectionEntity> queryByUserid(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
        if (result != 0) return null;

        /* parameters */
        Long userId = userEntity.getId();
        return collectionService.findByUserid(userEntity.getId(), createPageable(request));
    }

    @RequestMapping(value = "/share")
    public String share(HttpServletRequest request, HttpServletResponse response){
        return null;
    }

    private UserEntity callAuthService(String token){
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("token", token);
        return restTemplate.postForObject(url, multiValueMap, UserEntity.class);
    }

    private int authUser(UserEntity userEntity){

        if (userEntity == null) return 1;
        else if (!userEntity.getAuthority().equals("Customer")) return 2;
        else if (userEntity.getStatus().equals("Frozen")) return 3;
        else return 0;
    }
}
