FROM java:openjdk-8-jre
VOLUME /tmp

ADD bank-statement-service.jar app.jar
EXPOSE 8081
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]