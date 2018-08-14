package sjtusummerproject.collectionmicroservice.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sjtusummerproject.collectionmicroservice.DataModel.Domain.CollectionEntity;
import sjtusummerproject.collectionmicroservice.DataModel.Domain.TicketEntity;
import sjtusummerproject.collectionmicroservice.DataModel.Domain.UserEntity;

import java.util.List;

public interface CollectionService {
    public CollectionEntity save(UserEntity userEntity, TicketEntity ticketEntity);
    public String deleteBatchByIds(String ids);
    public Page<CollectionEntity> findByUserid(Long id, Pageable pageable);
}
