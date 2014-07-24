#!/bin/sh
BASEDIR=`dirname $0`
exec java \
     -d64 \
     -XstartOnFirstThread \
     -jar $BASEDIR/target/uber-jersey1-grizzly2-spring-1.0-SNAPSHOT.jar