package com.ch.ebusiness.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


@Data
public class UserDto
{
    private Integer userId;
    @NotBlank(message = "用户名必须输入！")
    private String username;
    @NotBlank(message = "密码必须输入！")
    @Length(min = 6, max = 20, message = "密码长度在6到20之间！")
    private String password;
}
