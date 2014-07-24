#!/bin/sh
BASEDIR=`dirname $0`
exec java \
     -d64 \
     -XstartOnFirstThread \
     -jar $BASEDIR/uber-thin-web-desktop-1.0-SNAPSHOT.jar