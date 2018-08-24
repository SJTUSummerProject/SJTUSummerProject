package sjtusummerproject.collectionmicroservice.Service.ServiceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sjtusummerproject.collectionmicroservice.DataModel.Dao.CollectionPageRepository;
import sjtusummerproject.collectionmicroservice.DataModel.Dao.CollectionRepository;
import sjtusummerproject.collectionmicroservice.DataModel.Domain.CollectionEntity;
import sjtusummerproject.collectionmicroservice.DataModel.Domain.TicketEntity;
import sjtusummerproject.collectionmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.collectionmicroservice.Service.CollectionService;

import javax.swing.plaf.metal.OceanTheme;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    CollectionRepository collectionRepository;
    @Autowired
    CollectionPageRepository collectionPageRepository;



    @Override
    public CollectionEntity save(UserEntity userEntity, TicketEntity ticketEntity){
        CollectionEntity testIfExisted = collectionRepository.findByTicketId(ticketEntity.getId());
        if (testIfExisted != null) return testIfExisted;
        CollectionEntity collectionEntity = new CollectionEntity();
        collectionEntity.setUserId(userEntity.getId());
        collectionEntity.setTicketId(ticketEntity.getId());
        collectionEntity.setImage(ticketEntity.getImage());
        collectionEntity.setTitle(ticketEntity.getTitle());
        collectionEntity.setIntro(ticketEntity.getIntro());
        return collectionRepository.save(collectionEntity);
    }

    @Override
    @Transactional
    public String deleteBatchByIds(String ids){
        String[] splitIds = ids.trim().replace("[","").replace("]","").split(",");

        for(String eachId : splitIds){
            collectionRepository.deleteById(Long.parseLong(eachId.trim()));
        }
        return "ok";
    }

    @Override
    public Page<CollectionEntity> findByUserid(Long id, Pageable pageable){
        return collectionPageRepository.findAllByUserId(id, pageable);
    }
}
