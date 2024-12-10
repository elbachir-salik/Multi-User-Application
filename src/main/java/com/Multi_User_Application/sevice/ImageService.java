package com.Multi_User_Application.sevice;

import com.Multi_User_Application.dtos.UpdateImageDescriptionDTO;
import com.Multi_User_Application.entities.Image;

import java.util.List;

public interface ImageService {
    List<Image> getImages();
    Image getImage(Long id);
    Image uploadImage(Image image,Long uploadedByUserId);
    void deleteImage(Long id);
    Image updateDescription(UpdateImageDescriptionDTO dto, Long id);
}
