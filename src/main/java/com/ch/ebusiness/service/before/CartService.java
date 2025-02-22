package com.ch.ebusiness.service.before;

import javax.servlet.http.HttpSession;

import com.ch.ebusiness.exception.IsClearCartException;
import com.ch.ebusiness.exception.NoCartException;
import org.springframework.ui.Model;

import com.ch.ebusiness.entity.Goods;
import com.ch.ebusiness.entity.Order;

public interface CartService {
    public String putCart(Goods goods, Model model, HttpSession session);

    public String focus(Model model, HttpSession session, Integer gid);

    public String selectCart(Model model, HttpSession session, String act) throws NoCartException;

    public String deleteCart(HttpSession session, Integer gid);

    public String clearCart(HttpSession session) throws IsClearCartException;

    public String submitOrder(Order order, Model model, HttpSession session);

    public String pay(Order order);


    public String myOder(Model model, HttpSession session);

    public String orderDetail(Model model, Integer id);

    public String updateUpwd(HttpSession session, String bpwd);
}
