package com.am.modules.order.service;

import com.am.common.service.BaseService;
import com.am.modules.order.dao.OrderMapper;
import com.am.modules.order.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jws on 2017/4/25.
 */
@Service
@Transactional(readOnly = true)
public class OrderService extends BaseService{
    @Autowired
    private OrderMapper orderDao;

    public boolean saveOrder(Order order){
        try{
            orderDao.insert(order);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteByCodeID(String codeid){
        try{
            orderDao.deleteByCodeID(codeid);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Order findOrderByID(Integer id){
        return orderDao.selectByPrimaryKey(id);
    }

    public List<Order> findAll(){
        return orderDao.finAll();
    }

    public Order findByCodeID(String codeid){
        return orderDao.findByCodeID(codeid);
    }
}
