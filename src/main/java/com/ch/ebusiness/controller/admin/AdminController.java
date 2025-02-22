package com.ch.ebusiness.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ch.ebusiness.service.admin.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/toLogin")
    public String toLogin()
	{
        return adminService.login();
    }

}
