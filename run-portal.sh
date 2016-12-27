#!/bin/bash

PORTAL_ROOTDIR=`pwd`/target
PORTAL_BASEDIR=$PORTAL_ROOTDIR/aui-demo
PORTAL_WEBAPPS=$PORTAL_BASEDIR/webapps
PORTAL_LOGS=$PORTAL_BASEDIR/logs
PORTAL_NAME=aui-demo-webapp
PORTAL_CONTEXT_PATH=/
PORTAL_PORT=8080

if [[ ! -d "$PORTAL_ROOTDIR" ]]; then
    echo "You need to build AUI Demo WebApp at first! Please run command 'build.sh' ..." >&2
    exit 1
fi

mkdir -p $PORTAL_BASEDIR
mkdir -p $PORTAL_WEBAPPS
mkdir -p $PORTAL_LOGS

cp $PORTAL_ROOTDIR/$PORTAL_NAME.war $PORTAL_WEBAPPS

PORTAL_OPTS="-Dportal.basedir=${PORTAL_BASEDIR} -Dorg.eclipse.jetty.LEVEL=INFO"

JETTY_BASEDIR=$PORTAL_ROOTDIR/dependency
JETTY_RUNNER_ARGS="--path $PORTAL_CONTEXT_PATH --port $PORTAL_PORT $PORTAL_WEBAPPS/$PORTAL_NAME.war"

java $PORTAL_OPTS -jar $JETTY_BASEDIR/jetty-runner.jar --log $PORTAL_BASEDIR/logs/jetty-runner.log $JETTY_RUNNER_ARGS