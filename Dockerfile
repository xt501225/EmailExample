# Use Amazon Corretto Java 17 as the base image
FROM amazoncorretto:17-alpine

# Set the working directory inside the container
WORKDIR /opt
# Copy the JAR file into the container at /app
COPY build/libs/EmailSample-0.0.1-SNAPSHOT.jar /opt/app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
