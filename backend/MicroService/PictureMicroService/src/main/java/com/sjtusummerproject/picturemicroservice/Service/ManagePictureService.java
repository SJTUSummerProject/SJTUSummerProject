package com.sjtusummerproject.picturemicroservice.Service;

import com.sjtusummerproject.picturemicroservice.DataModel.Domain.PictureEntity;

import java.util.UUID;

public interface ManagePictureService {
    public PictureEntity save(UUID uuid, byte[] picture);
    public PictureEntity query(UUID uuid);
}
