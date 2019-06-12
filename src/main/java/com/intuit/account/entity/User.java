package com.intuit.account.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.intuit.account.model.UserType;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "users")
@Getter
@Setter
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String emailId;
    private UserType userType;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", emailId='" + emailId + '\'' +
                ", userType=" + userType +
                '}';
    }
}
