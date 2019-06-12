package com.intuit.account.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.intuit.account.AccountApplication;
import com.intuit.account.entity.User;
import com.intuit.account.model.UserType;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {AccountApplication.class})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testSave() {
        User createdUser = saveUsers(1).get(0);
        Assert.assertNotNull(createdUser.getId());
    }

    @Test
    public void testDelete() {
        User createdUser = saveUsers(1).get(0);
        userRepository.deleteById(createdUser.getId());
        Assert.assertFalse(userRepository.findById(createdUser.getId()).isPresent());
    }

    @Test
    public void testFindById() {
        User createdUser = saveUsers(1).get(0);
        Optional<User> fUser = userRepository.findById(createdUser.getId());
        Assert.assertEquals(createdUser.getId(), fUser.get().getId());
    }

    @Test
    public void testFindByEmailId() {
        User createdUser = saveUsers(1).get(0);
        Optional<User> fUser = userRepository.findByEmailId(createdUser.getEmailId());
        Assert.assertEquals(createdUser.getId(), fUser.get().getId());
    }

    @Test
    public void testFindAll() {
        List<User> createdUsers = saveUsers(2);
        List<User> fUser = userRepository.findAll();
        Assert.assertEquals(createdUsers.size(), fUser.size());
    }

    @Test
    public void testFindByUserType() {
        List<User> createdUsers = saveUsers(2);
        List<User> fUser = userRepository.findByUserType(UserType.INDIVIDUAL);
        Assert.assertEquals(createdUsers.get(1).getId(), fUser.get(0).getId());
    }

    private List<User> saveUsers(int count) {
        return userRepository.saveAll((Iterable<User>) createUser(count));
    }

    private List<User> createUser(int noOfusers) {
        List<User> users = new ArrayList<>();
        while (noOfusers-- > 0) {
            User user = new User();
            user.setFirstName("firstNameTest" + noOfusers);
            user.setLastName("lastNameTest" + noOfusers);
            user.setEmailId("test@test" + noOfusers + ".com");
            user.setPhoneNum("123456789" + noOfusers);
            if (noOfusers % 2 == 0) {
                user.setUserType(UserType.INDIVIDUAL);
            } else {
                user.setUserType(UserType.BUSINESS);
            }
            users.add(user);
        }
        return users;
    }

}