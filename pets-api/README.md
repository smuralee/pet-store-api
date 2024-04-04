# Pets API

- Endpoint : `/pets`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**

## Compile, Test and Package

```shell
mvn clean
mvn package
docker buildx build -f Dockerfile --platform linux/amd64 --tag pets-api:latest --load .
```
