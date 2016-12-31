#!/bin/bash

##########################################################################
#                                                                        #
# AUI Demo - Application Launcher Script.                                #
# Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>.                  #
# All rights reserved.                                                   #
#                                                                        #
# Web: https://tomas.hrdlicka.co.uk                                      #
# Git: https://github.com/xboot-dev/aui-demo-webapp                      #
#                                                                        #
##########################################################################

PORTAL_BASEDIR="$( cd "$(dirname "$0")" ; pwd -P )"
PORTAL_OPTS="-Dportal.basedir=${PORTAL_BASEDIR}"

export JAVA_OPTS="${JAVA_OPTS} ${PORTAL_OPTS} -Djava.security.egd=file:/dev/./urandom"

echo
echo "Using PORTAL_BASEDIR:     $PORTAL_BASEDIR"
echo "Using JAVA_OPTS:          $JAVA_OPTS"
echo

bin/catalina.sh run