# Example project for Crocobet

## Local launch

```
docker run --name hazelcast -p 5701:5701 hazelcast/hazelcast
docker run --name postgres -p 5432:5432 -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=crocobet -d postgres:16.0-alpine
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

## Technologies

* `Gradle`
* `Spring Boot`
* `PostgreSQL`
* `Hazelcast`
* `Docker`

## Author

* Zura Chaganava

