package com.bigxiang.invoker.factory;

import com.bigxiang.invoker.proxy.RequestTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhon.Thao on 2019/11/16.
 *
 * @author Zhon.Thao
 */
public class RequestFactory {

    private static final Map<Long, RequestTask> map = new ConcurrentHashMap<>();

    public static void put(Long seq, RequestTask requestTask) {
        map.put(seq, requestTask);
    }

    public static RequestTask get(Long seq) {
        return map.get(seq);
    }
}
