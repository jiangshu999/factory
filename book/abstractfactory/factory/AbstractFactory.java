package com.company.jiash.book.abstractfactory.factory;

import com.company.jiash.book.abstractfactory.product.CPU;
import com.company.jiash.book.abstractfactory.product.Storage;

/**
 * @program: sjms_test
 * @Date: 2019/3/9 8:20
 * @Author: huangjp
 * @Description:
 */
public interface AbstractFactory {

    CPU useCpu();

    Storage useStorage();
}
