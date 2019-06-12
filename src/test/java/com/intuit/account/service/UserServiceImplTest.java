package com.intuit.account.service;

import static com.intuit.account.util.UserUtil.createUserDto;
import static com.intuit.account.util.UserUtil.createUserDtoWithId;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.intuit.account.AccountApplication;
import com.intuit.account.entity.User;
import com.intuit.account.mapper.UserMapper;
import com.intuit.account.model.UserType;
import com.intuit.account.repositories.UserRepository;
import com.intuit.account.response.ErrorStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {AccountApplication.class})
public class UserServiceImplTest {

    public static final String MOCKED_ID = "mockedId";
    @Autowired
    private UserService classUnderTest;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindByUserId() {
        List<User> mockUsers = createMockUsers(1);
        when(userRepository.findById(MOCKED_ID)).thenReturn(java.util.Optional.of(mockUsers.get(0)));

        com.intuit.account.model.User userDto = classUnderTest.findByUserId(MOCKED_ID);
        Assert.assertEquals(mockUsers.get(0).getId(), userDto.getId());
        verify(userRepository, times(1)).findById(MOCKED_ID);
    }

    @Test
    public void testFindByUserType() {
        List<User> mockUsers = createMockUsers(2);
        when(userRepository.findByUserType(UserType.INDIVIDUAL)).thenReturn(Arrays.asList(mockUsers.get(1)));

        List<com.intuit.account.model.User> userDtos = classUnderTest.findByUserType(UserType.INDIVIDUAL);
        Assert.assertEquals(mockUsers.get(1).getId(), userDtos.get(0).getId());
        verify(userRepository, times(1)).findByUserType(UserType.INDIVIDUAL);
    }

    @Test
    public void testFindAll() {
        List<User> mockUsers = createMockUsers(1);
        when(userRepository.findAll()).thenReturn(Arrays.asList(mockUsers.get(0)));

        List<com.intuit.account.model.User> userDtos = classUnderTest.findAllUsers();
        Assert.assertEquals(mockUsers.get(0).getId(), userDtos.get(0).getId());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteById() {
        List<User> mockUsers = createMockUsers(1);
        doNothing().when(userRepository).deleteById(any(String.class));

        classUnderTest.deleteUser(mockUsers.get(0).getId());
        verify(userRepository, times(1)).deleteById(any(String.class));
    }

    @Test
    public void testCreateUserWhenNoExistingUserPresent() {
        com.intuit.account.model.User newUser = createUserDto();

        User userEntity = userMapper.map(newUser, User.class);
        userEntity.setId("savedUserId");

        when(userRepository.save(any(User.class))).thenReturn(userEntity);
        when(userRepository.findByEmailId(any(String.class))).thenReturn(java.util.Optional.empty());

        com.intuit.account.model.User userDto = classUnderTest.createUser(newUser);
        Assert.assertNotNull(userDto.getId());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(1)).findByEmailId(any(String.class));
    }

    @Test
    public void testCreateUserWithSameEmailId() {
        com.intuit.account.model.User newUser = createUserDto();

        List<User> mockUsers = createMockUsers(1);
        when(userRepository.findByEmailId(any(String.class))).thenReturn(java.util.Optional.of(mockUsers.get(0)));

        com.intuit.account.model.User userDto = classUnderTest.createUser(newUser);
        Assert.assertEquals(ErrorStatus.CONFLICT, userDto.getErrorStatus());
        verify(userRepository, times(1)).findByEmailId(any(String.class));
    }

    @Test
    public void testCreateUserWhenExistingUserPresent() {

        com.intuit.account.model.User newUser = createUserDtoWithId();
        User userEntity = userMapper.map(newUser, User.class);

        when(userRepository.save(any(User.class))).thenReturn(userEntity);

        com.intuit.account.model.User userDto = classUnderTest.createUser(newUser);
        Assert.assertNotNull(userDto.getId());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUser() {
        List<User> mockUsers = createMockUsers(1);

        when(userRepository.save(any(User.class))).thenReturn(mockUsers.get(0));
        com.intuit.account.model.User userDto = userMapper.map(mockUsers.get(0), com.intuit.account.model.User.class);
        com.intuit.account.model.User response = classUnderTest.updateUser(userDto);
        Assert.assertNotNull(response);
        verify(userRepository, times(1)).save(any(User.class));
    }

    private List<User> createMockUsers(int count) {
        List<User> users = new ArrayList<>();

        while (count-- > 0) {
            User mockUser = new User();
            mockUser.setId(MOCKED_ID + count);
            mockUser.setFirstName("mockedFirstName" + count);
            mockUser.setLastName("mockedLastName" + count);
            mockUser.setEmailId("mocked@mocked" + count + ".com");
            mockUser.setPhoneNum("123456789" + count);
            if (count % 2 == 0) {
                mockUser.setUserType(UserType.INDIVIDUAL);
            } else {
                mockUser.setUserType(UserType.BUSINESS);
            }
            users.add(mockUser);
        }
        return users;
    }

}