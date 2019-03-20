package com.jiash.delegate.simple;

/**
 * @program: single
 * @Date: 2019/3/19 14:51
 * @Author: huangjp
 * @Description:
 */
public class EmployeeA implements IEmployee {
    @Override
    public void doing(String command) {
        System.out.println("EmployeeA doing..." + command);
    }
}
