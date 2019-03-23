package com.jiash.observer3.guavagper;

import com.google.common.eventbus.Subscribe;

/**
 * @program: single
 * @Date: 2019/3/23 15:40
 * @Author: huangjp
 * @Description:
 */
public class Teacher {

    private String name;

    public Teacher(String name){
        this.name = name;
    }

    @Subscribe
    public void update(Object arg) {
        Question question = (Question)arg;
        System.out.println("===============================");
        System.out.println(name + "老师，你好！\n" +
                "您收到了一个来自“" + question.getWhereGeneration() + "”的提问，希望您解答，问题内容如下：\n" +
                question.getContent() + "\n" +
                "提问者：" + question.getUserName());
    }
}
