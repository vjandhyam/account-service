package com.intuit.account.mapper;

import org.springframework.stereotype.Component;

import com.intuit.account.entity.User;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Component
public class UserMapper extends ConfigurableMapper {

    protected void configure(MapperFactory factory) {

        factory.classMap(User.class, com.intuit.account.model.User.class)
                .byDefault()
                .register();
    }
}