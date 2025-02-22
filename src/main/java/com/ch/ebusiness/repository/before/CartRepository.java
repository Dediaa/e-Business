package com.ch.ebusiness.repository.before;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ch.ebusiness.entity.Order;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CartRepository {
    List<Map<String, Object>> isFocus(@Param("uid") Integer uid, @Param("gid") Integer gid);

    int focus(@Param("uid") Integer uid, @Param("gid") Integer gid);

    int putCart(@Param("uid") Integer uid,
                @Param("gid") Integer gid,
                @Param("bnum") Integer bnum);

    List<Map<String, Object>> isPutCart(@Param("uid") Integer uid, @Param("gid") Integer gid);

    int updateCart(@Param("uid") Integer uid,
                   @Param("gid") Integer gid,
                   @Param("bnum") Integer bnum);

    List<Map<String, Object>> selectCart(Integer uid);

    int deleteAgoods(@Param("uid") Integer uid, @Param("gid") Integer gid);

    int clear(Integer uid);

    int addOrder(Order order);

    int addOrderDetail(@Param("ordersn") Integer ordersn, @Param("uid") Integer uid);

    List<Map<String, Object>> selectGoodsShop(Integer uid);

    int updateStore(Map<String, Object> map);

    int pay(Integer ordersn);


    List<Map<String, Object>> myOrder(Integer uid);

    List<Map<String, Object>> orderDetail(Integer id);

    int updateUpwd(@Param("uid") Integer uid, @Param("bpwd") String bpwd);

    @Select("select * from orderbasetable where id=#{id}")
    Order selectOrderByOrderId(Integer id);
}
