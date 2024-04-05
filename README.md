# PetStore Project

## Purpose
Demo application built for experimentation purpose only

## Pre-requisites
1. We are using Docker to install [PostgreSQL database](https://www.postgresql.org/download/). If you are interested in using a local or remote PostgreSQL installation, update the database connectivity details in `resources`. Here is the example of [pets-api application-docker.yml](./pets-api/src/main/resources/application-docker.yml).

## Services
1. [Pets API](./pets-api/README.md)

## Docker deployment
```shell
docker compose up --build
```