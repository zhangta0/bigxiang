package com.bigxiang.provider.annotation;

import com.bigxiang.constant.SerializerEnum;

import java.lang.annotation.*;

/**
 * Created by Zhon.Thao on 2019/4/23.
 *
 * @author Zhon.Thao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Provider {

    String url() default "";

    SerializerEnum serializer() default SerializerEnum.HESSIAN;
}
