package sjtusummerproject.collectionmicroservice.DataModel.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import sjtusummerproject.collectionmicroservice.Controller.CollectionController;
import sjtusummerproject.collectionmicroservice.DataModel.Domain.CollectionEntity;


public interface CollectionPageRepository extends PagingAndSortingRepository<CollectionEntity, Long> {
    public Page<CollectionEntity> findAllByUserId(Long id, Pageable pageable);
}