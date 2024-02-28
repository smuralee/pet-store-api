# PetStore API

- Endpoint : `/pets`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  
## Compile, Test and Package

```shell
mvn package
```

## Push image to container registry

```shell
docker build -t pet-store-api:latest .
docker push pet-store-api:latest
```
