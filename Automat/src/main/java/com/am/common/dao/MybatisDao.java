package com.am.common.dao;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by jws on 2017/4/20.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MybatisDao {
    String value() default "";
}
