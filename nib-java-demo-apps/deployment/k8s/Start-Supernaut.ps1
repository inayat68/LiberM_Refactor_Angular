kubectl create namespace supernaut-demo
kubectl create configmap supernaut-config --namespace supernaut-demo --from-file=./supernaut
kubectl create secret generic nib-java-license --namespace supernaut-demo --from-literal=NIB_JAVA_LICENSE_KEY="$env:NIB_JAVA_LICENSE_KEY"
kubectl apply -f redis-supernaut.yaml
kubectl apply -f supernaut-deployment.yaml
kubectl apply -f supernaut-service.yaml
kubectl apply -f supernaut-ingress.yaml

kubectl apply -f tn3270-deployment.yaml
kubectl apply -f tn3270-service.yaml
