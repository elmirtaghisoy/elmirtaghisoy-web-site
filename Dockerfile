FROM openjdk:11
LABEL maintainer="et-website"
ENV TZ=Asia/Baku
VOLUME /media
WORKDIR /app
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY /et-website-0.0.1.jar et-website-0.0.1.jar
ENTRYPOINT ["java","-jar","et-website-0.0.1.jar"]
CMD java -jar et-website-0.0.1.jar