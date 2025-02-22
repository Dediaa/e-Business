package com.ch.ebusiness.service.before.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.ch.ebusiness.exception.IsClearCartException;
import com.ch.ebusiness.exception.NoCartException;
import com.ch.ebusiness.service.before.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ch.ebusiness.entity.Goods;
import com.ch.ebusiness.entity.Order;
import com.ch.ebusiness.repository.before.CartRepository;
import com.ch.ebusiness.repository.before.IndexRepository;
import com.ch.ebusiness.utils.MD5Util;
import com.ch.ebusiness.utils.MyUtil;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private IndexRepository indexRepository;

    @Override
    public String putCart(Goods goods, Model model, HttpSession session) {
        Integer uid = MyUtil.getUser(session).getUserId();
        //如果商品已在购物车，只更新购买数量
        if (cartRepository.isPutCart(uid, goods.getId()).size() > 0) {
            cartRepository.updateCart(uid, goods.getId(), goods.getBuyNumber());
        } else {//新增到购物车
            cartRepository.putCart(uid, goods.getId(), goods.getBuyNumber());
        }
        //跳转到查询购物车
        return "forward:/cart/selectCart";
    }

    @Override
    public String selectCart(Model model, HttpSession session, String act) throws NoCartException
    {
        List<Map<String, Object>> list = cartRepository.selectCart(MyUtil.getUser(session).getUserId());
        double sum = 0;
        for (Map<String, Object> map : list)
		{
            sum = sum + (Double) map.get("smallsum");
        }
        model.addAttribute("total", sum);
        model.addAttribute("cartlist", list);
        //广告区商品
//        model.addAttribute("advertisementGoods", indexRepository.selectAdvertisementGoods());
        //导航栏商品类型
        model.addAttribute("goodsType", indexRepository.selectGoodsType());
        if ("toCount".equals(act))
		{//去结算页面
            if(list.isEmpty())
            {
                throw new NoCartException("购物车为空, 请先添加商品");
            }
            return "user/count";
        }
        return "user/cart";
    }

    @Override
    public String focus(Model model, HttpSession session, Integer gid) {
        Integer uid = MyUtil.getUser(session).getUserId();
        List<Map<String, Object>> list = cartRepository.isFocus(uid, gid);
        //判断是否已收藏
        if (list.size() > 0)
		{
            return "no";
        } else
		{
            cartRepository.focus(uid, gid);
            return "ok";
        }
    }

    @Override
    public String deleteCart(HttpSession session, Integer gid)
	{
        Integer uid = MyUtil.getUser(session).getUserId();
        cartRepository.deleteAgoods(uid, gid);
        return "forward:/cart/selectCart";
    }

    @Override
    public String clearCart(HttpSession session) throws IsClearCartException
    {
        List<Map<String, Object>> list = cartRepository.selectCart(MyUtil.getUser(session).getUserId());
        if(list.isEmpty())
        {
            throw new IsClearCartException("购物车为空, 不能清空");
        }
        cartRepository.clear(MyUtil.getUser(session).getUserId());
        return "forward:/cart/selectCart";
    }

    @Override
    @Transactional
    public String submitOrder(Order order, Model model, HttpSession session)
    {
        order.setBusertable_id(MyUtil.getUser(session).getUserId());
        //生成订单
        cartRepository.addOrder(order);
        //生成订单详情
        cartRepository.addOrderDetail(order.getId(), MyUtil.getUser(session).getUserId());
        //减少商品库存
        List<Map<String, Object>> listGoods = cartRepository.selectGoodsShop(MyUtil.getUser(session).getUserId());
        for (Map<String, Object> map : listGoods)
        {
            cartRepository.updateStore(map);
        }
        //清空购物车
        cartRepository.clear(MyUtil.getUser(session).getUserId());
        model.addAttribute("order", order);
        return "user/pay";
    }

    @Override
    public String pay(Order order)
    {
        cartRepository.pay(order.getId());
        return "ok";
    }

    @Override
    public String myOder(Model model, HttpSession session) {
        //广告区商品
//        model.addAttribute("advertisementGoods", indexRepository.selectAdvertisementGoods());
        //导航栏商品类型
        model.addAttribute("goodsType", indexRepository.selectGoodsType());
        model.addAttribute("myOrder", cartRepository.myOrder(MyUtil.getUser(session).getUserId()));
        return "user/myOrder";
    }

    @Override
    public String orderDetail(Model model, Integer id)
    {
        //根据订单编号查询订单
        model.addAttribute("order", cartRepository.selectOrderByOrderId(id));
        model.addAttribute("orderDetail", cartRepository.orderDetail(id));
        System.out.println(cartRepository.orderDetail(id));
        System.out.println(model);
        return "user/orderDetail";
    }

    @Override
    public String updateUpwd(HttpSession session, String bpwd) {
        Integer uid = MyUtil.getUser(session).getUserId();
        cartRepository.updateUpwd(uid, MD5Util.MD5(bpwd));
        return "forward:/user/toLogin";
    }

}
