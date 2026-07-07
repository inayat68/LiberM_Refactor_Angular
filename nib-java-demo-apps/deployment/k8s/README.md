# Deployment with Kubernetes

This directory provides an example deployment of NIB containers on **Kubernetes**.  
It is intended as a demo environment for local development and testing.

The deployment includes:

- A **Supernaut region** deployment
- A **TN3270 gateway** deployment
- A **Motorhead cluster** (initiators only) deployment

In production environments based on k8s, **Redis** and the database, are usually provided as managed services (e.g., AWS
ElastiCache, Azure Cache for Redis, Amazon RDS, Azure Database for PostgreSQL, etc.) or by on-prem database farms.

In this demo, to simplify setup, Redis and PostgreSQL services are simulated using **local Docker containers** rather
than provisioned cloud services.

> To run this example, you need a valid license key for NIB.

## Environment variables

Make sure you have the following two environment variables set:

- `NIB_JAVA_LICENSE_KEY`: A valid license key for NIB
- `NIB_JAVA_DEMO_HOME`: Set to the path of the root of the project

## Pre-requisites

In order to run this example, you need to have the following:

- A Kubernetes cluster (e.g. EKS, AKS, GKE, etc.)
- kubectl installed and configured to access your cluster

If working with k8s on docker, make sure to have the following:

- supernaut and motorhead images available locally
- correctly configured docker daemon (kubernetes enabled)
- correctly configured kubctl to access the docker daemon
    - check with `kubectl config current-context`
    - if needed, set the context with `kubectl config use-context docker-desktop`
- make sure you have an NGINX ingress controller deployed with `kubectl get pods -A | grep ingress`
  if not, run
  `kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.10.1/deploy/static/provider/cloud/deploy.yaml`

> Note: The demo mounts local directories into the pods, so make double check that the paths are correct for your
> system.

## Start and stop the demo

A set of powershell scripts controls the deployment/undeployment.

To start:

- `Start-Motorhead.ps1` - deploys the Motorhead pods and services
- `Start-Supernaut.ps1` - deploys the Supernaut pods and services

To Stop:

- `Stop-Motorhead.ps1` - delete the Supernaut pods and services
- `Stop-Supernaut.ps1` - delete the Supernaut pods and services
