provider "kubernetes" {
  config_path = "~/.kube/config"
}

resource "kubernetes_manifest" "api_deployment" {
  manifest = yamldecode(file("${path.module}/k8s-manifests/manifest-api-deployment.yaml"))
}

resource "kubernetes_manifest" "api_service" {
  manifest = yamldecode(file("${path.module}/k8s-manifests/manifest-api-service.yaml"))
}

resource "kubernetes_manifest" "consul_ssm_deployment" {
  manifest = yamldecode(file("${path.module}/k8s-manifests/manifest-consul-deployment.yaml"))
}

resource "kubernetes_manifest" "consul_ssm_service" {
  manifest = yamldecode(file("${path.module}/k8s-manifests/manifest-consul-service.yaml"))
}

resource "kubernetes_manifest" "nginx_deployment" {
  manifest = yamldecode(file("${path.module}/k8s-manifests/manifest-nginx-deployment.yaml"))
}

resource "kubernetes_manifest" "nginx_service" {
  manifest = yamldecode(file("${path.module}/k8s-manifests/manifest-nginx-service.yaml"))
}

resource "kubernetes_manifest" "ui_deployment" {
  manifest = yamldecode(file("${path.module}/k8s-manifests/manifest-ui-deployment.yaml"))
}

resource "kubernetes_manifest" "ui_service" {
  manifest = yamldecode(file("${path.module}/k8s-manifests/manifest-ui-service.yaml"))
}

resource "kubernetes_manifest" "redis_cache_weather_prediction_deployment" {
  manifest = yamldecode(file("${path.module}/k8s-manifests/manifest-redis-deployment.yaml"))
}

resource "kubernetes_manifest" "redis_cache_weather_prediction_service" {
  manifest = yamldecode(file("${path.module}/k8s-manifests/manifest-redis-service.yaml"))
}