#!/bin/bash

JAR=target/consul-reg-agent-0.1-SNAPSHOT.jar

if [ ! -f ${JAR} ]; then
    echo "${JAR} not found - run mvn package first!"
else

    java -javaagent:${JAR}=name:zoidberg,tag:service,port:12345 \
         -javaagent:${JAR}=name:zoidberg,tag:jmx,port:12346 \
        -classpath ${JAR} net.loshodges.consul.DemoReg

fi