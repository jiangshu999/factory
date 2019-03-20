package com.jiash.delegate.mvc;

import com.jiash.delegate.controllers.MemberController;
import com.jiash.delegate.controllers.OrderController;
import com.jiash.delegate.controllers.SystemController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: single
 * @Date: 2019/3/13 20:50
 * @Author: huangjp
 * @Description:
 */
public class DispatcherServlet extends HttpServlet {


    private List<Handler> handlerMapping = new ArrayList<Handler>();

    public void init() throws ServletException{

        try {
            Class<?> clzz = MemberController.class;
            handlerMapping.add(new Handler()
                    .setController(clzz.newInstance())
                    .setMethod(clzz.getMethod("getMemberById",new Class[]{String.class}))
                    .setUrl("/web/getMemberById.json")
            );
        }catch (Exception e){
            throw new ServletException();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            doDispach(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDispach(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        /*String uri = req.getRequestURI();
        String mid = req.getParameter("mid");

        if("getMemberById".equals(uri)){
            new MemberController().getMemberById(mid);
        }else if("getOrderById".equals(uri)){
            new OrderController().getOrderById(mid);
        }else if("logout".equals(uri)){
            new SystemController().logout();
        }else {
            resp.getWriter().write("404 Not Found!!!");
        }*/


        /**************/
        /**************/

        //1、获取用户请求的url
        //   如果按照J2EE的标准、每个url对对应一个Serlvet，url由浏览器输入
        String uri = req.getRequestURI();

        //2、Servlet拿到url以后，要做权衡（要做判断，要做选择）
        //   根据用户请求的URL，去找到这个url对应的某一个java类的方法

        //3、通过拿到的URL去handlerMapping（我们把它认为是策略常量）
        Handler handler = null;

        for(Handler h:handlerMapping){
            if(h.getUrl().equals(uri)){
                handler = h;
                break;
            }
        }

        //4、将具体的任务分发给Method（通过反射去调用其对应的方法）
        Object obj = null;
        try {
            obj = handler.getMethod().invoke(handler.getController(),req.getParameter("mid"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //5、获取到Method执行的结果，通过Response返回出去
        //resp.getWriter().write("");

    }

    class Handler{

        private Object controller;
        private Method method;
        private String url;

        public Object getController() {
            return controller;
        }

        public Handler setController(Object controller) {
            this.controller = controller;
            return this;
        }

        public Method getMethod() {
            return method;
        }

        public Handler setMethod(Method method) {
            this.method = method;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Handler setUrl(String url) {
            this.url = url;
            return this;
        }
    }
}
