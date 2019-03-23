package com.jiash.observer3.guavagper;

/**
 * @program: single
 * @Date: 2019/3/23 15:56
 * @Author: huangjp
 * @Description:
 */
public class ObserverTest {

    public static void main(String[] args) {
        //这为没有@Tom老师
        Question question = new Question();
        question.setWhereGeneration("GPer生态圈");
        question.setUserName("小明");
        question.setContent("观察者设计模式适用于哪些场景？");

        Teacher tom = new Teacher("Tom");
        Teacher mic = new Teacher("Mic");

        GPer gper = GPer.getInstance();
        gper.register(tom);
        gper.register(mic);
        gper.publishQuestion(question);
    }
}
