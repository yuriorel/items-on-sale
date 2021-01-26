FROM openjdk:8-jre-alpine
COPY spring2demo-0.0.1-SNAPSHOT.jar /app/spring2demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java"]
CMD ["-jar", "/app/spring2demo-0.0.1-SNAPSHOT.jar"]
EXPOSE 9080
RUN apk --update --no-cache add curl
HEALTHCHECK CMD curl -f http://localhost:9080/hello || exit 1
