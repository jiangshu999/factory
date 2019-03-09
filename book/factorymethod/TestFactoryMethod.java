package com.company.jiash.book.factorymethod;

/**
 * @program: sjms_test
 * @Date: 2019/3/9 7:53
 * @Author: huangjp
 * @Description:（网上写的） https://www.cnblogs.com/qiaoconglovelife/p/5750290.html
 *
 * 优点：
 *     （1）工厂方法模式就很好的减轻了工厂类的负担，把某一类/某一种东西交由一个工厂生产；（对应简单工厂的缺点1）
 *     （2）同时增加某一类”东西“并不需要修改工厂类，只需要添加生产这类”东西“的工厂即可，使得工厂类符合开放-封闭原则。
 * 缺点：
 *      （1）相比简单工厂，实现略复杂。
 *      （2）对于某些可以形成产品族的情况处理比较复杂。
 *       对于缺点（2），我们可以借用抽象工厂来实现
 */
public class TestFactoryMethod {

    public static void main(String[] args) {
        AbstractOperFactory factory = new AddOperFactory();
        IOperation oper = factory.calculation(11,22);
        System.out.println(oper.getClass());

        factory = new DivideOperFactory();
        oper = factory.calculation(11,0);
        System.out.println(oper.getClass());

    }
}
