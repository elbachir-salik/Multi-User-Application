package com.Multi_User_Application.repo;

import com.Multi_User_Application.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
