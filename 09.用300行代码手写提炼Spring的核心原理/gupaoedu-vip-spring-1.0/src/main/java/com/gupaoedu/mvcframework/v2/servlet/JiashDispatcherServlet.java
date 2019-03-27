package com.gupaoedu.mvcframework.v2.servlet;

import com.gupaoedu.mvcframework.annotation2.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: gupaoedu-vip-spring
 * @Date: 2019/3/25 17:17
 * @Author: huangjp
 * @Description:
 */
public class JiashDispatcherServlet extends HttpServlet {

    //保存appliction.properties配置文件中的内容
    private Properties contextConfig = new Properties();

    //保存扫描的所有的类名
    private List<String> classNames = new ArrayList<String>();

    //传说中的IOC容器，我们来揭开它的神秘面纱
    //为了简化程序，暂时不考虑ConcurrentHashMap
    //主要还是关注设计思想和原理
    //存放的是 对象名  对象实例 @JiashAutowired  private JiashIDemoSevice sevice;
    private Map<String, Object> ioc = new HashMap<String, Object>();

    //保存url和Method的对应关系
    /*private Map<String, Method> handlerMapping = new HashMap<String, Method>();*/
    private List<Handler> handlerMapping = new ArrayList<Handler>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    //用了委派模式，委派模式的具体逻辑在 doDispatch()方法中
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //6、调用，运行阶段
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //doDispatch()虽然完成了动态委派并反射调用，但对 url 参数处理还是静态代码
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        /*//绝对路径
        String url = req.getRequestURI();
        //处理成相对路径
        String contextPath = req.getContextPath();

        url = url.replaceAll(contextPath, "").replaceAll("/+", "/");

        if (!this.handlerMapping.containsKey(url)) {
            resp.getWriter().write("404 NOT FOUND!!!");
            return;
        }

        Method method = handlerMapping.get(url);
        //从reqest中拿到url传过来的参数
        Map<String, String[]> params = req.getParameterMap();

        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        method.invoke(ioc.get(beanName), new Object[]{req, resp, params.get("name")[0]});
        */

        Handler handler = getHandler(req);

        if(handler==null) return;

        Class<?>[] paramTypes = handler.getParamTypes();
        Object[] paramValues = new Object[paramTypes.length];

        Map<String,String[]> params = req.getParameterMap();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {

            if(!handler.paramIndexMapping.containsKey(entry.getKey())) continue;

            String value = Arrays.toString(entry.getValue()).replaceAll("\\[|]]","").replaceAll("\\s",",");
            int index = handler.paramIndexMapping.get(entry.getKey());
            paramValues[index] = convert(paramTypes[index],value);
        }

        if(handler.paramIndexMapping.containsKey(HttpServletRequest.class)){
            int reqIndex = handler.paramIndexMapping.get(HttpServletRequest.class);
            paramValues[reqIndex] = req;
        }

        if(handler.paramIndexMapping.containsKey(HttpServletResponse.class)){
            int respIndex = handler.paramIndexMapping.get(HttpServletResponse.class);
            paramValues[respIndex] = resp;
        }

