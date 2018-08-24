package sjtusummerproject.collectionmicroservice.DataModel.Dao;


import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.collectionmicroservice.DataModel.Domain.CollectionEntity;

import java.util.List;

public interface CollectionRepository extends CrudRepository<CollectionEntity ,Long> {
    void deleteById(Long id);
    CollectionEntity findByTicketId(Long id);
}
