package com.am.modules.adminUser.service;

import com.am.common.service.BaseService;
import com.am.modules.adminUser.dao.AdminUserMapper;
import com.am.modules.adminUser.entity.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jws on 2017/4/19.
 */
@Service
@Transactional(readOnly = true)
public class AdminUserService extends BaseService {

    @Resource
    private AdminUserMapper adminUserDao;

    /*
    * @parm String codeid
    * 根据codeid查找用户
    * */
    public AdminUser findAdminUserByCodeId(String codeid){
       return adminUserDao.findByCodeId(codeid);
    }
    /*
    * 根据ID查找用户
    * */
    public AdminUser findByID(int id){
        return adminUserDao.selectByPrimaryKey(id);
    }

    public int updatebyID(AdminUser adminUser){
        return adminUserDao.updateByPrimaryKey(adminUser);
    }

    public int deleteByID(int id){
        return adminUserDao.deleteByID(id);
    }

    public boolean save(AdminUser adminUser){
        if (adminUserDao.insert(adminUser)!=0){
            return true;
        }else {
            return false;
        }
    }

    public List<AdminUser> findAll(){
        return adminUserDao.findAll();
    }
}
