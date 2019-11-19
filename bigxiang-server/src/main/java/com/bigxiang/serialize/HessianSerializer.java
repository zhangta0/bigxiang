package com.bigxiang.serialize;

import com.bigxiang.serialize.iface.Serializer;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Zhon.Thao on 2019/4/24.
 *
 * @author Zhon.Thao
 */
public class HessianSerializer implements Serializer {

    @Override
    public byte[] serialize(Object object) throws Exception {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        HessianOutput output = new HessianOutput(outputStream);
        output.writeObject(object);

        return outputStream.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws Exception {

        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        HessianInput input = new HessianInput(inputStream);

        return (T) input.readObject(clz);
    }
}
