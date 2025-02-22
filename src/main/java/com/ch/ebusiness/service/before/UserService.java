package com.ch.ebusiness.service.before;

import com.ch.ebusiness.entity.dto.UserDto;
import com.ch.ebusiness.exception.EmailExistException;

import java.util.List;

public interface UserService {
    String isUse(UserDto userDto);

    public String register(UserDto userDto) throws EmailExistException;


    List<String> getRolesByUsername(Integer userId);
}
