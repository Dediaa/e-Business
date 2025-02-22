package com.ch.ebusiness.controller.before;

import com.ch.ebusiness.entity.dto.UserDto;
import com.ch.ebusiness.exception.EmailExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.ebusiness.service.before.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/toRegister")
    public String toRegister(@ModelAttribute("bUser") UserDto user)
	{
        return "user/register";
    }

    @RequestMapping("/toLogin")
    public String toLogin()
    {
        return "user/login";
    }

    @RequestMapping("/isUse")
    @ResponseBody
    public String isUse(@RequestBody UserDto userDto)
    {
        return userService.isUse(userDto);
    }

    @RequestMapping("/register")
    public String register(@ModelAttribute("bUser") @Validated UserDto user, BindingResult rs) throws EmailExistException {
        if (rs.hasErrors())
        {
            //验证失败
            return "user/register";
        }
        return userService.register(user);
    }
}
