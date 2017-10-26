package com.am.modules.adminUser.web;

import com.am.modules.adminUser.entity.AdminUser;
import com.am.modules.adminUser.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jws on 2017/4/19.
 */
@Controller
@RequestMapping(value = "user")
public class AdminUserControl {
    @Resource
    private AdminUserService adminUserService;

    @RequestMapping(value = "login",method= RequestMethod.POST)
    public String LoginAUser(HttpServletRequest request, HttpServletResponse response, Model model){
         String codeid = request.getParameter("codeid");
         String password = request.getParameter("password");
         AdminUser adminUser = adminUserService.findAdminUserByCodeId(codeid);
         if (adminUser!=null){
             if (adminUser.getPassword().equals(password)){
                 return"/index";
             }else{
                 return "{login:password error}";
             }
         }
         return "{login:no user}";
    }
    @ResponseBody
    @RequestMapping(value = "updatePw",method= RequestMethod.POST)
    public String updateUser(HttpServletRequest request, HttpServletResponse response, Model model){
        int id = Integer.getInteger(request.getParameter("id"));
        String password = request.getParameter("password");
        AdminUser adminUser = adminUserService.findByID(id);
        adminUser.setPassword(password);
        adminUserService.updatebyID(adminUser);
        return "{update:success}";
    }


}
