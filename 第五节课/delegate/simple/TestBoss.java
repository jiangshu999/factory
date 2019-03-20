package com.jiash.delegate.simple;

/**
 * @program: single
 * @Date: 2019/3/19 14:58
 * @Author: huangjp
 * @Description:
 */
public class TestBoss {

    public static void main(String[] args) {
        new Boss().command("加密",new Leader());
    }
}
