package com.intuit.account.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.intuit.account.mapper.UserMapper;
import com.intuit.account.model.User;
import com.intuit.account.model.UserType;
import com.intuit.account.repositories.UserRepository;
import com.intuit.account.response.ErrorStatus;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User findByUserId(String userId) {
        Optional<com.intuit.account.entity.User> userEntity = userRepository.findById(userId);

        return userMapper.map(userEntity.get(), User.class);
    }

    @Override
    public List<User> findByUserType(UserType userType) {
        List<com.intuit.account.entity.User> users = userRepository.findByUserType(userType);

        if (CollectionUtils.isEmpty(users)) {
            return Collections.EMPTY_LIST;
        }
        return userMapper.mapAsList(users.toArray(), User.class);
    }

    @Override
    public List<User> findAllUsers() {
        List<com.intuit.account.entity.User> users = userRepository.findAll();

        if (CollectionUtils.isEmpty(users)) {
            return Collections.EMPTY_LIST;
        }
        return userMapper.mapAsList(users.toArray(), User.class);
    }

    @Override
    public User createUser(User user) {
        if (StringUtils.isEmpty(user.getId()) && userRepository.findByEmailId(user.getEmailId()).isPresent()) {
            return buildAlreadyExistsUser();
        }

        com.intuit.account.entity.User userEntity = userMapper.map(user, com.intuit.account.entity.User.class);

        com.intuit.account.entity.User updatedUser = userRepository.save(userEntity);
        return userMapper.map(updatedUser, User.class);
    }

    @Override
    public User updateUser(User user) {
        com.intuit.account.entity.User userEntity = userMapper.map(user, com.intuit.account.entity.User.class);

        com.intuit.account.entity.User updatedUser = userRepository.save(userEntity);
        return userMapper.map(updatedUser, User.class);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    private User buildAlreadyExistsUser() {
        User user = new User();
        user.setErrorCode("INTUITBSERR1");
        user.setErrorStatus(ErrorStatus.CONFLICT);
        user.setErrorDescription("User Already exists");
        return user;
    }

}
