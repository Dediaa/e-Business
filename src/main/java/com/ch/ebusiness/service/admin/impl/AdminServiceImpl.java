package com.ch.ebusiness.service.admin.impl;

import com.ch.ebusiness.service.admin.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService
{

    @Override
    public String login()
    {
        return "forward:/goods/selectAllGoodsByPage?currentPage=1&act=select";
    }
}
