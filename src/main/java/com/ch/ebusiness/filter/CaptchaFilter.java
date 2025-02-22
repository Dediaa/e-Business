package com.ch.ebusiness.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CaptchaFilter extends AbstractAuthenticationProcessingFilter {

    private static final String FORM_CAPTCHA_KEY = "code";
    private static final String SESSION_CAPTCHA_KEY = "rand";
    private static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    private static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    public CaptchaFilter() {
        super(new AntPathRequestMatcher("/user/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // 验证码校验
        String captchaInForm = request.getParameter(FORM_CAPTCHA_KEY);
        String captchaInSession = (String) request.getSession().getAttribute(SESSION_CAPTCHA_KEY);

        if (captchaInForm == null || captchaInSession == null || !captchaInForm.equalsIgnoreCase(captchaInSession)) {
            throw new AuthenticationServiceException("验证码错误");
        }

        // 移除session中的验证码
        request.getSession().removeAttribute(SESSION_CAPTCHA_KEY);

        // 获取用户名和密码
        String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
        String password = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);

        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }

        username = username.trim();

        // 创建未认证的Authentication
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);

        // 设置详细信息
        setDetails(request, authRequest);

        // 执行认证
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}