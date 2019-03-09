package com.company.jiash.book.abstractfactory;

import com.company.jiash.book.abstractfactory.factory.AbstractFactory;
import com.company.jiash.book.abstractfactory.factory.BenQComputerFactory;
import com.company.jiash.book.abstractfactory.factory.IBMComputerFactory;
import com.company.jiash.book.abstractfactory.product.CPU;
import com.company.jiash.book.abstractfactory.product.Storage;

/**
 * @program: sjms_test
 * @Date: 2019/3/9 9:28
 * @Author: huangjp
 * @Description:（网上写的） https://www.cnblogs.com/qiaoconglovelife/p/5750290.html
 * 优点：针对产品族；
 * 缺点：针对产品族。
 * 所以，只有对应产品族的情况下，才需要使用抽象工厂模式。
 */
public class TestAbstractFactory {

    public static void main(String[] args) {

        AbstractFactory factory = new BenQComputerFactory();
        CPU cpu = factory.useCpu();
        Storage storage = factory.useStorage();

        System.out.println(cpu.getClass().getName());
        System.out.println(storage.getClass().getName());

        factory = new IBMComputerFactory();
        cpu = factory.useCpu();
        storage = factory.useStorage();

        System.out.println(cpu.getClass().getName());
        System.out.println(storage.getClass().getName());
    }
}
