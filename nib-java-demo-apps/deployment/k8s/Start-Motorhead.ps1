kubectl create namespace motorhead-demo
kubectl create configmap motorhead-config --namespace motorhead-demo --from-file=./motorhead
kubectl create secret generic nib-java-license --namespace motorhead-demo --from-literal=NIB_JAVA_LICENSE_KEY="$env:NIB_JAVA_LICENSE_KEY"
kubectl apply -n motorhead-demo -f redis-motorhead.yaml
kubectl apply -f motorhead-a-deployment.yaml
kubectl apply -f motorhead-b-deployment.yaml
kubectl apply -f motorhead-c-deployment.yaml
# kubectl apply -f motorhead-proxy-deployment.yaml
kubectl apply -f motorhead-service.yaml
kubectl apply -f motorhead-ingress.yaml

