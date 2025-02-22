package com.ch.ebusiness.service.before;

import com.ch.ebusiness.entity.GoodsType;
import org.springframework.ui.Model;

import java.util.List;

public interface IndexService {
	public String index(Model model, Integer tid);
	public String goodsDetail(Model model, Integer id);
	public String search(Model model, String mykey);

	List<GoodsType> selectGoodsType();
}
