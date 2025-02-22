package com.ch.ebusiness.repository.before;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ch.ebusiness.entity.Goods;
import com.ch.ebusiness.entity.GoodsType;

@Mapper
public interface IndexRepository {

    List<GoodsType> selectGoodsType();

    List<Goods> selectRecommendGoods(Integer tid);

    List<Goods> selectLastedGoods(Integer tid);

    Goods selectAGoods(Integer id);

    List<Goods> search(String mykey);

	List<Goods> selectGoods(Integer tid);
}
