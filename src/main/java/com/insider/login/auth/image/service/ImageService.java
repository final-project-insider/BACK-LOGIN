package com.insider.login.auth.image.service;

import com.insider.login.auth.image.repository.ImageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }
}
