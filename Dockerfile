# Use a lightweight Java image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory (optional, helps with structure)
WORKDIR /app

# Add a volume to store logs if needed
VOLUME /tmp

# Copy your built JAR file into the container
COPY target/todolist-0.0.1-SNAPSHOT.jar app.jar

# Expose the correct port your app uses
EXPOSE 4777

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
