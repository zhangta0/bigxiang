package com.bigxiang.serialize.iface;

/**
 * Created by Zhon.Thao on 2019/4/24.
 *
 * @author Zhon.Thao
 */
public interface Serializer {

    byte[] serialize(Object object) throws Exception;

    <T> T  deserialize(byte[] bytes,Class<T> clz) throws Exception;
}
