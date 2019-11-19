package com.bigxiang.serialize;

import com.alibaba.fastjson.JSON;
import com.bigxiang.serialize.iface.Serializer;

/**
 * Created by Zhon.Thao on 2019/4/24.
 *
 * @author Zhon.Thao
 */
public class JsonSerializer implements Serializer {

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) {
        return JSON.parseObject(bytes, clz);
    }
}
