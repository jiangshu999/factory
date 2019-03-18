package com.jiash.tgpproxy;

/**
 * @program: single
 * @Date: 2019/3/15 17:03
 * @Author: huangjp
 * @Description:
 */
public class TestJiashProxy {

    public static void main(String[] args) {
        Class[] cc = Girl.class.getInterfaces();
        /*System.out.println(GPTProxy.generateStr(Girl.class.getInterfaces()));*/
        /*GPTProxy.newProxyInstance(new GPJisahClassLoader(), cc, new GPInvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });*/

        try {
            Person p = (Person) new GPJiashMeipo().getInstance(new Girl());
            p.findLove();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
