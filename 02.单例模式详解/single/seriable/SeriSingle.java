package com.jiash.single.seriable;

import java.io.Serializable;

/**
 * @program: single
 * @Date: 2019/3/27 16:12
 * @Author: huangjp
 * @Description:
 */
public class SeriSingle implements Serializable {

    private static final SeriSingle INSTANCE = new SeriSingle();
    private SeriSingle(){}

    public static SeriSingle getInstance(){
        return INSTANCE;
    }

    public Object readResolve(){
        return INSTANCE;
    }
}
