#!/usr/bin/env bash
# 脚本出错就停止
set -e
set -x
TAG_NAME=V1.0
#获取当前目录名称
Project_name=$(basename `pwd`)
mvn package

ls -al target

echo "start build docker images"
docker build -t reg.jdy.com/sentinel/docker_${Project_name}:${TAG_NAME} .

echo "start push docker images"
docker push reg.jdy.com/sentinel/docker_${Project_name}:${TAG_NAME}

echo "over"

