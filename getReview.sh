#!/bin/sh

currentDir=`pwd`
CLASSPATH=.:${currentDir}/bin:${currentDir}/libs/*
limit=10
appId=

if [ $# -lt 1 ]; then
  echo "appIdを指定してください。" 1>&2
  echo "Usage:$0 appId [limit]" 1>&2
  echo "limit default 10." 1>&2
  exit 1
fi

appId=$1
if [ $# -eq 2 ]; then
  limit=$2
fi

java -Dfile.encoding=utf-8 -Duser.language=ja -classpath ${CLASSPATH} MarketApiTest $1 ${limit}
