package com.company.jiash.book.factorymethod;

/**
 * @program: sjms_test
 * @Date: 2019/3/9 7:51
 * @Author: huangjp
 * @Description:
 */
public class MultiplyOperFactory extends AbstractOperFactory {
    @Override
    public IOperation calculation(double numberA, double numberB) {
        return new MultiplyOperation();
    }
}
