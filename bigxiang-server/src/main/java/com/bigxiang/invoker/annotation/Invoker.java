package com.bigxiang.invoker.annotation;

import com.bigxiang.constant.SerializerEnum;

/**
 * Created by Zhon.Thao on 2019/4/28.
 *
 * @author Zhon.Thao
 */
public @interface Invoker {

    String url();

    int timeout() default 1000;

    SerializerEnum serializer() default SerializerEnum.HESSIAN;
}
