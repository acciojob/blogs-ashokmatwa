package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository3;

    public User createUser(String username, String password){
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setFirstName("test");
        user.setLastName("test");

        userRepository3.save(user);
        return user;
    }

    public void deleteUser(int userId){
        userRepository3.deleteById(userId);
        //delete its blogs also or cascade effect done ??
    }

    public User updateUser(Integer id, String password){
        //passing PARTIAL user
        User originalUser = userRepository3.findById(id).get();
        originalUser.setPassword(password);

        userRepository3.save(originalUser);

        return originalUser;
    }
}
