package com.ch.ebusiness.config;

import com.ch.ebusiness.entity.dto.UserDto;
import com.ch.ebusiness.filter.CaptchaFilter;
import com.ch.ebusiness.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 创建验证码过滤器
        CaptchaFilter captchaFilter = new CaptchaFilter();
        captchaFilter.setAuthenticationManager(authenticationManagerBean());
        captchaFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
            response.sendRedirect(request.getContextPath() + "/user/toLogin?error");
        });
        
        // 创建认证成功处理器
        SaveToSessionSuccessHandler successHandler = new SaveToSessionSuccessHandler();
        successHandler.setUserDetailsService(userDetailsService);
        captchaFilter.setAuthenticationSuccessHandler(successHandler);

        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/css/**", "/fonts/**", "/images/**", "/js/**",
                        "/", "/user/toRegister", "/user/register", "/goodsDetail", "/search",
                        "/validateCode", "/user/login").permitAll()
                .antMatchers("/admin/**").hasRole("admin")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/user/toLogin")
                .loginProcessingUrl("/user/login")
                .failureUrl("/user/toLogin?error")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
            .rememberMe()
                .tokenValiditySeconds(86400)
                .key("uniqueAndSecret")
                .rememberMeParameter("remember-me")
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/user/toLogin?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
                .and()
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    // 在WebSecurityConfig类中添加内部类
    private static class SaveToSessionSuccessHandler implements AuthenticationSuccessHandler {
        private UserDetailsServiceImpl userDetailsService;

        public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
            this.userDetailsService = userDetailsService;
        }


        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                          HttpServletResponse response,
                                          Authentication authentication) throws IOException {
            try {
                // 获取认证成功的用户信息
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                System.out.println("Authentication successful for user: " + userDetails.getUsername());
                
                // 获取完整的用户信息并立即存入session
                UserDto userDto = userDetailsService.getUserByUsername(userDetails.getUsername());
                if (userDto != null) {
                    HttpSession session = request.getSession(true);
                    // 确保在这里就存入session
                    session.setAttribute("bUser", userDto);
                    System.out.println("User saved to session immediately after login: " + userDto);
                }
                
                // 重定向到首页
                response.sendRedirect(request.getContextPath() + "/");
                
            } catch (Exception e) {
                System.err.println("Error in authentication success handler: " + e.getMessage());
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/user/toLogin?error");
            }
        }
    }
}
