FROM mysql:5.7
MAINTAINER sukaiyi

ENV MYSQL_ALLOW_EMPTY_PASSWORD yes
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD schema.sql /root/schema.sql
ADD privileges.sql /root/privileges.sql
ADD startup.sh /root/startup.sh

EXPOSE 3306

WORKDIR /root

CMD ["sh", "startup.sh"]
