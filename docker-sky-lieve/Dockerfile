FROM java:8
MAINTAINER sukaiyi

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD sky-lieve-0.0.1-SNAPSHOT.jar app.jar
ADD application.properties application.properties

EXPOSE 80

CMD ["java","-jar","app.jar"]

