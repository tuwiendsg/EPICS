#!/bin/bash

while sudo fuser /var/lib/dpkg/lock >/dev/null 2>&1; do
   sleep 1
done

sudo apt-get remove openjdk-6-jre-lib
sudo apt-get -y install maven2 openjdk-7-jdk

wget -Nq EDAAS.ZIP.URL
unzip  EDAAS.NAME.zip
cd eDaaS
mvn clean install

cp target/eDaaS.war /var/lib/tomcat7/webapps

