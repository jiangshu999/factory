package com.jiash.prototype.qian;

/**
 * @program: single
 * @Date: 2019/5/10 16:46
 * @Author: huangjp
 * @Description:
 */
public class PersonA implements PersonPrototype {

    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public PersonPrototype clone() {
        PersonA personA = new PersonA();
        personA.setAge(this.age);
        personA.setName(this.name);
        return personA;
    }
}
