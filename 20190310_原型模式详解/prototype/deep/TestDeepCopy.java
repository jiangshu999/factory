package com.jiash.prototype.deep;

/**
 * @program: single
 * @Date: 2019/5/10 17:05
 * @Author: huangjp
 * @Description:
 */
public class TestDeepCopy {

    public static void main(String[] args) {
        PersonB personB = new PersonB();
        try {
            PersonB personB1 = personB.clone();
            System.out.println(personB == personB1);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
