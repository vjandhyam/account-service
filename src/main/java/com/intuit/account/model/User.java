package com.intuit.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends BaseResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String emailId;
    private UserType userType;
}
