package com.sjtusummerproject.picturemicroservice.Service.ServiceImpl;

import com.sjtusummerproject.picturemicroservice.DataModel.Dao.PictureRepository;
import com.sjtusummerproject.picturemicroservice.DataModel.Domain.PictureEntity;
import com.sjtusummerproject.picturemicroservice.Service.ManagePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ManagePictureServiceImpl implements ManagePictureService {
    @Autowired
    PictureRepository pictureRepository;

    @Override
    public PictureEntity save(UUID uuid, byte[] picture) {
        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setUuid(uuid);
        pictureEntity.setBase64(picture);

        return pictureRepository.save(pictureEntity);
    }

    @Override
    public PictureEntity query(UUID uuid) {
        return pictureRepository.findByUuid(uuid);
    }
}
