FROM eclipse-temurin:17-jre-alpine
COPY build/libs/debit-instruction-*-all.jar debit-instruction-service.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "debit-instruction-service.jar"]
