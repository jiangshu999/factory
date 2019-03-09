package com.company.jiash.book.simpleFactory;

/**
 * @program: sjms_test
 * @Date: 2019/3/8 21:28
 * @Author: huangjp
 * @Description:（网上写的） https://www.cnblogs.com/qiaoconglovelife/p/5750290.html
 * 优点：
 *      （1）很明显，简单工厂的特点就是“简单粗暴”，通过一个含参的工厂方法，我们可以实例化任何产品类，
 *   上至飞机火箭，下至土豆面条，无所不能。所以简单工厂有一个别名：上帝类。
 * 缺点：
 *      （1）任何”东西“的子类都可以被生产，负担太重。当所要生产产品种类非常多时，工厂方法的代码量可能会很庞大。
 *      （2）在遵循开闭原则（对拓展开放，对修改关闭）的条件下，简单工厂对于增加新的产品，无能为力。
 *   因为增加新产品只能通过修改工厂方法来实现。工厂方法正好可以解决简单工厂的这两个缺点。
 *
 */
public class TestSimpleFactory {

    public static void main(String[] args) {
        SimpleFactory factory = new SimpleFactory();
        Car bus = factory.getCarByClass(Bus.class);
        Car bens = factory.getCarByNickName("bens");
        System.out.println(bus);
        System.out.println(bens);

        Car bmw = factory.getCarByNickName("bmw");
    }
}
