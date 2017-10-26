package com.am.modules.repertory.service;

import com.am.common.service.BaseService;
import com.am.modules.repertory.dao.RepertoryMapper;
import com.am.modules.repertory.entity.Repertory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jws on 2017/4/26.
 */
@Service
@Transactional(readOnly = true)
public class RepertoryService extends BaseService{

    @Autowired
    private RepertoryMapper RepertoryDao;

    public int saveRepertory(Repertory repertory){
        return RepertoryDao.insertSelective(repertory);
    }


}
