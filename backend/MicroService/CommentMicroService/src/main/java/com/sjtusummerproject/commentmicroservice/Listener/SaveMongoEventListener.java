package com.sjtusummerproject.commentmicroservice.Listener;

import com.sjtusummerproject.commentmicroservice.Annotation.GeneratedValue;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.SequenceId;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/* 用于 mongodb自增id 的反射listener*/
public class SaveMongoEventListener extends AbstractMongoEventListener<Object> {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void onBeforeConvert(BeforeConvertEvent event) {
        Object source = event.getSource();
        if (source != null) {
            ReflectionUtils.doWithFields(source.getClass(),
                    new ReflectionUtils.FieldCallback() {
                        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                            ReflectionUtils.makeAccessible(field);
                            if (field.isAnnotationPresent(GeneratedValue.class)) {                        //设置自增ID
                                field.set(source, getNextId(source.getClass().getSimpleName()));
                            }
                        }
                    });
        }
    }

    /**
     * 获取下一个自增ID
     *
     * @param CommentEntity 集合名
     * @return
     * @author Xu Tianqiang
     */
    private Long getNextId(String CommentEntity) {
        Query query = new Query(Criteria.where("collName").is(CommentEntity));
        Update update = new Update();
        update.inc("seqId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        SequenceId seqId = mongoTemplate.findAndModify(query, update, options, SequenceId.class);
        return seqId.getSeqId();
    }
}