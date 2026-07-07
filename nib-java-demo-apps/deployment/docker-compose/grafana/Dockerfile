#
# Remember to change the datasources:
#    `Prometheus` -> `Prometheus`
#    `${DS_LOKI}`       -> `Loki`
#   `${DS_MOTORHEAD_ADMIN NODE}`    -> `Motorhead Admin Node`
# in the dashboard JSON before building
#
FROM grafana/grafana:latest
RUN grafana-cli plugins install marcusolsson-json-datasource
RUN grafana-cli plugins install volkovlabs-form-panel
#ADD ./provisioning /etc/grafana/provisioning
#ADD ./dashboards /var/lib/grafana/dashboards
#ENV GF_AUTH_DISABLE_LOGIN_FORM "true"
#ENV GF_AUTH_ANONYMOUS_ENABLED "true"
#ENV GF_AUTH_ANONYMOUS_ORG_ROLE "Admin"
