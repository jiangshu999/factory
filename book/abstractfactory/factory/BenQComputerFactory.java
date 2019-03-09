package com.company.jiash.book.abstractfactory.factory;

import com.company.jiash.book.abstractfactory.product.ADATAStorage;
import com.company.jiash.book.abstractfactory.product.AMDCPU;
import com.company.jiash.book.abstractfactory.product.CPU;
import com.company.jiash.book.abstractfactory.product.Storage;

/**
 * @program: sjms_test
 * @Date: 2019/3/9 8:30
 * @Author: huangjp
 * @Description:
 */
public class BenQComputerFactory implements AbstractFactory {
    @Override
    public CPU useCpu() {
        return new AMDCPU();
    }

    @Override
    public Storage useStorage() {
        return new ADATAStorage();
    }
}
