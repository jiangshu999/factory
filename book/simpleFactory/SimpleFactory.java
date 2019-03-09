package com.company.jiash.book.simpleFactory;

/**
 * @program: sjms_test
 * @Date: 2019/3/8 21:17
 * @Author: huangjp
 * @Description:
 */
public class SimpleFactory {

    public Car getCarByClass(Class<? extends Car> clz){

        try {
            return clz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("cannot find the class: " + clz);
    }

    public Car getCarByNickName(String nickName){
        switch (nickName){
            case "bus":
                return new Bus();
            case "bens":
                return new Bens();
            default:
                throw new IllegalArgumentException("cannot find the nickname: " + nickName);

        }
    }
}
