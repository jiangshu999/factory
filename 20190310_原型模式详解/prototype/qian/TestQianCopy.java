package com.jiash.prototype.qian;

/**
 * @program: single
 * @Date: 2019/5/10 16:48
 * @Author: huangjp
 * @Description:
 */
public class TestQianCopy {

    public static void main(String[] args) {
        PersonA personA = new PersonA();
        personA.setName("Jiash");
        personA.setAge(25);

        PersonA personA1 = (PersonA) personA.clone();
        System.out.println(personA1.getAge());
        System.out.println(personA1.getName());
    }
}
