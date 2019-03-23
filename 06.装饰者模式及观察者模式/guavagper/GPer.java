package com.jiash.observer3.guavagper;

import com.google.common.eventbus.EventBus;

/**
 * @program: single
 * @Date: 2019/3/23 15:43
 * @Author: huangjp
 * @Description:
 */
public class GPer extends EventBus{

    private static GPer gper = null;

    private GPer(){}

    public static GPer getInstance(){
        if(null == gper){
            gper = new GPer();
        }
        return gper;
    }

    public static void register(Question question){
        gper.register(question);
    }

    public static void unregister(Question question){
        gper.unregister(question);
    }

    public static void publishQuestion(Question question){
        System.out.println(question.getUserName() + "在" + question.getWhereGeneration() + "上提交了一个问题。");
        gper.post(question);
    }

}
