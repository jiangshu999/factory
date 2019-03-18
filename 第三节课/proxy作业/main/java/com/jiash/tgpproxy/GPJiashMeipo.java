package com.jiash.tgpproxy;

import java.lang.reflect.Method;

/**
 * @program: single
 * @Date: 2019/3/15 22:04
 * @Author: huangjp
 * @Description:
 */
public class GPJiashMeipo implements GPInvocationHandler {

    private Object target;

    public Object getInstance(Person person) throws Exception{
        this.target = person;
        Class<?> clazz = this.target.getClass();
        return GPTProxy.newProxyInstance(new GPJisahClassLoader(),clazz.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object obj = method.invoke(target,null);
        System.out.println("after");
        return obj;
    }
}
