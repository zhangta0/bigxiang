package com.bigxiang.exception;

/**
 * Created by Zhon.Thao on 2019/12/8.
 *
 * @author Zhon.Thao
 */
public class InvokerException extends Exception {

    public InvokerException(String msg) {
        super(msg);
    }

    public InvokerException(Exception e) {
        super(e);
    }

    public InvokerException(String msg, Exception e) {
        super(msg, e);
    }
}
