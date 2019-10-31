#!/bin/bash
PROFILES_ACTIVE=$2
REPO_NAME="dandelion-schedule"
VERSION=$1
CONTAINER_NAME="dandelion-schedule"
LOG_PATH="/home/logs/dandelion_schedule"
PORT=8009
PRE_IMAGE="icepear"

set -ex;
# docker network create --subnet=172.22.0.0/16 daaswork
sudo docker rmi ${REPO_NAME}:old || true
sudo docker tag ${PRE_IMAGE}/${REPO_NAME}:${VERSION} ${REPO_NAME}:old || true

sudo docker build -t "icepear/"${REPO_NAME}:${VERSION} .
sudo docker rm -f ${CONTAINER_NAME} || true
sudo docker run -d --name ${CONTAINER_NAME} \
        -p ${PORT}:${PORT}  \
        --network daaswork --ip 172.22.0.6 \
        -e PROFILES_ACTIVE=${PROFILES_ACTIVE} \
        -v ${LOG_PATH}:/logs/dandelion_schedule_home:Z \
        -v /home/dandelion_schedule/config:/config ${PRE_IMAGE}/${REPO_NAME}:${VERSION}

exit
