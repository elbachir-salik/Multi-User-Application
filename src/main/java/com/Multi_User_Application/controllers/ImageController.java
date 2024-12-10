package com.Multi_User_Application.controllers;


import com.Multi_User_Application.dtos.UpdateImageDescriptionDTO;
import com.Multi_User_Application.entities.Image;
import com.Multi_User_Application.entities.User;
import com.Multi_User_Application.repo.UserRepository;
import com.Multi_User_Application.sevice.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/api")
public class ImageController {
    private final UserRepository userRepository;
    private final ImageService imageService;

    public ImageController(ImageService imageService, UserRepository userRepository) {
        this.imageService = imageService;
        this.userRepository = userRepository;
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
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username not found"));

        Image image = new Image();
        image.setTitle(file.getOriginalFilename());
        image.setUrl("path/save" + file.getOriginalFilename());
        Image savedImage = imageService.uploadImage(image, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
    }


    @DeleteMapping("/image/delete/{id}")
    @PreAuthorize("hasAuthority('Beta_Player')")
    public ResponseEntity<?> deleteImage(@PathVariable Long id){
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/description")
    @PreAuthorize("hasAuthority('Beta_Player')")
    public ResponseEntity<Image> updateImage(@PathVariable Long id, @RequestBody UpdateImageDescriptionDTO dto) {
        Image updatedImage = imageService.updateDescription(dto, id);
        return ResponseEntity.ok(updatedImage);
    }
}
