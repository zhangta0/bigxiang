package com.bigxiang.provider.annotation;

import com.bigxiang.constant.SerializerEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Zhon.Thao on 2019/4/23.
 *
 * @author Zhon.Thao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Provider {

    String url();

    SerializerEnum serializer() default SerializerEnum.HESSIAN;
}
