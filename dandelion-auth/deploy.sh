#!/bin/bash
PROFILES_ACTIVE=$2
REPO_NAME="dandelion-auth"
VERSION=$1
CONTAINER_NAME="dandelion-auth"
LOG_PATH="/home/logs/dandelion_auth"
PORT=8080
PRE_IMAGE="icepear"

set -ex;

sudo docker rmi ${REPO_NAME}:old || true
sudo docker tag ${PRE_IMAGE}/${REPO_NAME}:${VERSION} ${REPO_NAME}:old || true

sudo docker build -t "icepear/"${REPO_NAME}:${VERSION} .
sudo docker rm -f ${CONTAINER_NAME} || true
sudo docker run -d --name ${CONTAINER_NAME} \
        --network daaswork --ip 172.22.0.5 \
        -p ${PORT}:${PORT} \
        -e PROFILES_ACTIVE=${PROFILES_ACTIVE} \
        -v ${LOG_PATH}:/home/dandelion/logs/auth:Z \
        -v /home/dandelion_auth/config:/config ${PRE_IMAGE}/${REPO_NAME}:${VERSION}

exit