package com.bigxiang.factory;

import com.bigxiang.constant.SerializerEnum;
import com.bigxiang.serialize.HessianSerializer;
import com.bigxiang.serialize.JsonSerializer;
import com.bigxiang.serialize.iface.Serializer;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by Zhon.Thao on 2019/4/24.
 *
 * @author Zhon.Thao
 */
public class SerializerFactory {

    static final Map<Byte, Serializer> SERIALIZER = Maps.newHashMap();

    static void init() {
        SERIALIZER.put(SerializerEnum.JSON.code, new JsonSerializer());
        SERIALIZER.put(SerializerEnum.HESSIAN.code, new HessianSerializer());
    }

    static public Serializer get(byte code) {
        return SERIALIZER.get(code);
    }
}
