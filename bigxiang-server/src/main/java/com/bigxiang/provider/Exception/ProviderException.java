package com.bigxiang.provider.Exception;

/**
 * Created by Zhon.Thao on 2019/4/30.
 *
 * @author Zhon.Thao
 */
public class ProviderException extends Exception {

    public ProviderException(String msg) {
        super(msg);
    }

    public ProviderException(String msg, Exception e) {
        super(msg, e);
    }
}
