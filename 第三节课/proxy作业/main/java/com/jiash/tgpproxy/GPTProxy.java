package com.jiash.tgpproxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @program: single
 * @Date: 2019/3/15 15:53
 * @Author: huangjp
 * @Description:
 */
public class GPTProxy{


    /**
     * loader:类加载器
     * interfaces:目标对象实现的接口
     * h:InvocationHandler的实现类
     */
    public static Object newProxyInstance(GPJisahClassLoader loader,
                                          Class<?>[] interfaces,
                                          GPInvocationHandler h) {

        try {

            //1、动态生成源代码
            String str = generateStr(interfaces);

            //2、Java文件输出到磁盘
            String filePath = GPTProxy.class.getResource("").getPath();
            File file = new File(filePath + "$Proxy0.java");
            FileWriter fw = new FileWriter(file);
            fw.write(str);
            fw.close();

            //3、把生成的.java文件编译成.class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null,null,null);
            Iterable it = manager.getJavaFileObjects(file);
            JavaCompiler.CompilationTask task = compiler.getTask(null,manager,null,null,null,it);
            task.call();
            manager.close();

            /**
             * 关键实现：生成代理对象的class对象
             *
             */
            //4、编译生成的.class文件加载到JVM中来
            Class proxyClass = loader.findClass("$Proxy0");

            //5、返回字节码重组以后的新的代理对象
            Constructor c = proxyClass.getConstructor(GPInvocationHandler.class);
            return c.newInstance(h);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateStr(Class<?>[] interfaces) {
        String ln = "\n\r";
        StringBuffer str = new StringBuffer();
        str.append("package com.jiash.tgpproxy;").append(ln);
        str.append("import java.lang.reflect.Method;").append(ln);
        str.append("public class $Proxy0 implements ").append(interfaces[0].getName()).append(" {").append(ln);
        str.append("private GPInvocationHandler h;").append(ln);
        str.append("public $Proxy0(GPInvocationHandler h){").append(ln);
        str.append("this.h = h;").append(ln);
        str.append("}").append(ln);

        for(Method m:interfaces[0].getMethods()){
            str.append("public ").append(m.getReturnType()).append(" ").append(m.getName()).append("(){").append(ln);
            str.append("try{").append(ln);

            str.append("Method m = ").append(interfaces[0].getName()).append(".class.getMethod(\"").append(m.getName()).append("\",new Class[]{});").append(ln);
            str.append("this.h.invoke(this,m,null);").append(ln);

            str.append("}").append(ln);
            str.append("catch(Throwable e){").append(ln);
            str.append("e.printStackTrace();").append(ln);
            str.append("}").append(ln);
            str.append("}").append(ln);
        }

        str.append("}").append(ln);

        return str.toString();
    }
}
