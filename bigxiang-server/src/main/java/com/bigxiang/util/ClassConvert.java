package com.bigxiang.util;

/**
 * Created by Zhon.Thao on 2019/11/21.
 *
 * @author Zhon.Thao
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
