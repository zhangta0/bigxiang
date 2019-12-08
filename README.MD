一个基于spring的纯注解驱动式RPC框架，是一个完全的去中心化的RPC框架，基于zookeeper提供服务注册、服务发现功能、
提供多种负载均衡算法。支持服务端宕机，客户端自动从负载均衡桶里删除相应ip，支持服务端无状态扩容，客户端自动发现。
支持断网自动恢复连接、心跳检测机制。提供高可用的RPC调用环境

调用方注解
@Invoker
支持同步、异步两种调用方式。默认同步调用
支持hessian、json两种序列化方式。默认hessian
支持调用超时中断。默认timeout=1s


提供方注解
@Provider
支持自定义唯一标识url,默认是类名


支持3中负载均衡算法
hash ： 居于调用唯一参数的hashcode % 服务提供方节点的数量 
plain   顺序去取服务提供方节点
random  随机取服务提供方节点


**使用说明**
1.在项目中注入bigxiang的启动类
<bean class="com.bigxiang.start.BigxiangBoot"/>

2.bigxiang配置注入，提供两种方案
方案一：直接在上面👆启动类定义
<bean class="com.bigxiang.start.BigxiangBoot">
     <property name="zkAddress" value="localhost:2181,127.0.0.1:2181"/>
     <property name="serverPort" value="4002"/>
     <property name="loadBalance" value="plain"/>
</bean>
  
方案二：添加bigxiang的properties文件
zkAddress=localhost:2181,127.0.0.1:2181  （zk地址,必须） 
port=4002                                 (服务提供方netty端口，非必须，默认自动选择端口)         
loadBalance=plain                         (负载均衡算法，非必须，默认顺序负载均衡算法)       
