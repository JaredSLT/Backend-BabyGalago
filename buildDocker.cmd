@echo off
echo "Building docker"
docker build --no-cache -t gitlab.tresearchgroup.tech:5050/jthorne/babygalagoserver-alpha .
echo "Pushing docker"
docker push gitlab.tresearchgroup.tech:5050/jthorne/babygalagoserver-alpha