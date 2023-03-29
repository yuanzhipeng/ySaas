# ySaas

## 技术栈 
    开发环境使用 com.alibaba.druid.pool.DruidDataSource 数据池, 可以通过监控页面获取数据库使用情况, 更好的使用、优化系统
    生产环境更换 hikari 数据连接池, 性能更快更轻

    Spring-Cloud Spring-Cloud-Alibaba Spring-Boot SpringFramework 
    sharding-jdbc redis mysql5.7.24 nacos1.x elasticsearch7.x 

    未来发展可能使用到的技术栈
    redis cluster mongodb rocketMQ kafkaMQ flinkCDC zookeeper xxl-job 


## service-common
    ySaas 微服务通用模块 
    通用配置、基础类、基础util、各类常量池

## service-customer
    C端用户每个用户注册时通过 request path中获取店铺地址 通过saas 服务初始化的信息获取storeId、companyInfoId
    用户登陆令牌使用jwt+redis 默认缓存7天; 
    用户注册时, 通过sercice-id-generator获取用户customerId, 发送MQ消息,将用户信息推送到EsCustomerDetail索引中

## service-elasticsearch
    异构数据查询、搜索

## service-setting
    Saas系统设置中心、 Saas主站配置、 初始化配置等

## service-id-generator
    基于美团的lefa的发号器, 支持两种发号设置; 一种基于数据库的递增发号, 一种基于zk的雪花算法升级的长id
    qps 10w+

## service-message
    需要简历message Template 模版, 最好的方案是需要持久化,可以更好的重载
    用来发送站内信、短信、邮件、app推送等...

## service-pay
    主要用来与微信、支付宝等第三方支付渠道
    如果有清结算、对账等需求最好的方案是新建微服务

## service-saas
    Saas化基础 用户配置新的店铺信息等, 也可将配置后的信息推送到云平台, 自动解析Saas地址等信息

## y-platform
   * bff-manager-boos 
   * bff-web-2b

    bff-manager-boos : Saas化主站后端管理系统 管理和配置Saas平台 主站的启动会伴随着Saas平台的信息初始化,需要调用部分service获取配置和信息
    bff-web-2b : Saas化 WEB端, 主要用来服务客户, 可以直接使用WEB项目 也可以细化分为PC\MINI\MOBILE

## y-plugins
   * micro-service 
   * oss-sdk
   * pay-sdk

   micro-service : 自定义autoConfig
   oss-sdk : 云存储SDK(aliyun、腾讯、华为、minio、hdfs、fastFDS等)
   pay-sdk : 支付sdk 封装
