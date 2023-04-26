@echo off
echo "Building package"
mvn --batch-mode --quiet --threads 4 package