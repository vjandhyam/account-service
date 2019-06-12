package com.intuit.account.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.intuit.account.response.ErrorStatus;

import lombok.Getter;
import lombok.Setter;

@JsonSerialize(
        include = JsonSerialize.Inclusion.NON_EMPTY
)
@Getter
@Setter
public class BaseResponse {
    private ErrorStatus errorStatus;
    private String errorCode;
    private String errorDescription;
}