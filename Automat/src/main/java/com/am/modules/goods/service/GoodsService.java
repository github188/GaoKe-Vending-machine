package com.am.modules.goods.service;

import com.am.common.service.BaseService;
import com.am.modules.goods.dao.GoodsMapper;
import com.am.modules.goods.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jws on 2017/4/25.
 */
@Service
@Transactional(readOnly = true)
public class GoodsService extends BaseService{
    @Autowired
    private GoodsMapper goodsDao;

    public boolean saveGoods(Goods goods){
        try{
            goodsDao.insertSelective(goods);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public int delete(int id){
        return goodsDao.deleteByID(id);
    }

    public int update(Goods goods){
        return goodsDao.updateByPrimaryKey(goods);
    }

    public List<Goods> findAll(){
        return goodsDao.findAll();
    }

    public Goods findByID(int id){
        return goodsDao.findByID(id);
    }

}
