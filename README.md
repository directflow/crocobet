# Example project for Crocobet

## Local launch
```
docker run --name hazelcast -p 5701:5701 hazelcast/hazelcast
```
```
docker run --name postgre -p 5432:5432 -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=crocobet -d postgres:16.0-alpine
```

## Technologies

* `Gradle`
* `Spring Boot`
* `PostgreSQL`
* `Hazelcast`
* `Docker`

## Author

* Zura Chaganava

