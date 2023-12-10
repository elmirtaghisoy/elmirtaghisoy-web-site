FROM openjdk:11
ENV TZ=Asia/Baku
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY build/libs/et-website-0.0.1.jar et-website-0.0.1.jar
ENTRYPOINT ["java","-jar","et-website-0.0.1.jar"]
CMD java -jar et-website-0.0.1.jar




#FROM registry.access.redhat.com/ubi8/openjdk-17:latest
#COPY build/libs/*.jar /app/app.jar
#WORKDIR /app/
#CMD ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
