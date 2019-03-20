package com.jiash.delegate.simple;

/**
 * @program: single
 * @Date: 2019/3/19 14:51
 * @Author: huangjp
 * @Description:
 */
public class EmployeeB implements IEmployee {
    @Override
    public void doing(String command) {
        System.out.println("EmployeeB doing..." + command);
    }
}
