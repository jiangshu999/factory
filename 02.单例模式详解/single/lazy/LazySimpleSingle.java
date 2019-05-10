package com.jiash.single.lazy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @program: single
 * @Date: 2019/3/24 10:47
 * @Author: huangjp
 * @Description:
 * 懒汉式单例
 */
public class LazySimpleSingle {

    private static LazySimpleSingle instance = null;

    private LazySimpleSingle() {
        //加上判断，是为了防止反射的暴力破坏单例模式
        if(LazyHolder.LAZY_INSTANCE!=null){
            throw new RuntimeException("不允许创建多个实例");
        }
    }

    /**
     * 有线程安全的问题
     * @return
     */
    public synchronized static LazySimpleSingle getInstance() {
        if (instance == null) {
            instance = new LazySimpleSingle();
        }
        return instance;
    }

    /**
     * 无线程安全，在线程数据比较多时，如CPU分配压力上升，会导致大批线程阻塞，从而使程序运行性能大幅下降
     * @return
     */
    public synchronized static LazySimpleSingle getInstance2(){
        if (instance==null)
            instance = new LazySimpleSingle();
        return instance;
    }

    /**
     * 双重检查锁，虽然一定程度上解决了性能问题，但终究加上synchronzied，对程序性能还是存在一定的影响
     * @return
     */
    public static LazySimpleSingle getInstance3(){
        if (instance == null) {
            synchronized (LazySimpleSingle.class){
                if (instance==null)
                    instance = new LazySimpleSingle();
            }
        }
        return instance;
    }

    /**
     * 这个内部类的单例模式，即避免了饿汉式单例的内存资源浪费，也兼顾synchronzied的性能问题
     * 内部类是在方法调用前先初始化好，世界巧妙的避免的线程安全问题
     *
     * static final 保存这个方法不被重载、重写
     * @return
     */
    public static final LazySimpleSingle getInstance4(){
        return LazyHolder.LAZY_INSTANCE;
    }

    private static class LazyHolder{
        private static final LazySimpleSingle LAZY_INSTANCE = new LazySimpleSingle();
    }


    public static void main(String[] args) {
        /*Thread t1 = new Thread(new Exector());
        Thread t2 = new Thread(new Exector());
        t1.start();
        t2.start();
        System.out.println("end");*/

        //上面代码里，私有构造器，只有private修饰后，没有做任何处理，而反射是可以调用私有的构造器，再调用
        //newInstance()方法，就会生成两个不同的单例
        try {
            Class<?> clazz = LazySimpleSingle.class;
            Constructor cs = clazz.getDeclaredConstructor(null);
            cs.setAccessible(true);
            Object o1 = cs.newInstance();
            Object o2 = cs.newInstance();
            System.out.println(o1 == o2);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

class Exector implements Runnable {

    @Override
    public void run() {
        //LazySimpleSingle instance = LazySimpleSingle.getInstance();
        LazySimpleSingle instance = LazySimpleSingle.getInstance2();
        System.out.println(Thread.currentThread().getName() + ": " + instance);
    }
}
