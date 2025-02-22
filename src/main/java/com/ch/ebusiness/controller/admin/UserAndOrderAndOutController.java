package com.ch.ebusiness.controller.admin;

import com.ch.ebusiness.controller.before.BeforeBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.ebusiness.service.admin.UserAndOrderAndOutService;

@Controller
public class UserAndOrderAndOutController extends BeforeBaseController {
    @Autowired
    private UserAndOrderAndOutService userAndOrderAndOutService;

    @RequestMapping("/selectUser")
    public String selectUser(Model model, int currentPage) {
        return userAndOrderAndOutService.selectUser(model, currentPage);
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(Model model, int id) {
        return userAndOrderAndOutService.deleteUser(model, id);
    }

    @RequestMapping("/selectOrder")
    public String selectOrder(Model model, int currentPage)
    {
        return userAndOrderAndOutService.selectOrder(model, currentPage);
    }
}
