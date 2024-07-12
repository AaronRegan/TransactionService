FROM openjdk:8

ADD ./target/transaction-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/transaction-service.jar"]

EXPOSE 8080