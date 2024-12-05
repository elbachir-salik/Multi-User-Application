package com.Multi_User_Application.sevice;

import com.Multi_User_Application.entities.Image;

import java.util.List;

public interface ImageService {
    List<Image> getImages();
    Image getImage(Long id);
    Image uploadImage(Image image);
    void deleteImage(Long id);
}
