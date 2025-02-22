package com.ch.ebusiness.controller.before;

import com.ch.ebusiness.entity.Goods;
import com.ch.ebusiness.service.admin.GoodsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ch.ebusiness.service.before.IndexService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;

    @Autowired
    private GoodsService goodsService;


    @RequestMapping("/")
    public String index(Model model, 
                       Integer tid,
                       @RequestParam(defaultValue = "1") Integer currentPage,
                       @RequestParam(defaultValue = "24") Integer size)
    {
        int offset = (currentPage - 1) * size;
        List<Goods> goods = goodsService.getGoodsByPageAndTid(offset, size, tid);
        long totalItems = goodsService.countGoodsByTid(tid);
        int totalPages = (int) Math.ceil(totalItems * 1.0 / size);

        model.addAttribute("goods", goods);
        model.addAttribute("goodsType", indexService.selectGoodsType());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("tid", tid);
        System.out.println("Index controller accessed with path: " + model);
        return "user/index";
    }

    @Cacheable(value = "goodsDetail", key = "#id")
    @RequestMapping("/goodsDetail")
    public String goodsDetail(Model model, Integer id) {
        return indexService.goodsDetail(model, id);
    }

    @Cacheable(value = "searchResults", key = "#mykey")
    @RequestMapping("/search")
    public String search(Model model, String mykey) {
        return indexService.search(model, mykey);
    }
}
