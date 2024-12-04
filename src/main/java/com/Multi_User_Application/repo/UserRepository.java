package com.Multi_User_Application.repo;

import com.Multi_User_Application.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
