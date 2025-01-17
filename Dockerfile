# Use an official OpenJDK image as the base image
FROM maven:3.9.7-eclipse-temurin-17

# Set the working directory in the container
WORKDIR /app

# Copy the JAR files from the host to the container
COPY poc-aws-chime-api/target/poc-aws-chime-api-*.jar ./app.jar

ENV AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}
ENV AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "./app.jar"]


