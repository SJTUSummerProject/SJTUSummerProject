package com.mahoutjdbcmicroservice;

import com.mahoutjdbcmicroservice.DataModel.Domain.ItemEntity;
import com.mahoutjdbcmicroservice.DataModel.Domain.OrderEntity;
import com.mahoutjdbcmicroservice.DataModel.Domain.UserRecommendEntity;
import com.mahoutjdbcmicroservice.Service.OrderService;
import com.mahoutjdbcmicroservice.Service.UserRecommandService;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;

import static java.lang.Thread.sleep;

@Component
public class MahoutRunner implements ApplicationRunner{

    @Autowired
    OrderService orderService;
    @Autowired
    UserRecommandService userRecommandService;

    @Value("${nearestTicketNumber")
    int nearestTicketNumber;

    @Override
    public void run(ApplicationArguments var) throws Exception{
        System.out.println("MyApplicationRunner class will be execute when the project was started!");
        while(true){
            writeUserTicketFile(getOrders());
            readUserTicketFileAndCreateRecommanderAndSaveToDataBase();
            sleep(1000*60*15);  //每15分钟更新一次
        }
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

    public List<OrderEntity> getOrders(){
        return orderService.queryOrders();
    }

    public void readUserTicketFileAndCreateRecommanderAndSaveToDataBase() throws IOException, TasteException {
        DataModel dataModel = new FileDataModel(new File("user_ticket.csv"));

        PearsonCorrelationSimilarity pearsonCorrelationSimilarity = new PearsonCorrelationSimilarity(dataModel);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(nearestTicketNumber, pearsonCorrelationSimilarity, dataModel);//选择最近的6个ticket
        Recommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, pearsonCorrelationSimilarity);

        for(LongPrimitiveIterator userIds = recommender.getDataModel().getUserIDs(); userIds.hasNext();){
            writeDatabase(userIds.nextLong(),recommender);
        }
    }

    public void writeDatabase(Long userId, Recommender recommender) throws TasteException {
        UserRecommendEntity userRecommendEntity = new UserRecommendEntity();
        userRecommendEntity.setId(userId);

        LinkedList<Long> ticketRecommands = new LinkedList<>();

        List<RecommendedItem> recommendedItems = recommender.recommend(userId,nearestTicketNumber);
        // 输出推荐的物品
        for (RecommendedItem recommendedItem : recommendedItems) {
            ticketRecommands.add(recommendedItem.getItemID());
        }
        userRecommendEntity.setTicketRecommends(ticketRecommands);
        userRecommandService.save(userRecommendEntity);
    }
}