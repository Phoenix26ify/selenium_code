# Use an official Java runtime as a parent image
FROM openjdk:22-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Install Maven
RUN apt-get update && apt-get install -y maven && apt-get clean

# Package the application
RUN mvn clean package

# Install Firefox and Geckodriver
RUN apt-get update && \
    apt-get install -y firefox-esr && \
    apt-get install -y wget && \
    wget -q https://github.com/mozilla/geckodriver/releases/latest/download/geckodriver-linux64.tar.gz && \
    tar -xzf geckodriver-linux64.tar.gz && \
    mv geckodriver /usr/local/bin/ && \
    chmod +x /usr/local/bin/geckodriver && \
    rm geckodriver-linux64.tar.gz

# Command to run the tests
CMD ["mvn", "test"]

