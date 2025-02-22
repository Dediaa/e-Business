package com.ch.ebusiness.repository.before;

import java.util.List;

import com.ch.ebusiness.entity.Role;
import com.ch.ebusiness.entity.dto.UserDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRepository
{
    @Select("select * from usertable where username=#{username}")
    List<UserDto> isUse(UserDto userDto);

    @Insert("insert into usertable (user_id, username, password) values(null, #{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    int register(UserDto userDto);


    @Select("select * from usertable where username=#{username}")
    UserDto getUserByUsername(String username);


    /**
     * 根据用户id查询用户权限
     */
    List<Role> getRolesByUserId(Integer userId);

    @Insert("insert into role_user (user_id, role_id) values(#{userId}, #{roleId})")
    void addRole(int roleId, Integer userId);
}
