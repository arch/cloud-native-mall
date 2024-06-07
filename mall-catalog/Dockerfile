FROM eclipse-temurin:17 AS builder
WORKDIR /builder

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} catalog-service.jar

# https://docs.spring.io/spring-boot/reference/container-images/dockerfiles.html
# RUN java -Djarmode=tools -jar catalog-service.jar extract --layers
RUN java -Djarmode=layertools -jar catalog-service.jar extract

FROM eclipse-temurin:17
WORKDIR workspace
COPY --from=builder /builder/dependencies/ ./
COPY --from=builder /builder/spring-boot-loader/ ./
COPY --from=builder /builder/snapshot-dependencies/ ./
COPY --from=builder /builder/application/ ./

LABEL authors="yingtingxu"
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]