package com.gupaoedu.mvcframework.annotation2;

import java.lang.annotation.*;

/**
 * @program: gupaoedu-vip-spring
 * @Date: 2019/3/25 11:38
 * @Author: huangjp
 * @Description:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JiashController {
    String value() default "";
}
