FROM eclipse-temurin:17
RUN useradd -u 101 alpha
RUN mkdir -p /home/alpha/app && chown -R alpha:alpha /home/alpha/app
WORKDIR /home/alpha/app
USER alpha
ADD --chown=alpha:alpha target/pets-api-1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","./app.jar"]
