package com.am.modules.order.web;

import com.am.common.untils.toMap;
import com.am.modules.order.entity.Order;
import com.am.modules.order.service.OrderService;
import com.am.modules.user.entity.User;
import com.am.modules.user.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jws on 2017/4/25.
 */
@Controller
@RequestMapping(value = "order")
public class OrderControl {
    @Resource
    private OrderService orderService;
    @Resource
    private UserService userService;

    @RequestMapping(value = "findByID")
    public String findOrderByID(HttpServletRequest request, HttpServletResponse response, Model model){
        Order o;
        int id = Integer.valueOf(request.getParameter("id"));
        o = orderService.findOrderByID(id);
        if (o != null){
            Map<String,Object> map = toMap.beanToMap(o);
            String json = toMap.MapToJson(map);
            model.addAttribute("order",json);
        }
        return "/index";
    }

    @RequestMapping(value = "findAll")
    public String finOrderAll(HttpServletRequest request, HttpServletResponse response, Model model){
        List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();
        List<Order> orders = orderService.findAll();
        if (orders.get(0)!=null){
            Map<String,Object> map;
            for (Order order:orders){
                map = toMap.beanToMap(order);
                maps.add(map);
                map.clear();
            }
            model.addAttribute("maps",maps);
        }
        return "/index";
    }
    @RequestMapping(value = "index")
    public String getTotal(HttpServletRequest request, HttpServletResponse response, Model model){
        List<Order> orders = orderService.findAll();
        List<User> users = userService.findAll();
        float money = 0;
        int count = users.size();
        if (orders.get(0)!=null){
            for (Order order:orders){
                money += Float.parseFloat(order.getPay());
            }
        }
        Map<String ,Object> map = new HashMap<String,Object>();
        map.put("money",String.valueOf(money));
        map.put("money",String.valueOf(count));
        String json = toMap.MapToJson(map);
        model.addAttribute("maps",json);
        return "/index";
    }

}
