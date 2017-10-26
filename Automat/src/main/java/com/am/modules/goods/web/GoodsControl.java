package com.am.modules.goods.web;

import com.am.modules.goods.entity.Goods;
import com.am.modules.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jws on 2017/4/25.
 */
@Controller
@RequestMapping(value = "goods")
public class GoodsControl {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "saveGoods",method = RequestMethod.POST)
    public String saveGoods(Goods goods, HttpServletRequest request, HttpServletResponse response, Model model) {
        Goods g = goodsService.findByID(goods.getId());
        if (g!=null){
            goodsService.update(goods);
            return "{update:success}";
        }else {
            goodsService.saveGoods(goods);
            return "{save:success}";
        }
    }

    @RequestMapping(value = "getPrice")
    public String getPrice(HttpServletRequest request, HttpServletResponse response, Model model){
        String ID = request.getParameter("id");
        Goods goods = goodsService.findByID(Integer.valueOf(ID));
        if (goods!=null){
            return "{price:"+goods.getMoney()+"}";
        }else {
            return "{find:fail}";
        }
    }

    @RequestMapping(value = "deleteGoods")
    public String deleteGoods(HttpServletRequest request, HttpServletResponse response, Model model){
        String id = request.getParameter("id");
        try {
            goodsService.delete(Integer.valueOf(id));
            return "{delete:success}";
        }catch (Exception e){
            return "{delete:fail}";
        }
    }

}
