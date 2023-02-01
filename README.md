## weather-prediction

# Execution
[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/aa-tt/weather-prediction)
--
or,
1. Git Clone this repo.
2. Execute below `docker-compose` command wait for images to be pulled, build and started.
3. Open http://localhost for app.
4. Open http://localhost:8080/swagger-ui/index.html for swagger.

```shell
docker-compose -f docker-compose-dev.yml up
```

----
OR

# Setup (to run locally in say IntelliJ)
* The project in directory `./weather-prediction-api` is the gradle project and implements API in springboot and Kotlin
* While importing the project in Intellij, select `build.gradle` and open as project
* Set Project JDK to Java 17 by opening Module Settings for the project
* Use `clean`, `build` and then `bootRun` options build and application tasks in the Gradle tool window
* Alternatively `./gradlew clean build bootRun` can be used

# Docker
The images build in the project are build and published at https://hub.docker.com/repositories/ianunay.
For example, api published with tag v0 and image name ianunay/weather-app-api
```shell
> docker build -t ianunay/weather-app-api:v0 -f Dockerfile-api .
> docker push ianunay/weather-app-api:v0
```
# API (microservice in springboot and kotlin-jvm)
* The api runs at port 8080
* The api doc can be seen at `http://localhost:8080/swagger-ui/index.html`

# UI (microfrontend in react 16 (functional components and react hooks) )
* The UI that consumes this API is located in directory `./weather-prediction-ui`
* The UI can be started following the commands
```shell
    > cd ./weather-prediction-ui
    > npm install
    > npm start
```
* The UI in react runs at port 5001

# Caching
Caching is done using redis server running in docker
### todos
- security
- logging
- controller advice for error responses
- ~~caching~~ Done
- ~~unit test for tdd~~ Done
- ~~integratuon test for bdd~~ Done
- ~~docker-compose and jenkins script~~ Done
