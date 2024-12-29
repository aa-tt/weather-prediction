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
# Kubernetes
### Kind - Kubernetes in Docker for single node K8S cluster and local development
```shell
kind create cluster --name weather-cluster
kubectl cluster-info --context kind-weather-cluster
kubectl apply -f kubernetes-dashboard.yaml
kubectl port-forward service/nginx 30080:80 -n kind-weather-app
````
http://localhost:30080

### Kompose - deploy a sample docker-compose yaml file to a Kubernetes cluster.
```
brew install kompose

# Convert your Docker Compose file to Kubernetes:
kompose convert -f docker-compose-k8s.yml
```
### Microk8s - for kubernetes cluster
```shell
brew install ubuntu/microk8s/microk8s
microk8s install
microk8s status --wait-ready
microk8s enable dashboard dns istio storage
microk8s kubectl
alias k='microk8s.kubectl'
microk8s dashboard-proxy
microk8s kubectl apply -f .

microk8s kubectl delete -n default deployment ui consul redis-cache-weather-prediction nginx api
microk8s kubectl delete -n default persistentvolumeclaim consul-claim0 redis-cache-weather-prediction-claim0
microk8s kubectl delete -n default service api consul nginx redis-cache-weather-prediction ui kubernetes
```
### Minikube - for kubernetes cluster
```shell
minikube start
minikube kubectl -- get po -A
minikube dashboard

minikube kubectl -- delete -n default service api consul nginx ui redis-cache-weather-prediction
```
### kubectl
```shell
kubectl create deployment wapp-ui --image=ianunay/weather-app-ui:v0
kubectl expose deployment wapp-ui --type=LoadBalancer --port=5001
kubectl port-forward service/wapp-ui 5001:5001

kubectl create deployment wapp-redis --image=redis
kubectl expose deployment wapp-redis --type=LoadBalancer --port=6379
kubectl port-forward service/wapp-redis 6379:6379

kubectl create deployment wapp-ssm --image=ianunay/weather-app-ssm:v0
kubectl expose deployment wapp-ssm --type=LoadBalancer --port=8500
kubectl port-forward service/wapp-ssm 8500:8500

kubectl create deployment wapp-api --image=ianunay/weather-app-api:v0
kubectl expose deployment wapp-api --type=LoadBalancer --port=8080
kubectl port-forward service/wapp-api 8080:8080
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
The cached keys can be checked by login to redis-cli
```shell
    > redis-cli
    127.0.0.1:6379> keys *
```
### todos
- security
- logging
- controller advice for error responses
- ~~caching~~ Done
- ~~unit test for tdd~~ Done
- ~~integratuon test for bdd~~ Done
- ~~docker-compose and jenkins script~~ Done
