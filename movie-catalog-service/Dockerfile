# define base docker image
FROM openjdk:11
LABEL maintainer="code2go77.com"
ADD target/movie-catalog-service-0.0.1-SNAPSHOT.jar movie-catalog-docker.jar
ENTRYPOINT ["java", "-jar", "movie-catalog-docker.jar"]
