package com.jiash.tgpproxy;

import java.io.*;

/**
 * @program: single
 * @Date: 2019/3/15 15:06
 * @Author: huangjp
 * @Description:
 * 一个ClassLoader对象，定义了由哪个ClassLoader来对生成的代理对象进行加载
 *
 * Java类加载器ClassLoader总结：
 *      https://www.cnblogs.com/doit8791/p/5820037.html
 *
 * --------------------------------------------------------------------------
 *
 * ClassLoader类加载机制&&JVM内存管理：
 *      https://www.cnblogs.com/cloudml/p/4713642.html
 *
 *      ClassLoader.loadClass(...) 是ClassLoader的入口点。当一个类没有指明用什么加载器加载的时候，
 *  JVM默认采用AppClassLoader加载器加载没有加载过的class，调用的方法的入口就是loadClass(...)。
 *      如果一个class被自定义的ClassLoader加载，那么JVM也会调用这个自定义的ClassLoader.loadClass(...)方法
 *  来加载class内部引用的一些别的class文件。
 *  重载这个方法，能实现自定义加载class的方式，抛弃双亲委托机制如果要实现自己的类加载器，只需继承ClassLoader，重写findClass方法。
 */
public class GPJisahClassLoader extends ClassLoader{


    File classPathFile;
    public GPJisahClassLoader(){
        this.classPathFile = new File(GPJisahClassLoader.class.getResource("").getPath());
    }

    /**
     * @param name
     * @return
     * @throws ClassNotFoundException
     *
     * 父类有那么多方法，为什么偏偏只重写findClass方法？
     *
     *      因为JDK已经在loadClass方法中帮我们实现了ClassLoader搜索类的算法，当在loadClass方法中搜索不到类时，loadClass方法就会调用findClass
     *      方法来搜索类，所以我们只需重写该方法即可。如没有特殊的要求，一般不建议重写loadClass搜索类的算法。
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String className = GPJisahClassLoader.class.getPackage().getName() + "." + name;
        if(classPathFile!=null){
            File classFile = new File(classPathFile,name.replace("\\.","/") + ".class");
            if(classFile.exists()){
                FileInputStream fis = null;
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                try {
                    fis = new FileInputStream(classFile);

                    byte[] buf = new byte[1024];
                    int len;

                    while((len=fis.read(buf))!=-1){
                        out.write(buf,0,len);
                    }

                    /**
                     * Class类没有public的构造方法，Class对象是在装载类时由JVM通过调用类装载器中的defineClass()方法自动构造的
                     * 这个方法在编写自定义classloader的时候非常重要，它能将class二进制内容转换成Class对象，如果不符合要求的会抛出各种异常
                     * 一个ClassLoader创建时如果没有指定parent，那么它的parent默认就是AppClassLoader
                     */
                    return defineClass(className,out.toByteArray(),0,out.size());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if(out!=null){
                            out.close();
                        }
                        if (fis!=null){
                            fis.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

}
