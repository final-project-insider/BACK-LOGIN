package com.insider.login.auth.image.repository;

import com.insider.login.auth.image.dto.ImageDTO;
import com.insider.login.auth.image.entity.Image;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ImageRepository {

    @PersistenceContext
    private EntityManager manager;

    public void uploadImage(ImageDTO imageDTO) {
        manager.persist(imageDTO);
    }

    // 이미지 찾는 용도
    public ImageDTO findById(int memberImageNo) {
        return manager.find(ImageDTO.class, memberImageNo);
    }

    public void saveImage(Image image) {
    }
}
