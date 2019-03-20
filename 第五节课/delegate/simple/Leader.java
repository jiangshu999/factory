package com.jiash.delegate.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: single
 * @Date: 2019/3/19 14:51
 * @Author: huangjp
 * @Description:
 */
public class Leader implements IEmployee {

    private Map<String,IEmployee> emps = new HashMap<>();

    public Leader(){
        emps.put("加密",new EmployeeA());
        emps.put("登陆",new EmployeeA());
    }

    @Override
    public void doing(String command) {
        this.emps.get(command).doing(command);
    }
}
