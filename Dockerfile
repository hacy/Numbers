FROM openjdk:8-alpine

# Required for starting application up.
RUN apk update && apk add bash

RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app

COPY target/assignment-0.0.1-SNAPSHOT.jar $PROJECT_HOME/assignment-0.0.1-SNAPSHOT.jar

WORKDIR $PROJECT_HOME

CMD ["java", "-Dspring.data.mongodb.uri=mongodb://assi gnment-mongo:27017/numberdb","-Djava.security.egd=file:/dev/./urandom","-jar","./assignment-0.0.1-SNAPSHOT.jar"]
