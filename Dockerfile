FROM openjdk:11
ENV TZ=Asia/Baku
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY build/libs/et-website-0.0.1.jar et-website-0.0.1.jar
ENTRYPOINT ["java","-jar","et-website-0.0.1.jar"]
CMD java -jar et-website-0.0.1.jar