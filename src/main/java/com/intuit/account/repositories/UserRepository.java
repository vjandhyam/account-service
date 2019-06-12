package com.intuit.account.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.intuit.account.entity.User;
import com.intuit.account.model.UserType;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByUserType(UserType userType);

    Optional<User> findByEmailId(String emailId);
}
