## Bigxiang 简介

------
![cmd-markdown-logo](http://pic.51yuansu.com/pic3/cover/02/32/50/59c0dcc78223a_610.jpg)

一个基于spring的纯注解驱动式RPC框架，是一个完全的去中心化的RPC框架，基于zookeeper提供服务注册、服务发现功能

> * 提供多种负载均衡算法
> * 提供hessian、json两种序列化方式
> * 支持同步、异步两种调用方式
> * 支持客户端调用超时自动中断
> * 支持服务端无状态扩容、客户端自动发现
> * 服务端宕机，客户端自动从负载均衡桶里删除相应ip
> * 支持断网自动恢复连接、心跳检测机制



### [使用说明](https://www.zybuluo.com/cmd/)

> 请仔细阅读

------

### @Invoker 调用方注解
- [x] 支持同步、异步两种调用方式。默认同步调用
- [x] 支持hessian、json两种序列化方式。默认hessian
- [x] 支持调用超时中断。默认timeout=1s

   
```python
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Invoker {
        
    String url() default "";
        
    int timeout() default 1000;
        
    InvokeType invokeType() default InvokeType.SYNC;
        
    SerializerEnum serializer() default SerializerEnum.HESSIAN;
}
```




### @Provider 提供方注解 
- [x] 支持自定义唯一标识url,默认是类名
```python
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Provider {
    
    String url() default "";
}
```


###  支持3种负载均衡算法

> * hash ： 居于调用唯一参数的hashcode % 服务提供方节点的数量 
> * plain   顺序去取服务提供方节点
> * random  随机取服务提供方节点


### 8. 使用说明
1.在项目中注入bigxiang的启动类
```
<bean class="com.bigxiang.start.BigxiangBoot"/>

```

2.bigxiang配置注入，提供两种方案
`方案一：直接在上面👆启动类定义 `
```
<bean class="com.bigxiang.start.BigxiangBoot">
     <property name="zkAddress" value="localhost:2181,127.0.0.1:2181"/>
     <property name="serverPort" value="4002"/>
     <property name="loadBalance" value="plain"/>
</bean>
```
`方案二：添加bigxiang的properties文件,添加在根目录`
```
zkAddress=localhost:2181,127.0.0.1:2181  （zk地址,必须） 
port=4002                                 (服务提供方netty端口，非必须，默认自动选择端口)         
loadBalance=plain                         (负载均衡算法，非必须，默认顺序负载均衡算法)    
```

------

感谢您的阅读，请大家多多提意见, 谢谢! 

作者 @Zhon.Thao    
2019 年 12月 08日    

