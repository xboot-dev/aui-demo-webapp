#!/bin/bash

##########################################################################
#                                                                        #
# AUI Demo - Docker Run Image Script.                                    #
# Copyright 2016, 2018 Tomas Hrdlicka <tomas@hrdlicka.co.uk>.            #
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
DOCKER_CONTAINER_NAME=${DOCKER_IMAGE_NAME////-}
DOCKER_OPTS="-p 8080:8080 -v `pwd`:/mnt/share"

echo
echo "Using DOCKER_IMAGE_DIR:       $DOCKER_IMAGE_DIR"
echo "Using DOCKER_IMAGE_NAME:      $DOCKER_IMAGE_NAME"
echo "Using DOCKER_IMAGE_TAG:       $DOCKER_IMAGE_TAG"
echo "Using DOCKER_CONTAINER_NAME:  $DOCKER_CONTAINER_NAME"
echo "Using DOCKER_OPTS:            $DOCKER_OPTS"
echo

DOCKER_CONTAINER_STATUS=`$DOCKER_CMD inspect --format={{.State.Status}} $DOCKER_CONTAINER_NAME`

dockerContainerInfo() {
    DOCKER_CONTAINER_NAME=$1
    DOCKER_CONTAINER_IPV4=`$DOCKER_CMD inspect --format={{.NetworkSettings.IPAddress}} $DOCKER_CONTAINER_NAME`

    echo "Container IPv4 Address:		$DOCKER_CONTAINER_IPV4"
    echo
}

if [ "$1" = "show-log" ]; then
    echo "Showing log of container '$DOCKER_CONTAINER_NAME' ..."
    echo

    dockerContainerInfo $DOCKER_CONTAINER_NAME

    $DOCKER_CMD logs $DOCKER_CONTAINER_NAME -f
    return 0

elif [ "$1" = "create" ]; then
    echo "Creating new instance of container '$DOCKER_CONTAINER_NAME' ..."
    echo

    if [ "$DOCKER_CONTAINER_STATUS" = "running" ]; then
        $DOCKER_CMD stop $DOCKER_CONTAINER_NAME
    fi

    DOCKER_CONTAINER_STATUS=stopped

    $DOCKER_CMD rm $DOCKER_CONTAINER_NAME
fi

if [ "$DOCKER_CONTAINER_STATUS" = "exited" ] || [ "$DOCKER_CONTAINER_STATUS" = "created" ]; then
    $DOCKER_CMD start $DOCKER_CONTAINER_NAME
    DOCKER_CONTAINER_STATUS=`$DOCKER_CMD inspect --format={{.State.Status}} $DOCKER_CONTAINER_NAME`
fi

if [ "$DOCKER_CONTAINER_STATUS" = "running" ]; then
    dockerContainerInfo $DOCKER_CONTAINER_NAME
    $DOCKER_CMD exec -it $DOCKER_CONTAINER_NAME /bin/bash

else
    $DOCKER_CMD run -d -i --name $DOCKER_CONTAINER_NAME $DOCKER_OPTS $DOCKER_IMAGE_NAME

    echo
    dockerContainerInfo $DOCKER_CONTAINER_NAME

    $DOCKER_CMD logs $DOCKER_CONTAINER_NAME -f
fi
