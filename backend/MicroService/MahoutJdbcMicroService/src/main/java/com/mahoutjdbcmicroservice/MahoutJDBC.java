package com.mahoutjdbcmicroservice;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Component
public class MahoutJDBC implements ApplicationRunner {

    static Map<Long, Long> hashMap = new HashMap<Long, Long>();

    public HashMap<Long, Long> SVDlist(DataModel model,
                                       ArrayList<Long> userIDs, ArrayList<Long> brandIDs)
            throws TasteException {
        Recommender recommender = new SVDRecommender(model,
                new ALSWRFactorizer(model, 10, 0.75, 20));

        for (int i = 0; i < userIDs.size(); i++) {
            float score = 0, scoretemp = 0;
            Long bestBrand = 0L;

            for (int j = 0; j < brandIDs.size(); j++) {
                scoretemp = recommender.estimatePreference(userIDs.get(i),
                        brandIDs.get(j));
                if (scoretemp > score) {
                    score = scoretemp;
                    bestBrand = brandIDs.get(j);
                }
                hashMap.put(userIDs.get(i), bestBrand);
            }
            System.out.println("The best brand for " + userIDs.get(i) + " is "
                    + bestBrand);
        }
        return (HashMap<Long, Long>) hashMap;
    }

    @Override
    public void run(ApplicationArguments var) throws Exception{
        System.out.println("MyApplicationRunner class will be execute when the project was started!");

        String driver = "com.mysql.jdbc.Driver";
        String host = "120.79.58.85";
        String user = "root";
        String password = "pzy19980525";
        String databasename = "SJTUSummerProject";

        Class.forName(driver);
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(host);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setDatabaseName(databasename);

        JDBCDataModel jdbcDataModel = new MySQLJDBCDataModel(dataSource,
                "testjdbc", "user_id", "item_id", null, null);
        //利用ReloadFromJDBCDataModel包裹jdbcDataModel,可以把输入加入内存计算，加快计算速度。
        ReloadFromJDBCDataModel model = new ReloadFromJDBCDataModel(
                jdbcDataModel);
        //这里的refresh是刷新model，一般情况下我觉得用不上，因为像我这个程序，每次都会新建立datamodel，都是最新的，所以不用刷新
        //当然，如果你需要在内存中存储下model，然后自己的taste_preferences时刻在变化，此时是需要刷新的。
        model.refresh(null);

        //测试的brandIds可以从自己的数据库中获得，这里为了简单，只是加了几个做测试。
        ArrayList<Long> brandIDs = new ArrayList<Long>();
        brandIDs.add(2215L);
//        brandIDs.add(458L);
        brandIDs.add(691L);
//        brandIDs.add(3027L);
        brandIDs.add(1143L);

        ArrayList<Long> userIDs = new ArrayList<Long>();
        userIDs.add(2640L);
        userIDs.add(1640L);
//        userIDs.add(22845512L);
        MahoutJDBC mahoutJDBC = new MahoutJDBC();
        hashMap = mahoutJDBC.SVDlist(model, userIDs, brandIDs);
        //在之后你可以把这数据写入自己的数据库，或者直接post给他前端，让前端显示相应的产品给用户。
//        for()
    }

}