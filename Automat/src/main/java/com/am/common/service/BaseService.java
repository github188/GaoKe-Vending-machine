package com.am.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jws on 2017/4/22.
 */
@Transactional(readOnly = true)
public abstract class BaseService {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
