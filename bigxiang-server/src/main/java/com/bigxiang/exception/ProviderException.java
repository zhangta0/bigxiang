package com.bigxiang.exception;

import com.bigxiang.invoker.entity.InvokeRequest;

/**
 * Created by Zhon.Thao on 2019/12/8.
 *
 * @author Zhon.Thao
 */
public class ProviderException extends RuntimeException {

    public ProviderException(String msg, Exception e) {
        super(msg, e);
    }

    public ProviderException(InvokeRequest request, String msg) {
        super(msg + String.format(",request:%s", request.toString()));
    }

    public ProviderException(String msg) {
        super(msg);
    }
}
