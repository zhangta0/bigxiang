package com.bigxiang.entity;

import java.io.Serializable;

/**
 * Created by Zhon.Thao on 2019/4/24.
 *
 * @author Zhon.Thao
 */
public class ByteStruct implements Serializable {

    private byte serializeType;

    private byte messageType;

    private int length;

    private byte[] body;

    public ByteStruct(byte serializeType, byte messageType, int length, byte[] body) {
        this.serializeType = serializeType;
        this.messageType = messageType;
        this.length = length;
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public ByteStruct setBody(byte[] body) {
        this.body = body;
        return this;
    }

    public byte getSerializeType() {
        return serializeType;
    }

    public ByteStruct setSerializeType(byte serializeType) {
        this.serializeType = serializeType;
        return this;
    }

    public int getLength() {
        return length;
    }

    public ByteStruct setLength(int length) {
        this.length = length;
        return this;
    }

    public byte getMessageType() {
        return messageType;
    }

    public ByteStruct setMessageType(byte messageType) {
        this.messageType = messageType;
        return this;
    }
}
