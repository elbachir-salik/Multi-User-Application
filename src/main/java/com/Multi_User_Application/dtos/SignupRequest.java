package com.Multi_User_Application.dtos;


import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String role;
}
