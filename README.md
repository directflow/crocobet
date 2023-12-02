# Example project for Crocobet

## Local launch

```
docker run --name hazelcast -p 5701:5701 -d hazelcast/hazelcast
docker run --name postgres -p 5432:5432 -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=crocobet -d postgres:16.0-alpine
docker run --name pulsar -p 6650:6650 -p 8080:8080 -d apachepulsar/pulsar:3.1.1 bin/pulsar standalone
```

### Build and run local

```
gradle clean build -x test
java -jar -Dspring.profiles.active=dev build/lib/example.jar
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
http://localhost:8082/swagger-ui/index.html
```

## Auth with ADMIN role

```
username: admin
password: admin123
```

## Database params

```
username: admin
password: admin
port: 5432
```

## Pulsar Producer/Consumer added

* Add new user with pulsar producer
* Listen new user with pulsar consumer
* Log async sending with pulsar producer
* Log listening with pulsar consumer

## Flink as external application added

* https://github.com/directflow/crocobet-flink
* Flink dashboard: http://localhost:8081

## Technologies

* `Apache Pulsar`
* `Apache Flink`
* `Spring Boot`
* `PostgreSQL`
* `Hazelcast`
* `Gradle`
* `Docker`

## Author

* Zura Chaganava

