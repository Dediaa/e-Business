package com.ch.ebusiness.controller.before;

import com.ch.ebusiness.entity.dto.UserDto;
import com.ch.ebusiness.exception.NoLoginException;
import com.ch.ebusiness.utils.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class BeforeBaseController {
	/**
	 * 登录权限控制，处理方法执行前执行该方法 
	 */
	@ModelAttribute  
    public void isLogin(HttpSession session) throws NoLoginException
    {
        // 先检查session中是否有用户信息
        UserDto user = (UserDto) session.getAttribute("bUser");
        if (user == null) {
            // 如果session中没有，尝试从SecurityContext中获取并保存到session
            user = SecurityUtils.getCurrentUserInfo(session);
            if (user == null) {
                throw new NoLoginException("没有登录");
            }
        }
    } 
}