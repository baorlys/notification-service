package com.example.notification.service;

import com.example.notification.dto.UserDTO;
import com.example.notification.input.UserRegister;

public interface UserService {
    void createUser(UserRegister register);


    UserDTO getUserDTO(String contact);
}