        Object returnValue = handler.method.invoke(handler.controller,paramValues);
        if(returnValue==null || returnValue instanceof Void) return;
        resp.getWriter().write(returnValue.toString());
    }

    private Object convert(Class<?> paramType,String paramValue){
        if(Integer.class == paramType){
            return Integer.valueOf(paramValue);
        }else if(String.class == paramType){
            return String.valueOf(paramValue);
        }
        return paramValue;
    }

    private Handler getHandler(HttpServletRequest req) {
        if(handlerMapping.isEmpty()) return null;

        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath,"").replaceAll("/+","/");
        for (Handler handler:handlerMapping){
            Matcher matcher = handler.getPattern().matcher(url);
            if(!matcher.matches()) continue;
            return handler;
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1、加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        //2、扫描相关类
        doScanner(contextConfig.getProperty("scanPackage"));

        //3、初始化扫描到的类，并把它们放入到IOC容器里
        doInstance();

        //4、完成依赖注入
        doAutowired();

        //5、初始化好HandlerMapping
        initHandlerMapping();

        System.out.println("Jiash Spring MVC frameWork init.");
    }

    //初始化url和Method的一对一对应关系  handlerMapping 就是策略模式的应用案例
    private void initHandlerMapping() {
        if (ioc.isEmpty()) return;
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(JiashController.class)) continue;

            String baseUrl = "";
            if (clazz.isAnnotationPresent(JiashRequestMapping.class)) {
                JiashRequestMapping jiashRequestMapping = clazz.getAnnotation(JiashRequestMapping.class);
                baseUrl = jiashRequestMapping.value();
            }

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(JiashRequestMapping.class)) continue;
                JiashRequestMapping jiashRequestMapping = method.getAnnotation(JiashRequestMapping.class);
                String url = (baseUrl + "/" + jiashRequestMapping.value()).replaceAll("/+", "/");
                Pattern pattern = Pattern.compile(url);
                //handlerMapping.put(url, method);
                handlerMapping.add(new Handler(pattern,entry.getValue(),method));
                System.out.println("Mapped :" + url + "," + method);
            }
        }
    }

    //自动依赖注入
    private void doAutowired() {
        if (ioc.isEmpty()) return;

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(JiashAutowired.class)) continue;
                JiashAutowired jiashAutowired = field.getAnnotation(JiashAutowired.class);
                String beanName = jiashAutowired.value().trim();
                if (beanName.equals("")) {
                    beanName = field.getType().getName();
                }

                field.setAccessible(true);
                try {
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //初始化，为DI做准备 doInstance()方法就是工厂模式的具体实现
    //IOC 容器就是注册时单例的具体案例
    private void doInstance() {
        //classNames 存放的是： com.gupaoedu.demo.mvc.action.DemoAction
        if (classNames.isEmpty()) return;

        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(JiashController.class)) {
                    Object instance = clazz.newInstance();
                    //beanName的值：jiashDemoAction
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName, instance);
                } else if (clazz.isAnnotationPresent(JiashService.class)) {
                    //1、自定义的beanName
                    JiashService jiashService = clazz.getAnnotation(JiashService.class);
                    String beanName = jiashService.value();

                    //2、默认类名首字母小写
                    if (beanName.equals("")) {
                        beanName = toLowerFirstCase(clazz.getSimpleName());
                    }
                    Object instance = clazz.newInstance();
                    ioc.put(beanName, instance);

                    //3、根据类型自动赋值,投机取巧的方式
                    for (Class<?> iter : clazz.getInterfaces()) {
                        if (ioc.containsKey(iter.getName())) {
                            throw new Exception("The “" + iter.getName() + "” is exists!!!");
                        }
                        //把接口的类型直接当成key了
                        ioc.put(iter.getName(), instance);
                    }
                } else continue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //扫描出相关的类
    private void doScanner(String scanPackage) {
        //scanPackage = com.gupaoedu.demo ，存储的是包路径
        //转换为文件路径，实际上就是把.替换为/就OK了
        //classpath
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File file = new File(url.getFile());
        for (File classFile : file.listFiles()) {
            if (classFile.isDirectory()) doScanner(scanPackage + "." + classFile.getName());
            else {
                if (!classFile.getName().endsWith(".class")) continue;
                else {
                    //className: com.gupaoedu.demo.mvc.action.DemoAction
                    String className = scanPackage + "." + classFile.getName().replaceAll(".class", "");
                    classNames.add(className);
                }

            }
        }
    }

    //加载配置文件
    private void doLoadConfig(String contextConfigLocation) {
        //直接从类路径下找到Spring主配置文件所在的路径
        //并且将其读取出来放到Properties对象中
        //相对于scanPackage=com.gupaoedu.demo 从文件中保存到了内存中
        InputStream is = null;

        try {
            is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
            contextConfig.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String toLowerFirstCase(String simpleClassName) {
        char[] chars = simpleClassName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    //保存一个url和一个Method的关系
    public class Handler {
        private Pattern pattern;
        private Object controller;
        private Method method;
        private Class<?>[] paramTypes;

        //参数的名字作为key,参数的顺序，位置作为值
        private Map<String, Integer> paramIndexMapping;

        public Handler(Pattern pattern, Object controller, Method method) {
            this.pattern = pattern;
            this.controller = controller;
            this.method = method;

            paramTypes = method.getParameterTypes();
            paramIndexMapping = new HashMap<String, Integer>();

            putParamIndexMapping(method);
        }

        //参数的名字作为key,参数的顺序，位置作为值
        private void putParamIndexMapping(Method method) {
            //提取方法中加了注解的参数
            Annotation[][] annotations = method.getParameterAnnotations();
            for (int i=0;i<annotations.length;i++) {
                for (Annotation annotation : annotations[i]) {
                    if(annotation instanceof JiashRequestParam){
                        String paramName = ((JiashRequestParam)annotation).value();
                        if(!paramName.trim().equals("")){
                            paramIndexMapping.put(paramName,i);
                        }
                    }
                }
            }

            //提取request与response方法的参数
            Class<?>[] paramTypes = method.getParameterTypes();
            for(int i=0;i<paramTypes.length;i++){
                Class paramType = paramTypes[i];
                if(paramType == HttpServletRequest.class ||
                        paramType == HttpServletResponse.class){
                    paramIndexMapping.put(paramType.getName(),i);
                }
            }
        }

        public Pattern getPattern() {
            return pattern;
        }

        public Object getController() {
            return controller;
        }

        public Method getMethod() {
            return method;
        }

        public Class<?>[] getParamTypes() {
            return paramTypes;
        }
    }
}
