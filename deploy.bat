@echo off

echo STEP 1: mvn clean package -DskipTests
call mvn clean package -DskipTests

echo STEP 2: copy file
copy "target\sky-lieve-0.0.1-SNAPSHOT.jar" "docker-sky-lieve\sky-lieve-0.0.1-SNAPSHOT.jar"

echo STEP 3: push files
scp -r -P 50023 docker-sky-lieve\ root@108.160.132.84:/root/

echo all finished