package com.ch.ebusiness.service.before.impl;

import java.util.ArrayList;
import java.util.List;

import com.ch.ebusiness.entity.Role;
import com.ch.ebusiness.entity.dto.UserDto;
import com.ch.ebusiness.exception.EmailExistException;
import com.ch.ebusiness.service.before.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ch.ebusiness.repository.before.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String isUse(UserDto userDto) {
        if (userRepository.isUse(userDto).size() > 0) {
            return "no";
        }
        return "ok";
    }

    @Override
    @Transactional
    public String register(UserDto userDto) throws EmailExistException {
        //对密码加密
        UserDto user = userRepository.getUserByUsername(userDto.getUsername());
        if(user != null)
		{
			throw new EmailExistException("邮箱/账号已存在, 请重新注册");
		}
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        int result = userRepository.register(userDto);

        if (result > 0)
		{
            Integer userId = userDto.getUserId();
            userRepository.addRole(2, userId);//添加角色
            return "user/login";
        }
        return "user/register";
    }


    @Override
    public List<String> getRolesByUsername(Integer userId)
    {
        List<Role> roles = userRepository.getRolesByUserId(userId);
        System.out.println(roles);
        List<String> roleNames = new ArrayList<>();
        roles.forEach(c -> roleNames.add(c.getRoleName()));
        System.out.println(roleNames);
        return roleNames;
    }

}
