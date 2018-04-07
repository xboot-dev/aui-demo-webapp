#!/bin/bash

##########################################################################
#                                                                        #
# AUI Demo - Docker Stop Image Script.                                   #
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

echo
echo "Using DOCKER_IMAGE_DIR:       $DOCKER_IMAGE_DIR"
echo "Using DOCKER_IMAGE_NAME:      $DOCKER_IMAGE_NAME"
echo "Using DOCKER_IMAGE_TAG:       $DOCKER_IMAGE_TAG"
echo "Using DOCKER_CONTAINER_NAME:  $DOCKER_CONTAINER_NAME"
echo

$DOCKER_CMD stop $DOCKER_CONTAINER_NAME
