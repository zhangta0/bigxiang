package com.bigxiang.invoker.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhon.Thao on 2019/11/16.
 *
 * @author Zhon.Thao
 */
public class RequestFactory {

    private static final Map<String, RequestTask> map = new ConcurrentHashMap<String, RequestTask>();

    public static void put(String uuid, RequestTask requestTask) {
        map.put(uuid, requestTask);
    }

    public static RequestTask get(String uuid) {
        return map.get(uuid);
    }
}
