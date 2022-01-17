FROM adoptopenjdk:11-jre-openj9
RUN mkdir /opt/app
COPY target/readingisgood-0.0.1-SNAPSHOT.jar /opt/app
CMD ["java", "-jar", "/opt/app/readingisgood-0.0.1-SNAPSHOT.jar"]