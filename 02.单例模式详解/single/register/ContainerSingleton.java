package com.jiash.single.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: single
 * @Date: 2019/3/28 15:11
 * @Author: huangjp
 * @Description:
 */
public class ContainerSingleton {

    private static Map<String,Object> ioc = new ConcurrentHashMap<>();

    private ContainerSingleton(){}

    public static Object getInstance(String className){
        synchronized (ioc){
            if(!ioc.containsKey(className)){
                Object instance = null;
                try {
                    instance = Class.forName(className);
                    ioc.put(className,instance);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return instance;
            }
            return ioc.get(className);
        }
    }
}
