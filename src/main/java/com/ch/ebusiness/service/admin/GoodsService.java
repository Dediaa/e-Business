package com.ch.ebusiness.service.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import com.ch.ebusiness.entity.Goods;

public interface GoodsService {
    String selectAllGoodsByPage(Model model, int currentPage, String act);

    String addGoods(Goods goods, HttpServletRequest request, String act) throws IllegalStateException, IOException;

    String toAddGoods(Goods goods, Model model);

    String detail(Model model, Integer id, String act);

    String delete(Integer id);

    List<Goods> getGoodsByPageAndTid(int offset, int size, Integer tid);

    long countGoodsByTid(Integer tid);
}
