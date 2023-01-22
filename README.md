## weather-prediction

# Setup
* The project in directory `./weather-prediction-api` is the gradle project and implements API in springboot and Kotlin
* While importing the project in Intellij, select `build.gradle` and open as project
* Set Project JDK to Java 17 by opening Module Settings for the project
* Use `clean`, `build` and then `bootRun` options build and application tasks in the Gradle tool window
* Alternatively `./gradlew clean build bootRun` can be used

# Test
* The spring boot application runs at port 8080
* The api doc can be seen at `http://localhost:8080/swagger-ui/index.html`

# UI
* The UI that consumes this API is located in directory `./weather-prediction-ui`
* The UI can be setup following the commands
```shell
    > cd ./weather-prediction-ui
    > npm install
    > npm start
```
* The UI in react runs at port 5001

# CORS
* As UI running on port different from server, CORS issue may arise, to tackle it Chrome can be opened with disabled-web-security and a different user-dir
* For Mac users: `open -na Google\ Chrome --args --disable-web-security --user-data-dir="/Users/anunay.anindya/.hacked-chrome-user0"`
