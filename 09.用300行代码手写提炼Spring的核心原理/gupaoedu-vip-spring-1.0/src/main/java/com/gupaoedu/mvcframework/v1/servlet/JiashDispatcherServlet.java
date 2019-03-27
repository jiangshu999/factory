package com.gupaoedu.mvcframework.v1.servlet;

import com.gupaoedu.mvcframework.annotation.GPAutowired;
import com.gupaoedu.mvcframework.annotation.GPController;
import com.gupaoedu.mvcframework.annotation.GPRequestMapping;
import com.gupaoedu.mvcframework.annotation.GPService;
import com.gupaoedu.mvcframework.annotation2.JiashAutowired;
import com.gupaoedu.mvcframework.annotation2.JiashController;
import com.gupaoedu.mvcframework.annotation2.JiashRequestMapping;
import com.gupaoedu.mvcframework.annotation2.JiashService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @program: gupaoedu-vip-spring
 * @Date: 2019/3/25 13:58
 * @Author: huangjp
 * @Description:
 */
public class JiashDispatcherServlet extends HttpServlet {

    private Map<String,Object> mapping = new HashMap<String, Object>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath,"").replaceAll("/+","/");

        if(!mapping.containsKey(url)){
            resp.getWriter().write("404 NOT FOUND!");
            return;
        }

        Method method = (Method) this.mapping.get(url);
        Map<String,String[]> params = req.getParameterMap();
        method.invoke(mapping.get(method.getDeclaringClass().getName()),new Object[]{req,resp,params.get("name")[0]});
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        InputStream is = null;


        try{
            Properties configContext = new Properties();
            is = this.getClass().getClassLoader().getResourceAsStream(config.getInitParameter("contextConfigLocation"));
            configContext.load(is);
            String scanPackage = configContext.getProperty("scanPackage");

            doScanner(scanPackage);

            for(String className:mapping.keySet()){
                if(!className.contains(".")) continue;
                Class<?> clazz = Class.forName(className);
                if(clazz.isAnnotationPresent(JiashController.class)){
                    mapping.put(className,clazz.newInstance());

                    String baseUrl = "";
                    if(clazz.isAnnotationPresent(JiashRequestMapping.class)){
                        JiashRequestMapping requestMapping = clazz.getAnnotation(JiashRequestMapping.class);
                        baseUrl = requestMapping.value();
                    }

                    Method[] methods = clazz.getMethods();
                    for(Method method:methods){
                        if(!method.isAnnotationPresent(JiashRequestMapping.class)) continue;
                        JiashRequestMapping requestMapping = method.getAnnotation(JiashRequestMapping.class);
                        String url = (baseUrl + "/" + requestMapping.value()).replaceAll("/+","/");
                        mapping.put(url,method);
                        System.out.println("Mapping: " + url + "," + method);
                    }
                }else if(clazz.isAnnotationPresent(JiashService.class)){
                    JiashService jiashService = clazz.getAnnotation(JiashService.class);
                    String baseName = jiashService.value();
                    if(baseName.equals("")) baseName = clazz.getName();
                    Object instance = clazz.newInstance();
                    mapping.put(baseName,instance);
                    for(Class interf:clazz.getInterfaces()){
                        mapping.put(interf.getName(),instance);
                    }
                }else continue;
            }

            for(Object object:mapping.values()){
                if(object==null) continue;
                Class clz = object.getClass();
                if(clz.isAnnotationPresent(JiashController.class)){
                    Field[] fields = clz.getDeclaredFields();
                    for(Field field:fields){
                        if(!field.isAnnotationPresent(JiashAutowired.class)) continue;
                        JiashAutowired jiashAutowired = field.getAnnotation(JiashAutowired.class);
                        String baseName = jiashAutowired.value();
                        if(baseName.equals("")) baseName = field.getType().getName();
                        field.setAccessible(true);
                        field.set(mapping.get(clz.getName()),mapping.get(baseName));
                    }
                }
            }
        } catch (Exception e) {
        }finally {
            if(is != null){
                try {is.close();} catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.","/"));
        File classDir = new File(url.getFile());
        for(File file:classDir.listFiles()){
            if(file.isDirectory()){
                doScanner(scanPackage + "." + file.getName());
            }else{
                if(!file.getName().endsWith(".class")) continue;
                String className = scanPackage + "." + file.getName().replace(".class","");
                mapping.put(className,null);
            }
        }

    }
}
