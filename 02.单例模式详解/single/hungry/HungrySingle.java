package com.jiash.single.hungry;

/**
 * @program: single
 * @Date: 2019/3/24 10:19
 * @Author: huangjp
 * @Description:
 */
public class HungrySingle {

    private static final HungrySingle instance = new HungrySingle();

    private HungrySingle(){}

    public static HungrySingle getInstance(){
        return instance;
    }
}

class HungryStaticSingle{
    private static HungryStaticSingle instance = null;
    private HungryStaticSingle(){}

    static {
        if(instance==null){
            instance = new HungryStaticSingle();
        }
    }

    public static HungryStaticSingle getInstance(){
        return instance;
    }
}
