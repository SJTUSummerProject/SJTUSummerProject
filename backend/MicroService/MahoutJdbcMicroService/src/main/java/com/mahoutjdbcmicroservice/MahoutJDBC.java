package com.mahoutjdbcmicroservice;

import com.mahoutjdbcmicroservice.Config.RestTemplateConfig;
import com.mahoutjdbcmicroservice.DataModel.Domain.ItemEntity;
import com.mahoutjdbcmicroservice.DataModel.Domain.OrderEntity;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.web.client.RestTemplate;

import static java.lang.Thread.sleep;

@Component
public class MahoutJDBC implements ApplicationRunner{

    static Map<Long, Long> hashMap = new HashMap<Long, Long>();

    String orderUrl = "http://order-microservice:8080";

    @Override
    public void run(ApplicationArguments var) throws Exception{
        System.out.println("MyApplicationRunner class will be execute when the project was started!");
        while(true){
            writeUserTicketFile(getOrders());
            sleep(1000*60*15);  //每15分钟更新一次
        }
    }

    public List<OrderEntity> getOrders(){
        String url = orderUrl+"/Order/QueryAll";
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new RestTemplateConfig());
        return template.getForObject(url,List.class);
    }

    public void writeUserTicketFile(List<OrderEntity> orderList) throws IOException {
        String fileName = "user_ticket.csv";
        File writename = new File(fileName); // 相对路径，如果没有则要建立一个新的output。txt文件
        writename.createNewFile(); // 创建新文件
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));

        for(OrderEntity eachOrder : orderList){
            Long userId = eachOrder.getUserId();
            Set<ItemEntity> itemSet = eachOrder.getItems();
            for(ItemEntity eachItem : itemSet){
                Long ticketId = eachItem.getTicketId();
                out.write(userId+','+ticketId+"\r\n"); // \r\n即为换行
            }
        }
        out.flush(); // 把缓存区内容压入文件
        out.close(); // 最后记得关闭文件
    }

    public void writeRecommendFile() throws IOException, TasteException {
        DataModel dataModel = new FileDataModel(new File("user_ticket.csv"));

        //ItemSimilarity sim = new LogLikelihoodSimilarity(dm);
        PearsonCorrelationSimilarity pearsonCorrelationSimilarity = new PearsonCorrelationSimilarity(dataModel);

        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, pearsonCorrelationSimilarity);

        int x=1;
        for(LongPrimitiveIterator items = dataModel.getItemIDs(); items.hasNext();) {
            long itemId = items.nextLong();
            List<RecommendedItem>recommendations = recommender.mostSimilarItems(itemId, 5);

            for(RecommendedItem recommendation : recommendations) {
                System.out.println(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue());
            }
            x++;
            //if(x>10) System.exit(1);
        }
    }
}