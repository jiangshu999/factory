package com.company.jiash.book.abstractfactory.factory;

import com.company.jiash.book.abstractfactory.product.CPU;
import com.company.jiash.book.abstractfactory.product.IntelCPU;
import com.company.jiash.book.abstractfactory.product.KingstonStorage;
import com.company.jiash.book.abstractfactory.product.Storage;

/**
 * @program: sjms_test
 * @Date: 2019/3/9 8:31
 * @Author: huangjp
 * @Description:
 */
public class IBMComputerFactory implements AbstractFactory {
    @Override
    public CPU useCpu() {
        return new IntelCPU();
    }

    @Override
    public Storage useStorage() {
        return new KingstonStorage();
    }
}
