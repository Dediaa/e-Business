package com.ch.ebusiness.service;

import com.ch.ebusiness.entity.dto.UserDto;
import com.ch.ebusiness.repository.before.UserRepository;
import com.ch.ebusiness.service.before.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            System.out.println("Attempting to load user: " + username);
            UserDto user = userRepository.getUserByUsername(username);
            System.out.println(user);
            if(user == null) {
                System.out.println("User not found: " + username);
                throw new UsernameNotFoundException("用户不存在");
            }
            List<String> roles = userService.getRolesByUsername(user.getUserId());
            System.out.println(roles);
            String[] roleArray = roles.toArray(new String[roles.size()]);
            System.out.println("User found with roles: " + Arrays.toString(roleArray));
            return User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(roleArray)
                    .build();
        } catch (Exception e) {
            System.out.println("Error loading user: " + e.getMessage());
            throw e;
        }
    }

    public UserDto getUserByUsername(String username)
    {
        System.out.println("Getting user by username: " + username);
        UserDto user = userRepository.getUserByUsername(username);
        System.out.println("UserRepository returned: " + user);
        if (user == null) {
            System.out.println("Warning: No user found for username: " + username);
        }
        return user;
    }
}
