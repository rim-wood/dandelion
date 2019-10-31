#!/bin/bash
PROFILES_ACTIVE=$2
REPO_NAME="dandelion-upm"
VERSION=$1
CONTAINER_NAME="dandelion-upm"
LOG_PATH="/home/logs/dandelion_upm"
PORT=8008
PRE_IMAGE="icepear"

set -ex;
# docker network create --subnet=172.22.0.0/16 daaswork
sudo docker rmi ${REPO_NAME}:old || true
sudo docker tag ${PRE_IMAGE}/${REPO_NAME}:${VERSION} ${REPO_NAME}:old || true

sudo docker build -t "icepear/"${REPO_NAME}:${VERSION} .
sudo docker rm -f ${CONTAINER_NAME} || true
sudo docker run -d --name ${CONTAINER_NAME} \
        -p ${PORT}:${PORT} -p 18008:18008 \
        --network daaswork --ip 172.22.0.4 \
        -e PROFILES_ACTIVE=${PROFILES_ACTIVE} \
        -v ${LOG_PATH}:/home/dandelion/logs/upm:Z \
        -v /home/dandelion_upm/config:/config ${PRE_IMAGE}/${REPO_NAME}:${VERSION}

exit
