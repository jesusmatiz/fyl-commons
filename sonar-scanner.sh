#!/bin/bash
cd ./sonar-scanner/bin
./sonar-scanner.bat -Dproject.settings=../../sonar-project.properties
