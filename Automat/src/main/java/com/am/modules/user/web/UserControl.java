package com.am.modules.user.web;

import com.am.modules.user.entity.User;
import com.am.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by jws on 2017/4/23.
 */
@Controller
@RequestMapping(value = "weiChat")
public class UserControl {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "save",method = RequestMethod.POST)
    public String addUser(HttpServletRequest request, HttpServletResponse response, Model model){
        String openid = request.getParameter("openid");
        String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phoneNumber");
        int vip = Integer.valueOf(request.getParameter("vip"));
        User u = userService.findByOpenid(openid);
        if (u!=null){
            u.setName(name);
            u.setPhonenumber(phoneNumber);
            u.setVip(vip);
            int i = userService.updateByID(u);
            return "{update:"+i+"}";
        }else{
            u.setOppenid(openid);
            u.setName(name);
            u.setPhonenumber(phoneNumber);
            u.setVip(vip);
            boolean t = userService.save(u);
            return "{add:"+t+"}";
        }
    }

    @RequestMapping(value = "del",method = RequestMethod.POST)
    public String deleteUser(HttpServletRequest request, HttpServletResponse response, Model model){
        String openid = request.getParameter("openid");
        User u = userService.findByOpenid(openid);
        if (u!=null){
            int i = userService.deleteByOpenid(u.getOppenid());
            return "{delete:"+i+"}";
        }else {
            return "{delete:fail}";
        }
    }

    @RequestMapping(value = "isVIP",method = RequestMethod.POST)
    public String checkVIP(HttpServletRequest request, HttpServletResponse response, Model model){
        String openid = request.getParameter("openid");
        List<User> users = userService.findVIP();
        for (User user:users){
            if (user.getOppenid().equals(openid)){
                return "{vip:true}";
            }
        }
        return "{vip:false}";
    }



}
