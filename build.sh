#!/bin/bash

mvn clean install
docker buildx build -f Dockerfile --platform linux/amd64 --tag pet-store-api:latest --load .