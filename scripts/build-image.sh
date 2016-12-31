#!/bin/bash

##########################################################################
#                                                                        #
# AUI Demo - Docker Build Image Script.                                  #
# Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>.                  #
# All rights reserved.                                                   #
#                                                                        #
# Web: https://tomas.hrdlicka.co.uk                                      #
# Git: https://github.com/xboot-dev/aui-demo-webapp                      #
#                                                                        #
##########################################################################

DOCKER_CMD=docker
DOCKER_IMAGE_DIR="$( cd "$(dirname "$0")" ; pwd -P )/.."
DOCKER_IMAGE_NAME=xboot/aui-demo
DOCKER_IMAGE_TAG=1.0.0
DOCKER_CONTAINER_NAME=${DOCKER_IMAGE_NAME////-}-dev

echo
echo "Using DOCKER_IMAGE_DIR:       $DOCKER_IMAGE_DIR"
echo "Using DOCKER_IMAGE_NAME:      $DOCKER_IMAGE_NAME"
echo "Using DOCKER_IMAGE_TAG:       $DOCKER_IMAGE_TAG"
echo "Using DOCKER_CONTAINER_NAME:  $DOCKER_CONTAINER_NAME"
echo

DOCKER_CONTAINER_STATUS=`docker inspect --format={{.State.Status}} $DOCKER_CONTAINER_NAME`

if [ "$DOCKER_CONTAINER_STATUS" = "running" ]; then
    $DOCKER_CMD stop $DOCKER_CONTAINER_NAME
    DOCKER_CONTAINER_STATUS=`docker inspect --format={{.State.Status}} $DOCKER_CONTAINER_NAME`
fi

if [ "$DOCKER_CONTAINER_STATUS" = "exited" ] || [ "$DOCKER_CONTAINER_STATUS" = "created" ]; then
    $DOCKER_CMD rm $DOCKER_CONTAINER_NAME
    $DOCKER_CMD rmi $DOCKER_IMAGE_NAME
else
    DOCKER_IMAGE_COUNT=`$DOCKER_CMD images | grep $DOCKER_IMAGE_NAME | wc -l`

    if [ $DOCKER_IMAGE_COUNT -gt 0 ]; then
        $DOCKER_CMD rmi $DOCKER_IMAGE_NAME
    fi
fi

#$DOCKER_CMD build --no-cache -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} -t ${DOCKER_IMAGE_NAME}:latest $DOCKER_IMAGE_DIR
$DOCKER_CMD build -t $DOCKER_IMAGE_NAME $DOCKER_IMAGE_DIR