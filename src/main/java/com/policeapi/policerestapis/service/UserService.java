package com.policeapi.policerestapis.service;

import com.policeapi.policerestapis.dto.UserDto;
import com.policeapi.policerestapis.model.User;

public interface UserService {
    User registerNewUser(UserDto userDto);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
