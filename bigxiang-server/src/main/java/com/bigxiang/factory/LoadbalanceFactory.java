package com.bigxiang.factory;

import com.bigxiang.constant.LoadbalanceEnum;
import com.bigxiang.loadbalance.HashLoadBalance;
import com.bigxiang.loadbalance.PlainLoadBalance;
import com.bigxiang.loadbalance.RandomLoadBalance;
import com.bigxiang.loadbalance.iface.LoadBalance;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangtao47 on 2019/11/26.
 *
 * @author zhangtao47
 */
public class LoadbalanceFactory {

    private static Map<String, LoadBalance> loadbalanceMap = new HashMap<>();

    public static void init() {
        loadbalanceMap.put(LoadbalanceEnum.HASH.code, new HashLoadBalance());
        loadbalanceMap.put(LoadbalanceEnum.RANDOM.code, new RandomLoadBalance());
        loadbalanceMap.put(LoadbalanceEnum.PLAIN.code, new PlainLoadBalance());
    }

    public static LoadBalance get(String code) {
        return loadbalanceMap.get(code);
    }
}
