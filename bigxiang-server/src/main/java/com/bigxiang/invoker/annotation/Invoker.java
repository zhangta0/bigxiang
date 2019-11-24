package com.bigxiang.invoker.annotation;

import com.bigxiang.constant.SerializerEnum;

import java.lang.annotation.*;

/**
 * Created by Zhon.Thao on 2019/4/28.
 *
 * @author Zhon.Thao
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Invoker {

    String url() default "";

    int timeout() default 1000;

    SerializerEnum serializer() default SerializerEnum.HESSIAN;
}
