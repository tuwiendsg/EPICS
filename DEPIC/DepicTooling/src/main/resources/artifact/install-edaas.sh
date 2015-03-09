#!/bin/bash

while sudo fuser /var/lib/dpkg/lock >/dev/null 2>&1; do
    echo "Waiting other apt-get. Sleep 1" >> out.txt
   sleep 1
done

sudo apt-get remove openjdk-6-jre-lib >> out.txt
sudo apt-get -y install openjdk-7-jdk maven2 tomcat7 >> out.txt

wget -Nq EDAAS.ZIP.URL >> out.txt
unzip  EDAAS.NAME.zip >> out.txt
cd eDaaS 
mvn clean install >> out.txt

sudo cp target/eDaaS.war /var/lib/tomcat7/webapps >> out.txt

