package com.intuit.account.service;

import java.util.List;

import com.intuit.account.model.User;
import com.intuit.account.model.UserType;

public interface UserService {

    User findByUserId(String userId);

    List<User> findByUserType(UserType userType);

    List<User> findAllUsers();

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(String userId);
}
