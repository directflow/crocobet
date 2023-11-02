FROM gradle:jdk17 AS BUILD_STAGE
COPY --chown=gradle:gradle . /home/gradle
RUN gradle clean build -x test || return 1
FROM openjdk:17
ENV ARTIFACT_NAME=example.jar
ENV APP_HOME=/app
COPY --from=BUILD_STAGE /home/gradle/build/libs/$ARTIFACT_NAME $APP_HOME/
WORKDIR $APP_HOME
RUN groupadd -r -g 1000 user && useradd -r -g user -u 1000 user
RUN chown -R user:user /app
USER user
ENTRYPOINT exec java -jar -Dspring.profiles.active=prod -Duser.timezone=GMT+04:00 ${ARTIFACT_NAME}
