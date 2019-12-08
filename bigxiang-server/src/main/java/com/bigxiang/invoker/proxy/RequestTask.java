package com.bigxiang.invoker.proxy;

import com.bigxiang.exception.ProviderTimeoutException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Zhon.Thao on 2019/11/16.
 *
 * @author Zhon.Thao
 */
public class RequestTask {

    private boolean isDone = Boolean.FALSE;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Object response;
    private Class returnType;

    public RequestTask(Class returnType) {
        this.returnType = returnType;
    }

    public Object getResponse(long timeout) {
        if (!isDone) {
            try {
                lock.lock();
                if (condition.await(timeout, TimeUnit.MILLISECONDS)) {
                    if (!isDone) {
                        return new ProviderTimeoutException("provider execute timeout,timeOut:" + timeout);
                    }
                    return response;
                }
            } catch (InterruptedException e) {
            } finally {
                lock.unlock();
            }
        }
        return response;
    }

    public void setResponse(Object response) {
        lock.lock();
        try {
            this.response = response;
            isDone = true;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public Class getReturnType() {
        return returnType;
    }
}
