package com.company.jiash.book.factorymethod;

/**
 * @program: sjms_test
 * @Date: 2019/3/9 7:51
 * @Author: huangjp
 * @Description:
 */
public class DivideOperFactory extends AbstractOperFactory {
    @Override
    public IOperation calculation(double numberA, double numberB) {
        if(numberB!=0){
            return new DivideOperation();
        }else{
            throw  new ArithmeticException("Divisor cannot be zero: " + numberB);
        }
    }
}
