# 蒲公英项目

## 一、简介

1. 基于 springcloud gateway、hystrix、bus、springboot、dubbo、spring-security、spring-oauth2 构建的基础微服务项目

2. 提供对常见容器化支持 Docker

## 二、Future

### 技术

- [x] ~~task任务服务~~
- [x] ~~redis锁实现~~
- [x] ~~增加Hystrix组件~~
- [x] spring cloud gateway 网关实现 或者 KONG 实现
- [x] 鉴权服务
- [x] 消息总线BUS & MQ
- [x] 日志系统
- [x] 监控系统

## 三、前置条件

### Mysql、Redis、注册中心nacos、RabbitMQ

#### 1. 数据库为mysql,在other/sql目录下有数据库创建文件,运行sql文件
   
   - 下载docker镜像 
       ```shell script
       docker pull mysql:5.7
        ```
   - 创建mysql挂载目录
   
       以 Windows 为例，在D盘创建三个文件夹，Linux一样
        
        D:/Docker/data/mysql-01/conf 存放mysql配置文件，建议将other/mysql-conf/my.cnf文件放入该目录
        
        D:/Docker/data/mysql-01/data 存放mysql数据库文件
        
        D:/Docker/data/mysql-01/log  存放mysql日志
        
        D:/Docker/data/mysql-01/mnt  存放初始化数据库sql文件，将other/sql/中的文件放入该目录
   
   - 运行docker容器
       ```shell script
        docker run --name mysql-01 -p 3306:3306 \
            -e MYSQL_ROOT_PASSWORD=icepear123456 \
            -e TZ=Asia/Shanghai \
            -v D:/Docker/data/mysql-01/conf:/etc/mysql/conf.d \
            -v D:/Docker/data/mysql-01/data:/var/lib/mysql \
            -v D:/Docker/data/mysql-01/log:/var/log/mysql \
            -v D:/Docker/data/mysql-01/mnt:/home/mysql/mnt \
            --restart always \
            -d mysql:5.7
       ```
   - 初始化数据库sql文件
       ```shell script
          # 进入容器
           docker exec -it mysql-01 /bin/bash
          # 进入数据库
           mysql -u root -p
          # 运行sql文件
           source /home/mysql/mnt/dandelion_uaa.sql
           source /home/mysql/mnt/dandelion_schedulejob.sql
       ```
   
#### 2. redis为单机版即可
   - 下载docker镜像 
       ```shell script
       docker pull redis:latest
        ```
   - 创建redis挂载目录
      
      以 Windows 为例，在D盘创建三个文件夹，Linux一样
       
       D:/Docker/data/redis/conf/redis.conf redis配置文件，建议将other/redis-conf/redis.conf文件放入该目录
       
      D:/Docker/data/redis/data:/data 存放redis数据文件
      
   - 运行redis容器
       ```shell script
       docker run -p 6379:6379 \
       -v D:/Docker/data/redis/conf/redis.conf:/usr/local/etc/redis/redis.conf \
       -v D:/Docker/data/redis/data:/data \
       --name myredis --restart always -d redis
       ```
     
#### 3. nacos安装运行
   
   参考nacos安装方式[参考链接](https://github.com/nacos-group/nacos-docker)
   
   **注意：都是采用docker compose的方式运行，请先确保安装了docker compose**
   - Github Clone项目 
     ```
     git clone --depth 1 https://github.com/nacos-group/nacos-docker.git
     cd nacos-docker
     ```
   - 单应用模式
     ```
     docker-compose -f example/standalone-derby.yaml up
     ```
   - 用Mysql存储数据的单应用模式
     ```
     docker-compose -f example/standalone-mysql.yaml up
     ```
   - 集群高可用模式
     ```
     docker-compose -f example/cluster-hostname.yaml up 
     ```
   **使用**
   - 服务注册
     ```
     curl -X PUT 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=nacos.naming.serviceName&ip=20.18.7.10&port=8080'
     ```
   - 服务发现
     ```
     curl -X GET 'http://127.0.0.1:8848/nacos/v1/ns/instances?serviceName=nacos.naming.serviceName'
     ```
   - 配置发布
     ```
     curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test&content=helloWorld"
     ```
   - 获取配置
     ```
     curl -X GET "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test"
     ```
   - 界面访问  
     浏览器访问：http://127.0.0.1:8848/nacos/

#### 4. RabbitMQ安装运行

 
### 四、服务运行方式

1. 修改配置文件，数据库地址以及redis地址。密码方式请参考[配置文件加密方式](https://github.com/rim-wood/dandelion/tree/master/other/jasypt)

