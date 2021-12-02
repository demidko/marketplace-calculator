FROM maven:openjdk as builder
WORKDIR /project
COPY src ./src
COPY pom.xml ./pom.xml
RUN mvn clean package

FROM openjdk:18 as backend
WORKDIR /root
COPY --from=builder /project/target/*.jar ./app
ENTRYPOINT ["java", "-jar", "/root/app"]