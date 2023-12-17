# Email Service README

## Description
This Spring Boot application provides functionalities to interact with email services. The following features are supported:

1. **Retrieve Inbox Contents:**
    - Retrieve the contents of the user's inbox.

2. **Retrieve Single Email:**
    - Retrieve the contents of a single email.

3. **Write and Save Draft Email:**
    - Write a draft email and save it for later.

4. **Send Email:**
    - Send an email.

5. **Update Draft Email Properties:**
    - Update one or more properties of a draft email (e.g., recipients).


## How to build project
To run the Spring Boot application, use Gradle command(buildJar) to build the JAR file located in `/build/libs`.


## How to build docker
```cmd
docker build -t email-service .
```
## How to run docker

```cmd
docker run -p 8080:8080 email-service
```

## How to test project
```cmd
http://localhost:8080/swagger-ui/index.html
```
