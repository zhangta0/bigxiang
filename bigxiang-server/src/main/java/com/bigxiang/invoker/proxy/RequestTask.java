package com.bigxiang.invoker.proxy;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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

    Object response;
    Class<?> returnType;

    public RequestTask(Class<?> returnType) {
        this.returnType = returnType;
    }

    public Object getResponse(long timeOut) {
        if (!isDone) {
            try {
                lock.lock();
                if (condition.await(timeOut, TimeUnit.MILLISECONDS)) {
                    if (!isDone) {
                        return new TimeoutException();
                    }
                    return response;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
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

    public boolean isDone() {
        return isDone;
    }

    public RequestTask setDone(boolean done) {
        isDone = done;
        return this;
    }

    public Class<?> getReturnType() {
        return returnType;
    }
}
