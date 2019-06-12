package com.intuit.account.util;

import com.intuit.account.model.User;
import com.intuit.account.model.UserType;

public class UserUtil {

    public static com.intuit.account.model.User createUserDto() {
        com.intuit.account.model.User newUser = new com.intuit.account.model.User();
        newUser.setFirstName("newUser");
        newUser.setLastName("newUserLast");
        newUser.setEmailId("email@email.com");
        newUser.setPhoneNum("213456789");
        newUser.setUserType(UserType.INDIVIDUAL);
        return newUser;
    }

    public static com.intuit.account.model.User createUserDtoWithId() {
        User userDto = createUserDto();
        userDto.setId("requestedId");
        return userDto;
    }
}
