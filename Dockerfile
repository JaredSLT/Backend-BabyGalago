FROM alpine
RUN apk update
RUN apk add openjdk17-jdk
EXPOSE 80
EXPOSE 443
ADD target/babygalago-1.0-SNAPSHOT.jar server.jar
ADD Settings.json Settings.json
COPY assets ./assets
ENTRYPOINT ["java", "-jar", "server.jar", "server"]

