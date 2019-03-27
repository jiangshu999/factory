package com.gupaoedu.demo.service.impl;

import com.gupaoedu.demo.service.JiashIDemoSevice;
import com.gupaoedu.mvcframework.annotation2.JiashService;

/**
 * @program: gupaoedu-vip-spring
 * @Date: 2019/3/25 11:45
 * @Author: huangjp
 * @Description:
 */
@JiashService
public class JiashDemoService implements JiashIDemoSevice {
    @Override
    public String get(String name) {
        return "Hello,My name is " + name;
    }
}
