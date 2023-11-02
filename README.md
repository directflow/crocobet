# Example project for Crocobet

## Local launch

```
docker run --name hazelcast -p 5701:5701 -d hazelcast/hazelcast
docker run --name postgres -p 5432:5432 -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=crocobet -d postgres:16.0-alpine
```

### Build and run local
```
gradle clean build -x test
java -jar -Dspring.profiles.active=dev example.jar
```

### Run in Idea
```
-Dspring.profiles.active=dev
```

## Docker launch

```
docker-compose up
```

## Access
```
http://localhost:8080/swagger-ui/index.html
```

## Auth with ADMIN role
```
username: admin
password: admin123
```

## Technologies

* `Gradle`
* `Spring Boot`
* `PostgreSQL`
* `Hazelcast`
* `Docker`

## Author

* Zura Chaganava

