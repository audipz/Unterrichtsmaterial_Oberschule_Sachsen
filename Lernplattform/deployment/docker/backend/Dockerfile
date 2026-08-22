FROM eclipse-temurin:25-jre-ubi10-minimal

WORKDIR /opt/lernplattform

COPY Lernplattform/backend/app/target/lernplattform-app-*.jar app.jar

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75 -XX:+UseContainerSupport"

EXPOSE 8080

USER 10001

ENTRYPOINT ["java", "-jar", "/opt/lernplattform/app.jar"]
