#!/bin/bash

export SRC_DIR="/home/procurement/src"
export TOMCAT_DIR="/var/lib/tomcats/procurement"
export MAVEN_DIR="/usr/local/sbin/mvn"
export REPO="procurement"
#export JAVA_HOME="/usr/java/jdk1.7.0_80"
export DEPLOY_HOME="/home/procurement/v3deploy_online"

. lib_checkversion.sh
check_version "$REPO" "$1"

echo " Start pull $REPO's source code "
cd $SRC_DIR/$REPO
git pull

echo "Start do package"
$MAVEN_DIR -e -Dmaven.test.skip=true -DfailIfNoTests=false clean -U package
x=$?
if [ $x -ne 0 ]
then
        echo "打包失败！${1} code is :${?}"
        exit 1
else
        echo "打包成功！${1}"
fi
echo "backup procurement.itf's v3center.war as v3center.war.`date +'+%Y%m%d'`"
sudo -u procurement.itf  mv /home/procurement.itf/release_package/v3center.war /home/procurement.itf/release_package/v3center.war.`date '+%Y%m%d'`
sudo -u procurement.itf cp $SRC_DIR/$REPO/target/v3center.war /home/procurement.itf/release_package/
echo "Shut down tomcat now ..."

$DEPLOY_HOME/stopcat.sh

rm -fr $TOMCAT_DIR/webapps/v3center/*
rm -fr $TOMCAT_DIR/work/*

unzip $SRC_DIR/$REPO/target/v3center.war -d $TOMCAT_DIR/webapps/v3center/
cp $DEPLOY_HOME/pc-api.db.properties $TOMCAT_DIR/webapps/v3center/WEB-INF/classes/config/pc-api.db.properties
cp $DEPLOY_HOME/pc-api.conf.properties $TOMCAT_DIR/webapps/v3center/WEB-INF/classes/config/pc-api.conf.properties
cp $DEPLOY_HOME/procurement_upload.properties $TOMCAT_DIR/webapps/v3center/WEB-INF/classes/
cp $DEPLOY_HOME/configManager.properties $TOMCAT_DIR/webapps/v3center/WEB-INF/classes/
echo "Start tomcat..."
#export CATALINA_OPTS="$CATALINA_OPTS -server -Djava.awt.headless=true -Xms20G -Xmx20G -XX:PermSize=512m -XX:MaxPermSize=512m -XX:+UseParallelOldGC -XX:+DisableExplicitGC -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCApplicationStoppedTime -XX:+PrintTenuringDistribution -Xloggc:/home/tomcat/v3/log/gc.log"
$DEPLOY_HOME/startcat.sh

