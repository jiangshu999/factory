package com.gupaoedu.mvcframework.annotation2;

import java.lang.annotation.*;

/**
 * @program: gupaoedu-vip-spring
 * @Date: 2019/3/25 11:35
 * @Author: huangjp
 * @Description:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JiashAutowired {
    String value() default "";
}
