#!/bin/sh
tag=$(date +"%Y%m%d-%H%M%S")
image=192.168.30.32:5000/test/spring-boot:$tag
docker login 192.168.30.32:5000
docker build -t $image .
docker push $image
echo "$image"
