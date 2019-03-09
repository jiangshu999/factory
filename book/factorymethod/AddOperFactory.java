package com.company.jiash.book.factorymethod;

/**
 * @program: sjms_test
 * @Date: 2019/3/9 7:50
 * @Author: huangjp
 * @Description:
 */
public class AddOperFactory extends AbstractOperFactory {
    @Override
    public IOperation calculation(double numberA, double numberB) {
        return new AddOperation();
    }
}
