FROM java:8-jdk-alpine

#基本环境变量设置
ENV TZ=Asia/Shanghai \
 	UPM_HOME=/var/dandelion_upm_home \
 	LOG_DIR=/home/icepear/logs \
 	SERVER_PORT=8008 \
 	PROFILES_ACTIVE=test

#创建应用根目录
RUN set -ex; \
	mkdir -p "$UPM_HOME" "$LOG_DIR"; \
	ln -snf /usr/share/zoneinfo/"$TZ" /etc/localtime && echo "$TZ" > /etc/timezone;

#拷贝应用程序至镜像内部

ADD target/dandelion-upm-biz-1.0.0.jar $UPM_HOME/app.jar

#日志文件目录
VOLUME ["$LOG_DIR/upm"]

#对外暴露端口
EXPOSE $SERVER_PORT

CMD java -jar -Dfile.encoding=utf-8 $UPM_HOME/app.jar --server.port=$SERVER_PORT --spring.profiles.active=$PROFILES_ACTIVE