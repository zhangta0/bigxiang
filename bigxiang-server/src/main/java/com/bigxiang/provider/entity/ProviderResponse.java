package com.bigxiang.provider.entity;

import java.io.Serializable;

/**
 * Created by Zhon.Thao on 2019/4/30.
 *
 * @author Zhon.Thao
 */
public class ProviderResponse implements Serializable {

    private long seq;

    private Object result;

    public Object getResult() {
        return result;
    }

    public ProviderResponse(long seq, Object result) {
        this.seq = seq;
        this.result = result;
    }

    public ProviderResponse setResult(Object result) {
        this.result = result;
        return this;
    }

    public long getSeq() {
        return seq;
    }

    public ProviderResponse setSeq(long seq) {
        this.seq = seq;
        return this;
    }
}
