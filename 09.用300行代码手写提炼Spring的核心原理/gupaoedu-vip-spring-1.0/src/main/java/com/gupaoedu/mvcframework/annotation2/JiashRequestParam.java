package com.gupaoedu.mvcframework.annotation2;

import java.lang.annotation.*;

/**
 * @program: gupaoedu-vip-spring
 * @Date: 2019/3/25 11:42
 * @Author: huangjp
 * @Description:
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JiashRequestParam {
    String value() default "";
}
