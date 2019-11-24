package com.bigxiang.constant;

import io.netty.util.AttributeKey;

/**
 * Created by Zhon.Thao on 2019/4/30.
 *
 * @author Zhon.Thao
 */
public class ChannelAttributeKey {

    public static AttributeKey SERIALIZE_TYPE = AttributeKey.newInstance("serializeType");
    public static AttributeKey REQUEST_SEQ = AttributeKey.newInstance("requestSeq");
}
