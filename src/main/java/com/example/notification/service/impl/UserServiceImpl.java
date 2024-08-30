package com.example.notification.service.impl;

import com.example.notification.dto.UserDTO;
import com.example.notification.global.service.CommonService;
import com.example.notification.input.UserRegister;
import com.example.notification.model.User;
import com.example.notification.repository.UserRepository;
import com.example.notification.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Override
    //TODO hash the password before saving it to the database
    public void createUser(UserRegister register) {
        User user = new User();
        user.setEmail(register.getEmail());
        user.setPassword(register.getPassword());
        userRepository.save(user);
    }


    @Override
    public UserDTO getUserDTO(String contact) {
        UserDTO user = userRepository.findByEmailOrPhone(contact);
        CommonService.throwIsNull(user, "User not found");
        return user;
    }
}
