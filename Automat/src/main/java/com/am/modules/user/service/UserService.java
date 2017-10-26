package com.am.modules.user.service;

import com.am.common.service.BaseService;
import com.am.modules.user.dao.UserMapper;
import com.am.modules.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jws on 2017/4/23.
 */
@Service
@Transactional(readOnly = true)
public class UserService extends BaseService{
    @Resource
    private UserMapper userDao;

    public boolean save(User user){
        try{
            userDao.insert(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public User findByID(int id){
        return userDao.selectByPrimaryKey(id);
    }

    public User findByOpenid(String openid){
        return userDao.findByOpenid(openid);
    }

    public int deleteByOpenid(String openid){
        return userDao.deleteByOpenid(openid);
    }

    public List<User> findVIP(){
        return userDao.findVIP();
    }

    public List<User> findAll(){
        return userDao.findAll();
    }

    public int updateByID(User user){
        return userDao.updateByPrimaryKey(user);
    }

}
