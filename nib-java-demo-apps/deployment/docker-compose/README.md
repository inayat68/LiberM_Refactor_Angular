# Deployment with Docker Compose

This directory provides an example deployment of NIB containers using Docker Compose, designed to showcase the features
of Supernaut and Motorhead and their integration with other services.

The deployment includes:

- A Supernaut region instance
- A TN3270 gateway node
- A Motorhead cluster (initiators and proxy)
- A Redis instance serving as the communication backbone for Supernaut and Motorhead
- A PostgreSQL database for application data persistence
- A Grafana instance for monitoring and visualization
- A Loki instance for centralized log aggregation
- A Promtail instance for log collection and forwarding to Loki
- A Tempo instance for distributed tracing
- A Prometheus instance for metrics collection and exposure
- An Nginx instance acting as a reverse proxy for internal services

## Pre-requisites

To run this example, you need Docker installed on your machine and a valid license key for NIB.

## Environment variables

Make sure you have the following two environment variables set:

- `NIB_JAVA_LICENSE_KEY`: A valid license key for NIB
- `NIB_JAVA_DEMO_HOME`: Set to the path of the root of the project

## Deployment

To create and deploy the containers, run the following commands from the root of the project:

```shell
mvn clean package
cd deployment/docker-compose 
docker compose up --force-recreate --build -d 
```

## Ports used

| Port | Purpose                |
|------|------------------------|
| 3271 | TN3270                 |
| 8090 | Supernaut Web Services |
| 8081 | Motorhead proxy        |
| 3000 | Grafana                |

