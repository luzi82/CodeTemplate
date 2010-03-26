#!/bin/sh

PROJECT_PATH=/home/luzi82/project/tool/eclipse_workspace/CodeTemplate

cd ${PROJECT_PATH}/test/$1

java \
 -cp ${PROJECT_PATH}/bin/ \
 guri.codetemplate.CodeTemplate \
 -g ${PROJECT_PATH}/res/code_template_global_config.xml \
 -t input.xml
