rocketmq-web
=============
项目说明：rocketmq web版控制台

namesrv设置方法：
一下几种方法按照优先级从高到低排序
* 启动时设置 jvm 的启动参数 rocketmq.namesrv.addr 
* 设置系统的环境变量 NAMESRV_ADDR
* 设置项目中 src/main/resources/app.properties 中 rocketmq.namesrv.addr属性
