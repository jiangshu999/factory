package com.jiash.tgpproxy;

import java.lang.reflect.Method;

/**
 * @program: single
 * @Date: 2019/3/15 15:05
 * @Author: huangjp
 * @Description:
 */
public interface GPInvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}
