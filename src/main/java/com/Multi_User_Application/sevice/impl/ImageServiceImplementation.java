package com.Multi_User_Application.sevice.impl;

import com.Multi_User_Application.dtos.UpdateImageDescriptionDTO;
import com.Multi_User_Application.entities.Image;
import com.Multi_User_Application.exceptions.ResourceNotFoundException;
import com.Multi_User_Application.repo.ImageRepository;
import com.Multi_User_Application.sevice.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImplementation implements ImageService {

    private final ImageRepository imageRepository;

    public ImageServiceImplementation(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Image> getImages(){
        return imageRepository.findAll();
    }

    @Override
    public Image getImage(Long id){
        return imageRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Image not found with id" + id));
    }
    @Override
    public Image uploadImage(Image image,Long uploadedByUserId){
        image.setUploadedByUserId(uploadedByUserId);
        return imageRepository.save(image);
    }

    @Override
    public void deleteImage(Long id){
        Image image = getImage(id);
        imageRepository.delete(image);
    }

    @Override
    public Image updateDescription(UpdateImageDescriptionDTO dto, Long id){
        Image existingImage = imageRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Image not found with id" + id));
        if(dto == null ||dto.getDescription() == null ){
            throw new IllegalArgumentException("Description cannot be null or empty");
        }

        existingImage.setDescription(dto.getDescription());
        return imageRepository.save(existingImage);
    }
}
