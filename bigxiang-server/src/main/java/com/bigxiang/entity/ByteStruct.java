package com.bigxiang.entity;

/**
 * Created by Zhon.Thao on 2019/4/24.
 *
 * @author Zhon.Thao
 */
public class ByteStruct {

    private byte serializeType;

    private long seq;

    private int length;

    private byte[] body;

    public ByteStruct(byte serializeType, long seq, int length, byte[] body) {
        this.serializeType = serializeType;
        this.seq = seq;
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

    public long getSeq() {
        return seq;
    }

    public ByteStruct setSeq(long seq) {
        this.seq = seq;
        return this;
    }
}
