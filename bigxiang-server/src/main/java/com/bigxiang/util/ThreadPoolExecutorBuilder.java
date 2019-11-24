package com.bigxiang.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangtao47 on 2019/11/21.
 *
 * @author zhangtao47
 */
public class ThreadPoolExecutorBuilder {

    private int core = 5;

    private int max = 5;

    //second
    private long keeplive = 30;

    private BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10000);

    private ThreadFactory threadFactory = new ThreadFactory() {
        private AtomicInteger atomicInteger = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("bigxiang-thread-" + atomicInteger.addAndGet(1));
            return thread;
        }
    };

    private RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

    public ThreadPoolExecutorBuilder core(int core) {
        this.core = core;
        return this;
    }

    public ThreadPoolExecutorBuilder max(int max) {
        this.max = max;
        return this;
    }

    public ThreadPoolExecutorBuilder keeplive(int keeplive) {
        this.keeplive = keeplive;
        return this;
    }

    public ThreadPoolExecutorBuilder workQueue(BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        return this;
    }

    public ThreadPoolExecutorBuilder threadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        return this;
    }

    public ThreadPoolExecutorBuilder workQueue(RejectedExecutionHandler handler) {
        this.handler = handler;
        return this;
    }

    public ThreadPoolExecutor build() {
        return new ThreadPoolExecutor(core, max, keeplive, TimeUnit.SECONDS, workQueue, threadFactory, handler);
    }
}
