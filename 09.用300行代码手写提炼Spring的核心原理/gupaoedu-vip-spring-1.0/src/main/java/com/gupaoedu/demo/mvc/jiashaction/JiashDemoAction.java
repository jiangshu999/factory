package com.gupaoedu.demo.mvc.jiashaction;

import com.gupaoedu.demo.service.JiashIDemoSevice;
import com.gupaoedu.mvcframework.annotation2.JiashAutowired;
import com.gupaoedu.mvcframework.annotation2.JiashController;
import com.gupaoedu.mvcframework.annotation2.JiashRequestMapping;
import com.gupaoedu.mvcframework.annotation2.JiashRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: gupaoedu-vip-spring
 * @Date: 2019/3/25 11:47
 * @Author: huangjp
 * @Description:
 */
@JiashController
@JiashRequestMapping("/JiashDemo")
public class JiashDemoAction {

    @JiashAutowired
    private JiashIDemoSevice sevice;

    @JiashRequestMapping("/query")
    public void query(HttpServletRequest req, HttpServletResponse resp,
                      @JiashRequestParam("name") String name){
        String result = "Hello,my name's " + name;

        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @JiashRequestMapping("/add")
    public void add(HttpServletRequest req,HttpServletResponse resp,
                    @JiashRequestParam("a") Integer a,@JiashRequestParam("b") Integer b){
        try {
            resp.getWriter().write("a + b = " + (a + b));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @JiashRequestMapping("/remove")
    public void remove(HttpServletRequest req,HttpServletResponse resp,
                       @JiashRequestParam("sex") String sex,
                       @JiashRequestParam("age") Integer age, @JiashRequestParam("zhuji") String zhuji,
                       @JiashRequestParam("id") String id){
        try {
            resp.getWriter().write("a + b = " + (sex + age));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
