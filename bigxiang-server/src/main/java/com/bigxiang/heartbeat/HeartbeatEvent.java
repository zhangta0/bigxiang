package com.bigxiang.heartbeat;

import com.bigxiang.constant.MessageTypeCode;
import com.bigxiang.constant.SerializerEnum;
import com.bigxiang.entity.ByteStruct;
import com.bigxiang.netty.NettyClient;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Zhon.Thao on 2019/11/29.
 *
 * @author Zhon.Thao
 */
public class HeartbeatEvent implements Runnable {

    private NettyClient nettyClient;
    private AtomicBoolean started;
    private static ByteStruct byteStruct;
    private static ScheduledExecutorService schedule = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {

        AtomicInteger atomic = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("bigxiang-Heartbeat.thread-" + atomic.addAndGet(1));
            return thread;
        }
    });

    public HeartbeatEvent(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
        byteStruct = new ByteStruct(SerializerEnum.HESSIAN.code, MessageTypeCode.HEARTBEAT_CALL, 1, new byte[]{-1});
        started = new AtomicBoolean(false);
    }

    public void start() {
        if (started.compareAndSet(false, true)) {
            schedule.scheduleAtFixedRate(this, 0, 2, TimeUnit.SECONDS);
        }
    }

    @Override
    public void run() {
        nettyClient.write(byteStruct);
    }
}
