package com.ch.ebusiness.utils;

import com.ch.ebusiness.entity.dto.UserDto;
import com.ch.ebusiness.service.UserDetailsServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SecurityUtils {

    public static UserDetails getCurrentUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

    public static boolean hasRole(String role) {
        UserDetails user = getCurrentUser();
        if (user != null)
        {
            return user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(role));
        }
        return false;
    }

    public static String getUsername() {
        UserDetails user = getCurrentUser();
        return user != null ? user.getUsername() : null;
    }

    /**
     * 获得用户信息
     */
    public static UserDto getUser(HttpSession session)
    {
        System.out.println("session = " + session + "bUser" + session.getAttribute("bUser"));
        return (UserDto) session.getAttribute("bUser");
    }

    /**
     * 获取当前登录用户的完整信息
     */
    public static UserDto getCurrentUserInfo(HttpSession session)
    {
        UserDto user = getUser(session);
        if (user == null) {
            // 如果session中没有，尝试从SecurityContext中获取
            UserDetails userDetails = getCurrentUser();
            if (userDetails != null)
            {
                // 这里需要注入UserDetailsServiceImpl来获取完整用户信息
                // 由于是静态方法，建议使用Spring的ApplicationContext
                ApplicationContext context = ApplicationContextProvider.getApplicationContext();
                UserDetailsServiceImpl userDetailsService = context.getBean(UserDetailsServiceImpl.class);
                user = userDetailsService.getUserByUsername(userDetails.getUsername());
                // 存入session
                System.out.println("user = " + user);
                session.setAttribute("bUser", user);
            }
        }
        return user;
    }
}