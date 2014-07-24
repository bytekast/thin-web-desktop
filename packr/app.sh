#!/bin/sh
BASEDIR=`dirname $0`
exec chmod +x $BASEDIR/jre/bin/*

exec $BASEDIR/jre/bin/java \
     -d64 \
     -XstartOnFirstThread \
     -jar $BASEDIR/uber-thin-web-desktop-1.0-SNAPSHOT.jar