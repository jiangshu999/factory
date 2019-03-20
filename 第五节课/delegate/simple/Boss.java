package com.jiash.delegate.simple;

/**
 * @program: single
 * @Date: 2019/3/19 14:56
 * @Author: huangjp
 * @Description:
 */
public class Boss {

    public void command(String command,Leader leader){
        leader.doing(command);
    }
}
