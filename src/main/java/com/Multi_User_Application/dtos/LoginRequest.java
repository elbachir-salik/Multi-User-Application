package com.Multi_User_Application.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
