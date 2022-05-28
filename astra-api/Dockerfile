FROM amazoncorretto:11-alpine AS build
WORKDIR /workspace/app

COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./
COPY src src

RUN ./gradlew bootJar -x test

FROM amazoncorretto:11-alpine

COPY --from=build /workspace/app/build/libs/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
