package com.Multi_User_Application.controllers;


import com.Multi_User_Application.entities.Image;
import com.Multi_User_Application.sevice.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/api")
public class ImageController {
    ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images")
    @PreAuthorize("hasAnyAuthority('Beta_Player', 'Company_User', 'Growth_Plan_Subscriber')")
    public ResponseEntity<List<Image>> getImages() {
        return ResponseEntity.ok(imageService.getImages());
    }


    @GetMapping("/image/{id}")
    @PreAuthorize("hasAnyAuthority('Beta_Player', 'Company_User', 'Growth_Plan_Subscriber')")
    public ResponseEntity<Image> getImage(@PathVariable Long id) {
        Image image = imageService.getImage(id);
        return ResponseEntity.ok(image);
    }


    @PostMapping("/uploadimage")
    @PreAuthorize("hasAuthority('Beta_Player')")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        Image image = new Image();
        image.setTitle(file.getOriginalFilename());
        image.setUrl("path/save" + file.getOriginalFilename());
        Image savedImage = imageService.uploadImage(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
    }


    @DeleteMapping("/image/delete/{id}")
    @PreAuthorize("hasAuthority('Beta_Player')")
    public ResponseEntity<?> deleteImage(@PathVariable Long id){
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
