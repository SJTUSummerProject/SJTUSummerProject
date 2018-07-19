package com.sjtusummerproject.commentmicroservice;


import com.sjtusummerproject.commentmicroservice.DataModel.Dao.CommentRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class save {
    @Autowired
    CommentRepository commentRepository;

    @Test
    public void testsave(){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent("123123123123");

        commentRepository.save(commentEntity);
        // service.update(stu);
        System.out.println("已生成ID：" + commentEntity.getId());
    }
}
