FROM maven:3.8.3-openjdk-17-slim
# Author: Kevin Velasquez

ARG APP_NAME=jwtAuth #App name
ARG APP_VERSION=v1.1 #App version
ARG JAR_FILENAME ${APP_NAME}-${APP_VERSION}.jar #App file name

#Set working directory
WORKDIR /${APP_NAME}

COPY /lib/FilterLibrary-1.0.0.jar /${APP_NAME}/lib/FilterLibrary-1.0.0.jar
RUN mvn install:install-file -Dfile=/${APP_NAME}/lib/FilterLibrary-1.0.0.jar -DgroupId=com.lab -DartifactId=FilterLibrary -Dversion=1.0.0 -Dpackaging=jar

COPY /lib/JwtLibrary-1.0.0.jar /${APP_NAME}/lib/JwtLibrary-1.0.0.jar
RUN mvn install:install-file -Dfile=/${APP_NAME}/lib/JwtLibrary-1.0.0.jar -DgroupId=com.jwtlibrary -DartifactId=JwtLibrary -Dversion=1.0.0 -Dpackaging=jar

COPY /lib/authority-1.0.0.jar /${APP_NAME}/lib/authority-1.0.0.jar
RUN mvn install:install-file -Dfile=/${APP_NAME}/lib/authority-1.0.0.jar -DgroupId=com.lab -DartifactId=authority -Dversion=1.0.0 -Dpackaging=jar

#Copy sources from to container
COPY . /${APP_NAME}

#Build with maven
RUN mvn clean test package -X

CMD ["java", "-jar", "target/jwtAuth-v1.1.jar"] #Run with Spring Boot
#CMD ["tail", "-f", "/dev/null"] #Keep container #Run to access it