package com.bigxiang.invoker.client;

import java.io.Serializable;

/**
 * Created by Zhon.Thao on 2019/11/16.
 *
 * @author Zhon.Thao
 */
public class Response implements Serializable {

    private String seq;

    private Object object;

    public String getSeq() {
        return seq;
    }

    public Response setSeq(String seq) {
        this.seq = seq;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public Response setObject(Object object) {
        this.object = object;
        return this;
    }
}
