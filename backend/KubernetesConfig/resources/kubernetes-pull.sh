#!/bin/bash
images=(
    kube-scheduler-amd64:v1.11.0
    kube-proxy-amd64:v1.11.0
    kube-controller-manager-amd64:v1.11.0
    kube-apiserver-amd64:v1.11.0
    etcd-amd64:3.2.18
    coredns:1.1.3
    pause-amd64:3.1)

for imageName in ${images[@]} ; do
    docker pull ssingularity/$imageName
    docker tag  ssingularity/$imageName k8s.gcr.io/$imageName
done
