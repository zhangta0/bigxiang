package com.bigxiang.util;

/**
 * Created by zhangtao47 on 2019/11/21.
 *
 * @author zhangtao47
 */
public class ClassConvert {

    public static String[] convert(Class<?>[] types) {
        String[] clzStr = new String[types.length];
        if (types.length == 0) {
            return clzStr;
        }
        int i = 0;
        for (Class clz : types) {
            clzStr[i++] = clz.getName();
        }
        return clzStr;
    }
}
