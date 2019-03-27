package com.gupaoedu.mvcframework.annotation2;

import java.lang.annotation.*;

/**
 * @program: gupaoedu-vip-spring
 * @Date: 2019/3/25 11:41
 * @Author: huangjp
 * @Description:
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JiashRequestMapping {
    String value() default "";
}


