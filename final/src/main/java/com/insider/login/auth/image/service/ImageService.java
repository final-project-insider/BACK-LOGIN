package com.insider.login.auth.image.service;

import com.insider.login.auth.image.dto.ImageDTO;
import com.insider.login.auth.image.entity.Image;
import com.insider.login.auth.image.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Transactional
    public void insertImage(ImageDTO imageDTO) {

        Image image = new Image(imageDTO.getMemberImageNo(),
                                imageDTO.getMemberImageName(),
                                imageDTO.getMemberImagePath());
        imageRepository.saveImage(image);

//        imageRepository.uploadImage(imageDTO);      // 회원등록을 할 때 사진 저장할 것
    }
}
